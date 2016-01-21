<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>second page</title>
</head>

<%
	String contextPath=request.getContextPath();
	MyUser user=(MyUser)session.getAttribute(GlobalKeys.KEY_AUTH_USER);
	user.setOrChoseCurrentSystem(user.getCurrentSystem());
%>
<script>
    if (!window.console) window.console = {log: function() {}};
    
    // NOW pass java variables to javascript variables        
    var LABELS={
    		clientLabel:'<%=clientLabel%>',
    		eventLabel:'<%=eventLabel%>'
    }; 
</script>
<body>

	<h1>This is placeholder of first page</h1>
	


	<br />
	<br />

</body>
</html>