<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" import="java.io.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>文件列表</title>
</head>
<body>
<table border="1" cellspacing="0" width="80%">
	<%
		Boolean bool = (Boolean) request.getAttribute("fboolean");
		if (bool == null || !bool.booleanValue())
			request.getRequestDispatcher("RepresentAction").forward(
			request, response);
		else {
			File[] files = (File[]) request.getAttribute("files");
			if (files == null || files.length == 0)
				out.print("没有数据");
			else {
	%>

	<thead>
		<tr>
			<td align="center">所有配置文件</td>
		</tr>
	</thead>

	<%
			for (int i = 0; i < files.length; i++) {
			File f = files[i];
	%>

	<tr>
		<td><a href="RepresentAction?filename=<%=f.getName()%>&index=4"><%=f.getName()%></a></td>
	</tr>

	<%
			}
			}
		}
	%>
</table>

</body>
</html>