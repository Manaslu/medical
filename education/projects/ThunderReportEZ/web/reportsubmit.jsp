<?xml version="1.0" encoding="utf-8" ?>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page language="java"
	import="com.teamsun.thunderreport.parse.Condition"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="css" dir="css/portlet.css"></style>
<title></title>
</head>
<body>
<form id="form1" action="RepresentAction?index=8" method="post">
<table border="1" cellspacing="0" cellpadding="0" width="50%">
	<tr>
		<td colspan="4"><%=request.getAttribute("filename")%></td>
	</tr>
	<%
		Condition[] conds = (Condition[]) request.getAttribute("conds");
		if (conds != null && conds.length > 0)
			for (int i = 0; i < conds.length; i++) {
	%><tr>
		<%
		Condition cond = conds[i];
		%>
		<td>id：<%=cond.getId()%></td>
		<td>title:<%=cond.getTitle()%></td>
		<td>paraname:<%=cond.getParaname()%></td>
		<td><input type="text" name="<%=cond.getParaname()%>" /></td>
	</tr>
	<%
	}
	%>
	<tr>
		<td><input type="submit" value="查询表格" onclick="doProcess(8)"/><input
			type="hidden" value="<%=request.getAttribute("filename") %>"
			name="filename" /></td>
		<td><input type="submit" value="查询图形" onclick="doProcess(9)"/><input
			type="hidden" value="<%=request.getAttribute("filename") %>"
			name="filename" /></td>	
		<td colspan="4"><input type="submit" value="预生成" onclick="doProcess(7)"/></td>
		
	</tr>
</table>

</form>
</body>
</html>
<script laguage="javascript">

function doProcess(index){
	alert(index);
	//document.all("form1").action="RepresentAction?index="+index;
	//document.all("form1").submit();
	document.getElementById("form1").action="RepresentAction?index="+index;
	document.getElementById("form1").submit();

}

</script>

