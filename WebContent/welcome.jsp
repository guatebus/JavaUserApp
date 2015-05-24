<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<%
	String userName = null;
	String sessionID = null;
	Cookie[] cookies = request.getCookies();
	if(cookies !=null){
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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
		<title>Welcome <%=userName %></title>
	</head>
	<body>
		<h3>Hi user <%=userName %></h3>
		
			<p><a href="/resources?p=1">page 1</a> - PAG_1 role required</p>
			<p><a href="/resources?p=2">page 2</a> - PAG_2 role required</p>
			<p><a href="/resources?p=3">page 3</a> - PAG_3 role required</p>
		
			<br>
		<form action="logout-servlet" method="post">
			<input type="submit" value="Logout" >
		</form>
			<br>
		<span>session <%=sessionID %></span>
	</body>
</html>