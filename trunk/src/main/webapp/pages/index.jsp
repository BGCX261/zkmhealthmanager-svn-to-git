<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Healthmanager 2.0</title>
</head>
<body>
	<form action="<%= request.getContextPath() %>/iniciarSesionServlet" method="post" >
		<table>
			<tr>
				<td>
					<label>Usuario</label>
				</td>
				<td>
					<input name="login" type="text" />
				</td>
			</tr>
			<tr>
				<td>
					<label>Password</label>
				</td>
				<td>
					<input name="password" type="password" />
				</td>
			</tr>
			<tr>
				<td colspan="2" >
					<input type="submit" value="Iniciar" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>