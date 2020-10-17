<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="MemberServlet" method="post">
        <table>
            <tr>
                <td>아이디</td>
                <td><label><input type="text" name="userId"></label></td>
            </tr>
            <tr>
                <td>비밀번호</td>
                <td><label><input type="password" name="userPw"></label></td>
            </tr>
        </table>
        <input type="submit" value="로그인" />
        <input type="hidden" name="command" value="login" />
    </form>
</body>
</html>
