<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	if (session.getAttribute("username") == null
			|| session.getAttribute("username").equals("null")) {
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
	
	<meta name="description" content=""/>
	<meta name="author" content="Gerhard"/>
	
	<title>MitarbeiterHauptseite</title>
	
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

	<!-- Fixed navbar -->
	<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="https://www.univie.ac.at/en/">University
				of Vienna</a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<li><a href="http://royalcoconut.bplaced.net/">bPlaced</a></li>
				<li><a href="http://homepage.univie.ac.at/a1363761">Homepage1</a></li>
				<li class="active"><a
					href="http://wwwlab.cs.univie.ac.at/~a1363761/">Homepage2</a></li>
				<li><a
					href="http://tomcat01lab.cs.univie.ac.at:31637/IBuy/login">SWE</a></li>
				<li><a href="http://wwwlab.cs.univie.ac.at/~a1363761/dbs">DBS2015</a></li>
			</ul>
		</div>
		<!--/.nav-collapse -->
	</div>
	</nav>






	<div class="container theme-showcase" role="main">

		<!-- Main jumbotron for a primary marketing message or call to action -->
		<div class="jumbotron">

			<h1>
				Willkommen <%=session.getAttribute("username")%></h1> <!-- wenn null, dann darf man sowieso nicht auf die Hauptseite zugreifen --> 

<%
	if(session.getAttribute("message")!=null){ %>
		<h2>Neue Meldung: <%=session.getAttribute("message") %></h2>
<% 		session.setAttribute("message", null);
	} 
	if(session.getAttribute("fehler")!=null){ %> 
		<h2>Achtung Fehler aufgetreten:<%=session.getAttribute("fehler")%></h2>
<% 		session.setAttribute("fehler", null);
	} 
%>


			<div>
				<form class="navbar-form navbar-left"
					action="SucheProduktController" method="post">
					<div class="form-group">
						<b>Produkt:</b> <input class="form-control" name="suchwert"
							type="text" size="80" />
					</div>
					<input class="btn btn-primary" type="submit" value="suchen" />
				</form>
				
				
				<br /> <br />
				
				<form action="Logincontroller" method="GET">
					<input class="btn btn-primary" name="logout" type="submit"
						value="Logout" />
				</form>	
				

			</div>
				<form action="MitarbeiterRegistrierController" method="POST">
					<input class="btn btn-primary" name="mitarbeiterReg" type="submit" value="Mitarbeiter registrieren"/>
					<input type="hidden" name="regBest" value="true"/>
				</form>
				<form action="Benutzerverwaltungscontroller" method="POST">
					<input class="btn btn-primary" name="mitarbeiterReg" type="submit" value="Kunden verwalten"/>
					<input type="hidden" name="regBest" value="true"/>
				</form>
			</div>

		</div>
</body>
</html>
