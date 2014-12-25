<%@page contentType="text/html; charset=utf-8" import="java.io.*"%>

<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Insert title here</title>
</head>
<body>
<form action="ServletDemo" method="post"><input type="submit"
	value="生成文件" />

<table>
	<%
		Boolean b = (Boolean) request.getAttribute("fboolean");
		if (b == null)
			return;

		File[] files = (File[]) request.getAttribute("files");
		if (files == null)
			return;

		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			if (!b.booleanValue()) {
	%>
	<tr>
		<td><a href="ServletFileList?d=<%=f.getName()%>"><%=f.getName()%></a>
	</tr>
	<%
	} else {
	%>
	<tr>
		<td><a href="pages/report/<%=request.getAttribute("dir")%>/<%=f.getName()%>"><%=f.getName()%></a></td>
	</tr>
	<%
		}
		}
	%>
</table>
</form>
</body>
</html>
