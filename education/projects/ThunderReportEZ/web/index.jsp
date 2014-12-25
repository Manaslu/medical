<%@page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<style type="css" dir="css/portlet.css"></style>

<style type="text/css">
<!-- 
	input{BACKGROUND: #CADEFA;border:1 solid black;FONT-SIZE: 9pt; FONT-STYLE: normal; FONT-VARIANT: normal; FONT-WEIGHT: normal; HEIGHT: 18px; LINE-HEIGHT: normal}
-->
</style>

<title></title>
<script language="javascript">
	function fun1()
	{
		document.form.action="RepresentAction?index=2";
		document.form.submit();
	}
	function fun2()
	{
		document.form.action="RepresentAction?index=3";
		document.form.submit();
	}
	function fun3()
	{
		document.form.action="RepresentAction?index=9";
		document.form.submit();
	}
</script>
</head>
<body>
<form name="form" action="RepresentAction" method="post">
<table border="0" cellspacing="0" cellpadding="0" width="50%">
	<tr>
		<td><input type="button" name="c1" value="预生成文件" onclick="fun1()" /></td>
	</tr>
	<tr>
		<td></td>
	</tr>
	<tr>
		<td><input type="button" name="c2" value="单个生成文件" onclick="fun2()" /></td>
	</tr>
	<tr>
		<td><input type="button" name="c2" value="刷新配置文件" onclick="fun3()" /></td>
	</tr>
</table></form>
</body>
</html>