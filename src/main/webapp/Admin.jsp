<%@ page import="java.util.Date" %>
<%@ page import="VO.Member" %>
<%@ page import="DAO.MemberDAO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%
        if (session.getAttribute("userId") != null) {
            String userId = (String) session.getAttribute("userId");

    %>

    <h1>Admin 님 환영합니다.
        <div>
            <form method="post" action="Admin.jsp">
                회원 아이디를 입력 : <input type="text" name="userId" /><input type="submit" value="찾기" />
            </form>
        </div>

            <%
            }
        %>

            <%
        String id = request.getParameter("userId");
        Member member = new Member();
        member.setUserId(id);

        if (id != null) {
            MemberDAO dao = new MemberDAO();
            List memberList = dao.memberList(member);

            for (Object memberLists : memberList) {
                Member member1 = (Member) memberLists;
                String userId = member1.getUserId();
                String userName = member1.getUserName();
                String userEmail = member1.getUserEmail();
                Date joinDate = member1.getJoinDate();
    %>

        <div>
            <table border="1" bordercolor="black" width="60%" align="center" style="word-break: break-all">
                <tr align="center" bgcolor="#f5f5dc">
                    <td class="bold">아이디</td>
                    <td class=".bold">이름</td>
                    <td class=".bold">이메일</td>
                    <td class=".bold">가입날짜</td>
                </tr>
                <tr align="center">
                    <td><%= userId %></td>
                    <td><%= userName %></td>
                    <td><%= userEmail %></td>
                    <td><%= joinDate %></td>
                </tr>
            </table>
        </div>

        <%
            }
        }
        %>


</body>
</html>
