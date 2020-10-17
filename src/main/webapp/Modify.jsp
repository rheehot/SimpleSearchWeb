<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <dd>
            <%-- 비밀번호 수정 시 id 값은 그대로 들고 --%>
            <label>아이디: <%=session.getAttribute("userId")%> </label>
        </dd>
        <dd>
            <label for="userPw">기존 비밀번호: </label>
            <input type="password" name="userPw" id="userPw">
        </dd>
        <dd>
            <label for="modifyPw">변경할 비밀번호: </label>
            <input type="password" name="modifyPw" id="modifyPw">
        </dd>
        <tr>
            <td>
                <input type="submit" value="비밀번호 수정">
                <input type="hidden" name="command" value="modify" />
            </td>
        </tr>
    </table>
</form>
<%
    }
%>
</body>
</html>
