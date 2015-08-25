<%
  		
String pages = request.getParameter("page");

System.out.println("pages: "+pages);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Insert title here</title>
<style>
body ,html{
	height: 100%;
	margin:0;
	padding:0;
}
</style>
</head>
<body>
<iframe src="<%=pages%>" width="100%" height="99%" scrolling="auto" frameborder="0" />
</body>
</html>