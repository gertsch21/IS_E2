<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Warenkorb</title>

</head>
<body>
 
 <% 
 session = request.getSession();
 if(session.getAttribute("cartOut") != null) {
	 out.print(session.getAttribute("cartOut"));
 } else {
	 out.print("Ihr Warenkorb ist leer!");
 }
 
 
 %>
 
</body>
</html>