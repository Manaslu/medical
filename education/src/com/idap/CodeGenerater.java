package com.idap;


 

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.idp.pub.entity.annotation.MetaTable;

 

public class CodeGenerater {
	public static String replaceslash(String str){
		int index=   str.indexOf("_");
		String c=str.substring(index+1, index+2);
		String ret = str.replaceAll("_"+c,c.toUpperCase());
		return ret;
	}
	
	public static String GeneraterJava(List table,String packagefullname,String classname){
		String javastring="package "+packagefullname+".entity;\r\n";
		javastring+="\r\n";
		javastring+="import java.util.Date;\r\n";
		javastring+="import com.idp.pub.entity.annotation.MetaTable;\r\n";
		javastring+="\r\n";
		javastring+="@MetaTable\r\n";
		javastring+="public class "+classname+" implements java.io.Serializable {\r\n";
		javastring+="	private static final long serialVersionUID = 5736511926578194639L;\r\n";
		
		   for (int i = 0; i < table.size(); i++) {
			   Map<String, String> m = (Map)table.get(i);
			   javastring+= "	private "+m.get("type")+" "+replaceslash((String)m.get("column"))+";\r\n";
	        }
		   javastring+="\r\n";
		   javastring+="}\r\n";
		return javastring;
	}
	
 
 
	public static void main(String[] args) {
		
		String classname="ClinicInfo";
		String packagename="clinic";
		String[][] tablearr = {
				                {"clinic_id","String"},
				                {"clinic_name","String"},
				                {"clinic_date","Date"},
				                {"clinic_license","int"}
				              };
		
		 
		ArrayList table = new ArrayList();
		for(int i=0;i<tablearr.length;i++){
			Map<String, String> map = new HashMap<String, String>();
			map.put("column", tablearr[i][0]);
			map.put("type", tablearr[i][1]);
			table.add(map);
		}
	 
 
		String packagefullname="com.idap."+packagename;
		String entityPath="\\src\\com\\idap\\"+packagename+"\\entity\\";
		String daoPath = "\\src\\com\\idap\\"+packagename+"\\dao\\";
		String implPath="\\src\\com\\idap\\"+packagename+"\\service\\impl\\";
		String controllerPath="src\\com\\idap\\web\\"+packagename+"\\controller\\";
		String xmlPath = "\\src\\resources\\com\\idap\\"+packagename+"\\entity\\mapping\\";
		String absolutePath= new File("").getAbsolutePath() ;  
 
		

		
//java		
		 try {
			   File f = new File(absolutePath+entityPath+classname+".java");
			   if (!f.exists()) {
			    f.createNewFile();
			   }
			   OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f),"UTF-8");
			   BufferedWriter writer=new BufferedWriter(write);   
			   String javaContent=GeneraterJava(table,packagefullname,classname);
			   writer.write(javaContent);
			   writer.close();
			   System.out.println("写java文件成功");
			  } catch (Exception e) {
			   System.out.println("写java文件内容操作出错");
			   e.printStackTrace();
			  }
		 
		

 

 
		
 
	
	}
}
