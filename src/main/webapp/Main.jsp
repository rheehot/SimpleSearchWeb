<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%
        if (session.getAttribute("userId") != null) {
            String userId = (String) session.getAttribute("userId");
    %>
        <h1><%=userId%> 님 환영합니다.</h1>
    <%
        } else {
    %>
    <%
            response.sendRedirect("/SignIn.html");
        }
    %>
</body>
</html>
