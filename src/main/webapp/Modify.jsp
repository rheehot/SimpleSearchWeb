<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%-- 세션 유지 --%>
<%
    if (session.getAttribute("userId") != null) {
%>
<form action="MemberServlet" method="post">
    <table>
        <tr>
            <%-- 비밀번호 수정 시 id 값은 그대로 들고 --%>
            <td><label>아이디: <%=session.getAttribute("userId")%> </label></td>
        </tr>
        <tr>
            <td><label for="userPw">기존 비밀번호: </label></td>
            <td><label><input type="password" name="userPw" id="userPw"></label></td>
        <tr>
        <tr>
            <td><label for="modifyPw">변경할 비밀번호: </label></td>
            <td><label><input type="password" name="modifyPw" id="modifyPw"></label></td>
        <tr>
        <tr>
            <td><input type="submit" value="비밀번호 수정"><td>
            <td><label><input type="hidden" name="command" value="modify" /></label></td>
        </tr>
    </table>
</form>
<%
    }
%>
</body>
</html>
