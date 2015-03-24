package com.idap;


 

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.idp.workflow.itf.service.runtime.IWorkFlowRunTime;

 

 

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
	
	public static String GeneraterIbatis(List table,String packagename,String packagefullname,String classname,String lclassname,String tablename){
	    int morethanone =0; 
		for (int i = 0; i < table.size(); i++) {
			Map<String, String> m = (Map)table.get(i);
			if( m.get("filter").equals("1")){
				morethanone++;
			}
	    }
		 Map<String, String> keymap = (Map)table.get(0);//primiry key
		
		String javabatisstring="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>   \r\n";
		javabatisstring+="<!DOCTYPE mapper    PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"    \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">  \r\n";
		javabatisstring+="<mapper namespace=\""+packagefullname+".entity."+classname+"\" >\r\n";
		javabatisstring+="    <resultMap id=\""+classname+"\" type=\""+packagefullname+".entity."+classname+"\">\r\n";
	    for (int i = 0; i < table.size(); i++) {
		   Map<String, String> m = (Map)table.get(i);
		   // replaceslash((String)m.get(\"column\"))   (String)m.get("column").toUpperCase()  (String)m.get("xmltype")
		   String ii =  replaceslash( m.get("column"));
		    javabatisstring+= "		<result property=\""+replaceslash( (String)m.get("column"))+"\" column=\""+(String)m.get("column").toUpperCase()+"\" jdbcType=\""+(String)m.get("xmltype")+"\" />\r\n";
        }
		javabatisstring+="    </resultMap>\r\n";
		javabatisstring+="\r\n";
		javabatisstring+="\r\n";
		javabatisstring+="	<sql id=\"queryBody\">\r\n";
		javabatisstring+="		<if test=\"sno!=null and eno!=null and orderBy!=null\">\r\n";
		javabatisstring+="			select\r\n";
		javabatisstring+="			<include refid=\"allColumn\" />\r\n";
		javabatisstring+="			 from\r\n";
		javabatisstring+="			"+tablename+"  t\r\n";
		javabatisstring+="			<include refid=\"dynamicWhere\" />\r\n";
		javabatisstring+="			<include refid=\"dynamicOrderBy\" />\r\n";
		javabatisstring+="			limit ${sno-1},${eno}\r\n";
		javabatisstring+="		</if>\r\n";
		javabatisstring+="		<if test=\"sno!=null and eno!=null and orderBy==null\">\r\n";
		javabatisstring+="			select\r\n";
		javabatisstring+="			<include refid=\"allColumn\" />\r\n";
		javabatisstring+="			 from\r\n";
		javabatisstring+="			"+tablename+" t\r\n";
		javabatisstring+="			<include refid=\"dynamicWhere\" />\r\n";
		javabatisstring+="			limit ${sno-1},${eno}\r\n";
		javabatisstring+="		</if>\r\n";
		javabatisstring+="	</sql>	\r\n";
		javabatisstring+="\r\n";
		javabatisstring+="\r\n";
		javabatisstring+="   <sql id=\"allColumn\">\r\n";
	    for (int i = 0; i < table.size(); i++) {
		   Map<String, String> m = (Map)table.get(i);
		   if(i==table.size()-1){
			   javabatisstring+="	           t."+(String)m.get("column").toUpperCase()+"\r\n";
		   }else{
			   javabatisstring+="	           t."+(String)m.get("column").toUpperCase()+",\r\n";
		   }
        }
		javabatisstring+="	 </sql>\r\n";
		javabatisstring+="\r\n";
		javabatisstring+="\r\n";	 
		javabatisstring+=" 	<select id=\"query\" parameterType=\"java.util.Map\" resultMap=\""+classname+"\">\r\n";
		javabatisstring+="		select \r\n";
		javabatisstring+="        <include refid=\"allColumn\" />\r\n";
		javabatisstring+="	    from "+tablename+" t\r\n";
		javabatisstring+="	    <include refid=\"dynamicWhere\" />\r\n";
		javabatisstring+="	    <sql id=\"dynamicOrderBy\">\r\n";
		javabatisstring+="	</select>\r\n";
		javabatisstring+="\r\n";
		javabatisstring+="\r\n"; 
		javabatisstring+="	<select id=\"findByPager\" parameterType=\"java.util.Map\" resultMap=\""+classname+"\">\r\n";
		javabatisstring+="		<include refid=\"queryBody\" />\r\n";
		javabatisstring+="	</select>\r\n";
		javabatisstring+="\r\n";
		javabatisstring+="\r\n";	 
		javabatisstring+="	<select id=\""+lclassname+"query\" parameterType=\"java.util.Map\" resultMap=\""+classname+"\">\r\n";
		javabatisstring+="		<include refid=\"queryBody\" />\r\n";
		javabatisstring+="	</select>\r\n";
		javabatisstring+="\r\n";
		javabatisstring+="\r\n";
		javabatisstring+="	<select id=\"findByPagerCount\" parameterType=\"java.util.Map\" resultType=\"int\" >\r\n";
		javabatisstring+="		select count(1) from "+tablename+" \r\n";
		javabatisstring+="		<include refid=\"dynamicWhere\" />\r\n";
		javabatisstring+="	</select>\r\n";	
		javabatisstring+="\r\n";
		javabatisstring+="\r\n";
		javabatisstring+="	<sql id=\"dynamicWhere\">\r\n";
		javabatisstring+="			<trim prefix=\"WHERE\" prefixOverrides=\"AND|OR\">\r\n";
		javabatisstring+="				<if test=\"querykeyword!=null\">\r\n";
	      if(morethanone==1){
	    	  for (int i = 0; i < table.size(); i++) {
	   		   Map<String, String> m = (Map)table.get(i);
	   		   if(m.get("filter").equals("1")){// only one String filter
	   			   javabatisstring+="					AND ("+(String)m.get("column").toUpperCase()+" like concat('%',#{querykeyword},'%')) \r\n";
	   		   }
	    	  }
	    	  }else if(morethanone>1){
	    		  int flag=0;
	    		  javabatisstring+="					AND (\r\n";
	    		  for (int i = 0; i < table.size(); i++) {
	   	   		    Map<String, String> m = (Map)table.get(i);
			   	   		if(m.get("filter").equals("1")){
			   	   		 
			   	   		 if(flag>0){
			   	   		       javabatisstring+="					 OR    "+(String)m.get("column").toUpperCase()+" like concat('%',#{querykeyword},'%') \r\n";
			   	   		 }else{
			   	   		       javabatisstring+="					       "+(String)m.get("column").toUpperCase()+" like concat('%',#{querykeyword},'%') \r\n";
			   	   		 }
			   	   		}
	   	   		   }
	    		  javabatisstring+="					    )\r\n";
	    		 
	    	  
	      }
 
		javabatisstring+="				</if>\r\n";
	    for (int i = 0; i < table.size(); i++) {
		   Map<String, String> m = (Map)table.get(i);
		   if       (m.get("filter")=="2"){//=
				javabatisstring+="				<if test=\""+replaceslash( (String)m.get("column"))+"!=null\">\r\n";
				javabatisstring+="					AND "+(String)m.get("column").toUpperCase()+" = #{"+replaceslash( (String)m.get("column"))+"}\r\n";
				javabatisstring+="				</if>\r\n";
			   
		   }else if(m.get("filter")=="3"){//>=
				javabatisstring+="				<if test=\""+replaceslash( (String)m.get("column"))+"!=null\">\r\n";
				javabatisstring+="					AND "+(String)m.get("column").toUpperCase()+" <![CDATA[>=]]> #{"+replaceslash( (String)m.get("column"))+"}\r\n";
				javabatisstring+="				</if>\r\n";
			    
		   }else if(m.get("filter")=="4"){//<
				javabatisstring+="				<if test=\""+replaceslash( (String)m.get("column"))+"!=null\">\r\n";
				javabatisstring+="					AND "+(String)m.get("column").toUpperCase()+" <![CDATA[<]]> #{"+replaceslash( (String)m.get("column"))+"}\r\n";
				javabatisstring+="				</if>\r\n";
			    
		   }else if(m.get("filter")=="5"){//date =
			   javabatisstring+="				<if test=\""+replaceslash( (String)m.get("column"))+"!=null\">\r\n";
				javabatisstring+="					AND "+(String)m.get("column").toUpperCase()+"  <![CDATA[=]]> to_date('${"+replaceslash( (String)m.get("column"))+"}', 'yyyy-mm-dd')  \r\n";
				javabatisstring+="				</if>\r\n";
			    
		   }else if(m.get("filter")=="6"){//date range
			    javabatisstring+="				<if test=\""+replaceslash( (String)m.get("column"))+"1!=null and "+replaceslash( (String)m.get("column"))+"2!=null\">\r\n";
				javabatisstring+="					AND (("+(String)m.get("column").toUpperCase()+"  <![CDATA[<=]]> to_date('${"+replaceslash( (String)m.get("column"))+"1}', 'yyyy-mm-dd')\r\n";
				javabatisstring+="					AND "+(String)m.get("column").toUpperCase()+" <![CDATA[>]]> to_date('${"+replaceslash( (String)m.get("column"))+"2}', 'yyyy-mm-dd') )\r\n";
				javabatisstring+="				</if>\r\n";
			    
		   }else if(m.get("filter")=="7"){//>
				javabatisstring+="				<if test=\""+replaceslash( (String)m.get("column"))+"!=null\">\r\n";
				javabatisstring+="					AND "+(String)m.get("column").toUpperCase()+" <![CDATA[>]]> #{"+replaceslash( (String)m.get("column"))+"}\r\n";
				javabatisstring+="				</if>\r\n";
			    
		   }else if(m.get("filter")=="8"){//<=
				javabatisstring+="				<if test=\""+replaceslash( (String)m.get("column"))+"!=null\">\r\n";
				javabatisstring+="					AND "+(String)m.get("column").toUpperCase()+" <![CDATA[<=]]> #{"+replaceslash( (String)m.get("column"))+"}\r\n";
				javabatisstring+="				</if>\r\n";
			    
		   } 
        }
 
		javabatisstring+="			</trim>\r\n";
		javabatisstring+="	</sql>\r\n";
		javabatisstring+="\r\n";
		javabatisstring+="\r\n";
		javabatisstring+="	<sql id=\"dynamicOrderBy\">\r\n";
		javabatisstring+="		<if test=\"orderBy!=null\">\r\n";
		javabatisstring+="			order by ${orderBy}\r\n";
		javabatisstring+="			<if test=\"order!=null\">\r\n";
		javabatisstring+="				${order}\r\n";
		javabatisstring+="		</if>\r\n";
		javabatisstring+="		</if>\r\n";
		javabatisstring+="	</sql>\r\n";
		javabatisstring+="\r\n";
		javabatisstring+="\r\n";
		javabatisstring+="	<insert id=\"insert\" parameterType=\""+packagefullname+".entity."+classname+"\" >\r\n";
		javabatisstring+="	    INSERT INTO \r\n";
		javabatisstring+="	    "+tablename+"(<include refid=\"allapplColumn\"/>	)\r\n";
		javabatisstring+="		<include refid=\"allapplColumnVar\"/>\r\n";
		javabatisstring+="	</insert>\r\n";
		javabatisstring+="\r\n";
		javabatisstring+="\r\n";
		javabatisstring+="	<sql id=\"allapplColumn\">\r\n";
	    for (int i = 0; i < table.size(); i++) {
		   Map<String, String> m = (Map)table.get(i);
		   if(i==table.size()-1){
			   javabatisstring+="	           "+(String)m.get("column").toUpperCase()+"\r\n";
		   }else{
			   javabatisstring+="	           "+(String)m.get("column").toUpperCase()+",\r\n";
		   }
        }
		javabatisstring+="	</sql>\r\n";
		javabatisstring+="\r\n";
		javabatisstring+="\r\n";
		javabatisstring+="	<sql id=\"allapplColumnVar\">\r\n";
		javabatisstring+="		values(\r\n";
	    for (int i = 0; i < table.size(); i++) {
		   Map<String, String> m = (Map)table.get(i);
		   if(i==table.size()-1){
			   javabatisstring+="		#{"+replaceslash( (String)m.get("column"))+",jdbcType="+(String)m.get("xmltype")+"}\r\n";
		   }else{
			   javabatisstring+="		#{"+replaceslash( (String)m.get("column"))+",jdbcType="+(String)m.get("xmltype")+"},\r\n";
		   }
        }
		javabatisstring+="		     )\r\n";
		javabatisstring+="	</sql>\r\n";
		javabatisstring+="\r\n";
		javabatisstring+="\r\n";
		javabatisstring+="	<update id=\"update\" parameterType=\""+packagefullname+".entity."+classname+"\">\r\n";
		javabatisstring+="			update "+tablename+"\r\n";
		javabatisstring+="			<trim prefix=\" SET \" prefixOverrides=\",\">\r\n";


	    for (int i = 1; i < table.size(); i++) {
		   Map<String, String> m = (Map)table.get(i);
			   javabatisstring+="		        <if test=\""+replaceslash( (String)m.get("column"))+"!=null\">\r\n";
			   javabatisstring+="			         ,"+(String)m.get("column").toUpperCase()+"=#{"+replaceslash( (String)m.get("column"))+",jdbcType="+(String)m.get("xmltype")+"}\r\n";
			javabatisstring+="                  </if>\r\n";
        }
		javabatisstring+="			</trim>\r\n";
		javabatisstring+="			WHERE \r\n";
		javabatisstring+="				"+(String)keymap.get("column").toUpperCase()+"=#{"+replaceslash( (String)keymap.get("column"))+"}\r\n";
		javabatisstring+="	</update>\r\n";
		javabatisstring+="\r\n";
		javabatisstring+="\r\n";
		javabatisstring+="	<delete id=\"delete\" parameterType=\"java.util.Map\">\r\n";
		javabatisstring+="		DELETE FROM "+tablename+" \r\n";
		javabatisstring+="		where "+(String)keymap.get("column").toUpperCase()+" =#{"+replaceslash( (String)keymap.get("column"))+"}\r\n";
		javabatisstring+="	</delete>	\r\n";
		javabatisstring+="\r\n";
		javabatisstring+="\r\n";
		javabatisstring+="</mapper>\r\n";
		return javabatisstring;
	}	
public static void main(String[] args) {
		
		String classname="ClinicInfo"; //entity name
		String lclassname="clinicInfo";//entity name string
		String packagename="clinic";//package name
		String tablename="CLINIC";//table name in database
		String htmlfilename="clinicadmin";
		String primirykey="setClinicId"; //id setter in impl.java
		
	//---------------------------------------------------------------------------	
		//主键放在第一个; 模糊查询的列(标号1)排在一起;   querykeyword为页面的字符串     去掉一个or
		//列名,java类型,ibatis类型,where条件类别(0:不参与where,1:字符串模糊,2:精确,3:大于等于,4:小于,5:日起精确,6:日起区间,7:<,8:>=)
		//html类别(0:不显示不维护的列,1:单行文本,2:多行文本区,3:日期,4:枚举,5:文件)
		String[][] tablearr = {
				                {"clinic_id",      "String",  "VARCHAR", "2","0"},
				                {"clinic_name",    "String",  "VARCHAR", "1","1"},
				                {"clinic_address", "String",  "VARCHAR", "1","2"},
				                {"clinic_date",    "Date",    "DATE",    "6","3"},
				                {"clinic_pic",     "String",  "VARCHAR", "0","5"},
				                {"clinic_level",   "String",  "VARCHAR", "2","4"},
				                {"clinic_license", "int",     "NUMERIC", "4","1"}
				              };//database table
	 
		ArrayList table = new ArrayList();
		for(int i=0;i<tablearr.length;i++){
			Map<String, String> map = new HashMap<String, String>();
			map.put("column", tablearr[i][0]);
			map.put("type", tablearr[i][1]);
			map.put("xmltype", tablearr[i][2]);
			map.put("filter", tablearr[i][3]);
			map.put("control", tablearr[i][4]);
			table.add(map);
		}
		String packagefullname="com.idap."+packagename;
		String entityPath="\\src\\com\\idap\\"+packagename+"\\entity\\";
		String daoPath = "\\src\\com\\idap\\"+packagename+"\\dao\\";
		String implPath="\\src\\com\\idap\\"+packagename+"\\service\\impl\\";
		String controllerPath="\\src\\com\\idap\\web\\"+packagename+"\\controller\\";
		String xmlPath = "\\src\\resources\\com\\idap\\"+packagename+"\\entity\\mapping\\";
		String htmlPath="\\templates\\"+packagename+"\\";
		String jsPath="\\application\\controller\\"+packagename+"\\";
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
		 
//ibatis xml		
		 try {
			   File f = new File(absolutePath+xmlPath+classname+".xml");
			   if (!f.exists()) {
			    f.createNewFile();
			   }
			   OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f),"UTF-8");
			   BufferedWriter writer=new BufferedWriter(write);   
			   String xmlContent=GeneraterIbatis(table,packagename,packagefullname,classname,lclassname,tablename);
			   writer.write(xmlContent);
			   writer.close();
			   System.out.println("写ibatis文件成功");
			  } catch (Exception e) {
			   System.out.println("写ibatis文件内容操作出错");
			   e.printStackTrace();
			  }	
//left work		 
		 System.out.println("service.js:  app.factory('"+classname+"', [ '$resource', function($resource) {return $resource('"+lclassname+".shtml');} ]);	");
		 System.out.println(classname+".java add: setter and getter  ");
	}
}
