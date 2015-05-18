<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Resource Page</title>
</head>
<body>
<%
String user = (String) session.getAttribute("user");
String userName = null;
String sessionID = null;
Cookie[] cookies = request.getCookies();
if(cookies != null){
	for(Cookie cookie : cookies){
	    if(cookie.getName().equals("user")) {
	    	userName = cookie.getValue();
	    }
	    if(cookie.getName().equals("JSESSIONID")) {
	    	sessionID = cookie.getValue();
	    }
	}
}
%>
<h3>Hola <%=userName %></h3>
<br>
<h3>This is page: Page number!!</h3>
<br>
<form action="/logout-servlet" method="post">
<input type="submit" value="Logout" >
</form>
</body>
</html>