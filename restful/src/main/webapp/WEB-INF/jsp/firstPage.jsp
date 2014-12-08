<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Person Info</title>
</head>
<body>

	<h1>This is Spring MVC Page</h1>

	<h2>Show default person info</h2>
	<h2>Id: ${person.id}</h2>
	<h2>Name: ${person.name}</h2>
	<h2>Password: ${person.password}</h2>
	
	<c:forEach var="email" items="${person.emails}" >
		<h2>Email: ${email}</h2>
	</c:forEach>

	<br />
	<br />
	<br />
	<br />

	<h3>Author: ${author}</h3>

</body>
</html>