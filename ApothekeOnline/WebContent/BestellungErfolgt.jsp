<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	if(session.getAttribute("username")==null || session.getAttribute("username").equals("null")  ){
		System.out.println("HauptseiteMitarbeiter: nicht eingeloggt -> Login");
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

<title>KundenHauptseite</title>

<!-- Bootstrap core CSS -->
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet"/>
<link href="<%=request.getContextPath()%>/css/custom.css" rel="stylesheet" media="screen">
<!-- Bootstrap theme -->
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css"
	rel="stylesheet"/>

</head>
<body>

<!-------- HEADER -------->
	<div class="container wrap sidebar-right">
		<div class="row"> 
			<div class="col-md-12"> 
				<div class="jumbotron">

					<h1>Apotheke Online</h1>
					<h3>Herzlich Willkommen <%=session.getAttribute("username")%>!</h3> <!-- wenn null, dann darf man sowieso nicht auf die Hauptseite zugreifen -->

<%
	if(session.getAttribute("message")!=null){ %>
		<h2>Neue Meldung: <%=session.getAttribute("message") %></h2>
<% 		request.getSession().setAttribute("message", null);
	} 
	if(request.getSession().getAttribute("fehler")!=null){ %> 
		<h2>Achtung Fehler aufgetreten: <%=request.getSession().getAttribute("fehler")%></h2>
<% 		request.getSession().setAttribute("fehler", null);
	} 
%>
					<!-- Suchfeld -->
					<div class="col-md-10">
						<form class="navbar-form navbar-left"
							action="SucheProduktController" method="post">
								<input class="form-control" name="suchwert" type="text" size="80" />
								<input class="btnGreen" type="submit" value="Produkt suchen" />
						</form>
					</div>
					
					<!-- Logout-Button -->
					<div class="col-md-2">
						<form action="Logincontroller" method="GET">
							<input class="btnGreen" name="logout" type="submit"
								value="Logout" />
						</form>
					</div>
				</div> <!-- End jumbotron -->
			</div> 
		</div><!-- End HEADER -->
		
<!-- Container für Hauptbereich: große Spalte für Produkte + kleine für Warenkorb -->		
		<div class="container"> 
			<div class="row">
				 <div class="col-md-8"> <!-- Hauptspalte -->
				 	<div class="product">
				 		<h2>Vielen Dank für Ihre Bestellung!</h2>
				 		<h3>Ihr Auftrag wurde erfolgreich gespeichert.</h3>
				 		<br><br>
				 		
				 		<form action="ShopController" method="GET">
										<input class="btnGreen" type="submit" 
											value="zurück zur Startseite" />
										
						</form>
				 	</div>
				 	
				 </div> <!-- Ende Hauptspalte -->
				 
				 <!-------- Sidebar für Warenkorb -------->				 
				 <div class="col-md-4"> 
				 	<div class="sidebar-nav-fixed affix">
							 
		                   <div class="panel panel-cart">
						      <div class="panel-heading">Kundenservice</div>
						      <div class="panel-body">
						      	<p> Bei Fragen zu Ihrer Bestellung
						      	erreichen Sie uns zum Ortstarif
						        unter 01/ 238 852 </p>
						      </div>
						    </div>
	  
           			</div>
				 </div> <!-- Ende Sidebar für Warenkorb -->
			</div>
		</div> <!-- Ende Container Hauptbereich -->
		
<!-------- Footer -------->		
		<div class="row"> 
			<div class="col-md-12">
				(c) Apotheke Online
			</div>
		</div> <!-- Ende Footer -->
	</div>
		
        
</body>
</html>