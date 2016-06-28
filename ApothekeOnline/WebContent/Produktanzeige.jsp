<%@page import="model.Produkt"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	if (session.getAttribute("username") == null
			|| session.getAttribute("username").equals("null") || session.getAttribute("alleKunden")==null) {
		System.out
				.println("HauptseiteMitarbeiter: nicht eingeloggt -> Login");
		response.sendRedirect("Login.jsp");
	}
%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta name="viewport" content="width=device-width, initial-scale=1"/>
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content=""/>
<meta name="author" content="Gerhard"/>

<title>ProduktAnzeige</title>

<!-- To ensure proper rendering and touch zooming for mobile -->
<meta name="viewport" content="width=device-width, initial-scale=1"/>

<!-- Bootstrap core CSS -->
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet"/>

<!-- Bootstrap theme -->
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css"
	rel="stylesheet"/>

</head>
<body>
	<div class="container theme-showcase" role="main">
		<div class="jumbotron">
			<h1>Produkte:</h1>

<!-- Möglichen Fehlertext ausgeben -->
			<%
				if (request.getSession().getAttribute("fehler") != null) {
			%>
			<h2><%=request.getSession().getAttribute("fehler")%></h2>
			<%
					request.getSession().setAttribute("fehler", null); //nach ausgabe auf null setzen
				}
			%>


<!-- Eingaben zum Registrieren -->
			<form action="Produktverwaltungscontroller" method="POST">
				<table class="table">
					<tr><th>Name</th><th>Preis</th><th>löschen</th></tr>
<%for(Produkt p : (List<Produkt>)(session.getAttribute("alleProdukte")) ){ %>
					<tr><td><%=p.getprodName()%></td><td><%=p.getprice()%></td><td><input type="submit" name="zuLoeschen" value="<%=p.getprodID()%>"/></td></tr>
<%} %>
</table>	
Anzahl an Produkte: <%=( (List<Produkt>)(session.getAttribute("alleProdukte")) ).size()%>			
			</form>

<!-- Einfaches Retour zur Hauptseite -->
			<form method="post" action="Produktverwaltungscontroller">
			    <button name="beendeProduktverw" type="submit">Back</button>
			</form>

		</div>
	</div>
</body>
</html>