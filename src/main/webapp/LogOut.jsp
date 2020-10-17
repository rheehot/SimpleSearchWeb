<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>로그아웃</title>
</head>
<body>
    <%--
    1. 로그아웃 버튼 클릭 시 현재 페이지(LogOut.jsp)로 이동
    2. 접속 하자 마자 invalidate로 세션 종료 / 로그아웃
    3. 로그아웃 후 welcome 페이지로 바로 이동
    --%>
    <%
        HttpSession userSession = request.getSession();
        userSession.invalidate();
        response.sendRedirect("LogOutMsg.html");
    %>
</body>
</html>
