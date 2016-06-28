<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CheckCart</title>
</head>
<body>

	
	<div class="product">
	<form name="checkCart" method="POST" action="KassaController">
		<table class="checkCart">
			<tr>
				<th>Produkt-ID</th>
				<th>Bezeichnung</th>
				<th>Menge</th>
				<th>Einzelpreis</th>
				<th>Preis</th>
			</tr>
		 <% 
			 session = request.getSession();
			 if(session.getAttribute("checkCart") != null) {
				 out.print(session.getAttribute("checkCart"));
			 } else {
				 out.print("Ihr Warenkorb ist leer!");
			 }
	 	%>
	 	</table>
	 	<br><br>
	 	<input class="btnGreen" type="submit" value="Warenkorb aktualisieren" />
	 </form>
	 <form name="order" method="GET" action="BestellController">	
	 	<input class="btnGreen" type="submit" value="Bestellung abschicken" />
	 </div>
</body>
</html>