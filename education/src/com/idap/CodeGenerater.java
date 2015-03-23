package com.idap;


 

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

 

 

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
	
 
 
	public static String GeneraterDao(String packagefullname,String classname,String lclassname){
		String javadaostring="package "+packagefullname+".dao;\r\n";
		javadaostring+="\r\n";
		javadaostring+="import org.springframework.stereotype.Repository;\r\n";
		javadaostring+="import com.idp.pub.dao.impl.DefaultBaseDao;\r\n";
		javadaostring+="import "+packagefullname+".entity."+classname+";\r\n";
		javadaostring+="\r\n";
		javadaostring+="@Repository(\""+lclassname+"Dao\")\r\n";
		javadaostring+="public class "+classname+"Dao extends DefaultBaseDao<"+classname+",String>{\r\n";
		javadaostring+="	@Override\r\n";
		javadaostring+="	public String getNamespace() {\r\n";
		javadaostring+="		return "+classname+".class.getName();\r\n";
		javadaostring+="	}\r\n";
		javadaostring+="}\r\n";
		
 
		   javadaostring+="\r\n";
		return javadaostring;
	}
	

	public static String GeneraterImpl(String packagename,String packagefullname,String classname,String lclassname,String primirykey){
		String javaimplstring="package "+packagefullname+".service.impl;\r\n";
		javaimplstring+="\r\n";
		javaimplstring+="import java.util.Date;\r\n";
		javaimplstring+="import java.util.Map;\r\n";
		javaimplstring+="import javax.annotation.Resource;\r\n";
		javaimplstring+="import org.springframework.stereotype.Service;\r\n";
		javaimplstring+="import org.springframework.transaction.annotation.Transactional;\r\n";
		javaimplstring+="import com.idp.pub.dao.IBaseDao;\r\n";
		javaimplstring+="import com.idp.pub.dao.IPagerDao;\r\n";
		javaimplstring+="import com.idp.pub.generatekey.service.IGenerateKeyMangerService;\r\n";
		javaimplstring+="import com.idp.pub.service.impl.DefaultBaseService;\r\n";
		javaimplstring+="import com.idap."+packagename+".entity."+classname+";\r\n";
		javaimplstring+="\r\n";
		javaimplstring+="\r\n";
		javaimplstring+="@Transactional\r\n";
		javaimplstring+="@Service(\""+lclassname+"Service\")\r\n";
		javaimplstring+="public class "+classname+"ServiceImpl extends DefaultBaseService<"+classname+", String>\r\n";
		javaimplstring+="{\r\n";
		javaimplstring+="	@Resource(name = \""+lclassname+"Dao\")\r\n";
		javaimplstring+="	public void setBaseDao(IBaseDao<"+classname+", String> baseDao) {\r\n";
		javaimplstring+="		super.setBaseDao(baseDao);\r\n";
		javaimplstring+="	}\r\n";
		javaimplstring+="\r\n";
		javaimplstring+="	@Resource(name = \""+lclassname+"Dao\")\r\n";
		javaimplstring+="	public void setPagerDao(IPagerDao<"+classname+"> pagerDao) {\r\n";
		javaimplstring+="		super.setPagerDao(pagerDao);\r\n";
		javaimplstring+="	}\r\n";
		javaimplstring+="	@Resource(name = \"generateKeyServcie\")\r\n";
		javaimplstring+="	private IGenerateKeyMangerService generateKeyService;\r\n";
		javaimplstring+="\r\n";
		javaimplstring+="	 @Override\r\n";
		javaimplstring+="	public "+classname+" save("+classname+" entity) {\r\n";
		javaimplstring+="		 String id =  generateKeyService.getNextGeneratedKey(null).getNextKey();\r\n";
		javaimplstring+="		 entity."+primirykey+"(id);\r\n";
		javaimplstring+="	 	 return   this.getBaseDao().save(entity);\r\n";
		javaimplstring+="	    }\r\n";
		javaimplstring+="\r\n";
		javaimplstring+="	 @Override\r\n";
		javaimplstring+="     public "+classname+" update("+classname+" entity) {\r\n";
		javaimplstring+="			return  this.getBaseDao().update(entity);\r\n";
		javaimplstring+="	    }\r\n";
		javaimplstring+="\r\n";
		javaimplstring+="    @Override\r\n";
		javaimplstring+="    public Integer delete(Map<String, Object> params) {\r\n";
		javaimplstring+="        return this.getBaseDao().delete(params); \r\n";
		javaimplstring+="    }\r\n";
		javaimplstring+="\r\n";
		javaimplstring+="}\r\n";
 
		
 
		javaimplstring+="\r\n";
		return javaimplstring;
	}
	
	
	
	
	public static String GeneraterControllor(String packagename,String classname,String lclassname){
		String javacontrollorlstring="package com.idap.web."+packagename+".controller;\r\n";
		javacontrollorlstring+="\r\n";
		javacontrollorlstring+="import javax.annotation.Resource;\r\n";
		javacontrollorlstring+="import org.springframework.stereotype.Controller;\r\n";
		javacontrollorlstring+="import org.springframework.web.bind.annotation.RequestMapping;\r\n";
		javacontrollorlstring+="import com.idp.pub.service.IBaseService;\r\n";
		javacontrollorlstring+="import com.idp.pub.web.controller.BaseController;\r\n";
		javacontrollorlstring+="import com.idap."+packagename+".entity."+classname+";\r\n";
		javacontrollorlstring+="\r\n";
		javacontrollorlstring+="\r\n";
		javacontrollorlstring+="@Controller\r\n";
		javacontrollorlstring+="@RequestMapping(value = \"/"+lclassname+"\")\r\n";
		javacontrollorlstring+="public class "+classname+"Controller extends BaseController<"+classname+", String> {\r\n";
		javacontrollorlstring+="	@Resource(name = \""+lclassname+"Service\")\r\n";
		javacontrollorlstring+="	public void setBaseService(IBaseService<"+classname+", String> baseService) {\r\n";
		javacontrollorlstring+="		super.setBaseService(baseService);\r\n";
		javacontrollorlstring+="	}\r\n";
		javacontrollorlstring+="}\r\n";
		 
 
		
 
		javacontrollorlstring+="\r\n";
		return javacontrollorlstring;
	}
	
	public static void main(String[] args) {
		
		String classname="ClinicInfo"; //entity name
		String lclassname="clinicInfo";//entity name string
		String packagename="clinic";//package name
		String[][] tablearr = {
				                {"clinic_id","String"},
				                {"clinic_name","String"},
				                {"clinic_date","Date"},
				                {"clinic_license","int"}
				              };//database table
		String primirykey="setClinicId"; //id setter
		
	//---------------------------------------------------------------------------	 
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
		String controllerPath="\\src\\com\\idap\\web\\"+packagename+"\\controller\\";
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
		 
		
//javaDao		
		 try {
			   File f = new File(absolutePath+daoPath+classname+"Dao.java");
			   if (!f.exists()) {
			    f.createNewFile();
			   }
			   OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f),"UTF-8");
			   BufferedWriter writer=new BufferedWriter(write);   
			   String javaDaoContent=GeneraterDao(packagefullname,classname,lclassname);
			   writer.write(javaDaoContent);
			   writer.close();
			   System.out.println("写dao文件成功");
			  } catch (Exception e) {
			   System.out.println("写dao文件内容操作出错");
			   e.printStackTrace();
			  }
 

//javaImpl		
		 try {
			   File f = new File(absolutePath+implPath+classname+"ServiceImpl.java");
			   if (!f.exists()) {
			    f.createNewFile();
			   }
			   OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f),"UTF-8");
			   BufferedWriter writer=new BufferedWriter(write);   
			   String javaImplContent=GeneraterImpl(packagename,packagefullname,classname,lclassname,primirykey);
			   writer.write(javaImplContent);
			   writer.close();
			   System.out.println("写impl文件成功");
			  } catch (Exception e) {
			   System.out.println("写impl文件内容操作出错");
			   e.printStackTrace();
			  }
		
//controllor		
		 try {
			   File f = new File(absolutePath+controllerPath+classname+"Controller.java");
			   if (!f.exists()) {
			    f.createNewFile();
			   }
			   OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f),"UTF-8");
			   BufferedWriter writer=new BufferedWriter(write);   
			   String javaControllorContent=GeneraterControllor(packagename,classname,lclassname);
			   writer.write(javaControllorContent);
			   writer.close();
			   System.out.println("写controllor文件成功");
			  } catch (Exception e) {
			   System.out.println("写controllor文件内容操作出错");
			   e.printStackTrace();
			  }
	
	}
}
