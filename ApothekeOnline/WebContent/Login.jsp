<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	
	<meta name="description" content="" />
	<meta name="author" content="Gerhard" />
	
	<title>Loginseite</title>
	
	<!-- To ensure proper rendering and touch zooming for mobile -->
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	
	<!-- Bootstrap core CSS -->
	<link
		href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
		rel="stylesheet" />
	
	<!-- Bootstrap theme -->
	<link
		href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css"
		rel="stylesheet" />
	
	<title>Login</title>
</head>
<body>
	<div class="container theme-showcase">
		<div class="jumbotron">
			<img src="Bilder/aon_logo.png" alt="" height="123" width="600"/>
			<h2>Login</h2>
			<p>

				Kunde: (user:nessi | passwort:hallo)<br /> Mitarbeiter:
				(user:gertsch21 | passwort:hallo)
			</p>
<%
				if (request.getSession().getAttribute("fehler") != null) {
			%>
					<h2><%=request.getSession().getAttribute("fehler")%></h2>
			<%
					request.getSession().setAttribute("fehler", null); //nach ausgabe auf null setzen
				}
			%>
			<br />
			<form action="Logincontroller" method="POST">
				<table>
					<tr>
						<td><b>Username:</b></td>
						<td><input type="text" name="username" /></td>
					</tr>
					<tr>
						<td><b>Passwort:</b></td>
						<td><input type="password" name="password" /><br /></td>
					</tr>
					<tr>
						<td><input class="btn btn-primary" type="submit"
							value="Login" /></td>
					</tr>
				</table>
			</form>
			<br />
			<form action="Registriercontroller" method="GET">
				<input class="btn btn-primary" type="submit"
					value="Als Kunde Registrieren" />
			</form>

		</div>
	</div>
</body>
</html>