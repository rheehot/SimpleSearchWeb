<%@ page import="java.util.Date" %>
<%@ page import="VO.Member" %>
<%@ page import="DAO.MemberDAO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

    <%-- 세션유지 --%>
    <%
        if (session.getAttribute("userId") != null) {
            String userId = (String) session.getAttribute("userId");
    %>

    <%-- 1. 특정 회원 아이디 입력 시 결과값
        2. 특정 회원 아이디 입력 X 시 전체 회원 보여줌 --%>
    <h1><%= userId %> 계정으로 로그인 하셨습니다.
        <div>
            <form method="post" action="Admin.jsp">
                회원 아이디를 입력해주세요. 전체 회원을 찾을 시 그냥 "찾기" 버튼 클릭해주세요. <label><input type="text" name="userId" /><input type="submit" value="찾기" /></label>
            </form>
            <%-- 로그아웃 버튼 --%>
            <form action="LogOut.jsp" method="post">
                <input type="submit" value="로그아웃">
            </form>
        </div>

    <%
        }
    %>

    <%-- 특정 혹은 전체 회원 목록 --%>
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
                    <td class=".bold">가입 날짜</td>
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
