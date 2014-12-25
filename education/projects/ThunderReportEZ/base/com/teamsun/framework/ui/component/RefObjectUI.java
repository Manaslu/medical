/*package com.teamsun.framework.ui.component;


public class RefObjectUI
{
	private MetaAttr metaattr; // 属性

	private String value=""; // 属性值

	private int readOnly=0; // 缺省值为1 不能编辑，等于0可以选择，

	private String callBackFunc = ""; // 缺省值为空

	private int  isRefLink=0;//默认是0,不是超连接
	
	private RefFactory refFactory;

	// 根据metaattr中的参照表，参照键，参照值从参照表中读数据，条件是当前的value
	public  String[] readRefObj()
	{
		// 0--id值 1--对应名字
		return this.getRefFactory().getRefCodeByValue(this.metaattr,this.value);
	}

	public String toHtmlString()
	{
		String code = this.metaattr.getCode();
		String size = this.metaattr.getInputcols().toString();  
		String code_text = code+"_text";   
		String name="";
		
		
		if(this.metaattr.getDtype().intValue()== AttrDataType.ID&&this.value==null)
		{
			this.value = "0";
		}
		String id=this.value;
		if(id!="" && id!="0")
		{
			String[] ret = this.readRefObj(); // 以后被缓存代替 
			if(ret!=null&&ret.length>0)
			{
				id=ret[0];
				name= ret[1]; // 输出时注意转换
			}
		}
		//id=外键的id值 ,code=元数据属性的code,code_text=外键的显示值,
		String onclickFunc = "SingleRefObjSelector('"+id+"','"+code+"','"+code_text+"','"+this.callBackFunc+"')";
		StringBuffer sHtml = new StringBuffer("<table cellSpacing=\"0\" cellPadding=\"0\">\n");
		sHtml.append("<tr><td><INPUT TYPE=\"hidden\"  id=\""+code+"\" name=\""+code+"\" value=\""+id+"\"/>");
		sHtml.append("<INPUT TYPE=\"text\" id=\""+code_text+"\" name=\""+code_text+"\" size=\""+size+"\" readOnly=\"false\" value=\""+name+"\"/></td>");
		if(this.readOnly ==0)
		{
			sHtml.append("<td style=\"font-size:60%;font-family:webdings;border:1px solid #666666;border-left:0px;cursor:hand\" onclick=\""+onclickFunc+"\">6</td>");
		}		
		sHtml.append("</tr></table>");
		return sHtml.toString();
	}
		
	public String getCallBackFunc()
	{
		return callBackFunc;
	}

	public void setCallBackFunc(String callBackFunc)
	{
		this.callBackFunc = callBackFunc;
	}

	public MetaAttr getMetaattr()
	{
		return metaattr;
	}

	public void setMetaattr(MetaAttr metaattr)
	{
		this.metaattr = metaattr;
	}

	public int getReadOnly()
	{
		return readOnly;
	}

	public void setReadOnly(int readOnly)
	{
		this.readOnly = readOnly;
	}

	public RefFactory getRefFactory()
	{
		return refFactory;
	}

	public void setRefFactory(RefFactory refFactory)
	{
		this.refFactory = refFactory;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		if(value!=null)
			this.value = value;
	}

}
*/