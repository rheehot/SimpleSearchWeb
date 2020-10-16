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
                <td><input type="text" name="userId"></td>
            </tr>
            <tr>
                <td>비밀번호</td>
                <td><input type="password" name="userPw"></td>
            </tr>
        </table>
        <input type="submit" value="로그인" />
        <input type="hidden" name="command" value="login" />
    </form>
</body>
</html>
