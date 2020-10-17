<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="java.util.*" import="VO.*" import="java.lang.*"%>
<%@ page import="DAO.MemberDAO" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <style>
        .bold {
            font-weight: bold;
        }
    </style>
</head>
<body>
    <%-- 세션 유지 --%>
    <%
        if (session.getAttribute("userId") != null) {
            String userId = (String) session.getAttribute("userId");
    %>

    <div>
        <h1><%=userId%> 님 환영합니다.
            <div>
                <form action="Modify.jsp">
                    <input type="submit" value="비밀번호 변경">
                </form>
                <form action="MemberServlet" method="post">
                    <input type="submit" value="회원탈퇴">
                    <input type="hidden" name="command" value="delMember" />
                </form>
            </div>
    </div>

    <%-- 로그인 세션 없을 시 LogIn.jsp 이동 --%>
    <%
    } else {
        response.sendRedirect("/LogIn.jsp");
    }
    %>

    <div>
        <form method="post" action="Main.jsp">
            원하는 와인의 당도를 입력해주세요(1 ~ 6) : <input type="text" name="name" /><input type="submit" value="찾기" />
        </form>
    </div>

    <%-- 1. 원하는 당도 입력 시 해당하는 와인 보여줌(1 ~ 6)
        2. 당도 미입력 버튼 클릭 시 전체 와인 보여줌--%>
    <%
        String name = request.getParameter("name");
        Wine wine = new Wine();
        wine.setSweetness(name);

        if (name != null) {
            MemberDAO dao = new MemberDAO();
            List listWines = dao.listWines(wine);

            for (Object listWine : listWines) {
                Wine wine1 = (Wine) listWine;
                String wineName = wine1.getWineName();
                String kind = wine1.getKind();
                String origin = wine1.getOrigin();
                String sweetness = wine1.getSweetness();
                String wineImg = wine1.getWineImg();
    %>

    <div>
        <table border="1" bordercolor="black" width="60%" align="center" style="word-break: break-all">
            <tr align="center" bgcolor="#f5f5dc">
                <td class="bold">와인이름</td>
                <td class=".bold">와인종류</td>
                <td class=".bold">생산지</td>
                <td class=".bold">당도(1 ~ 6)</td>
                <td class=".bold">사진</td>
            </tr>
            <tr align="center">
                <td><%= wineName %></td>
                <td><%= kind %></td>
                <td><%= origin %></td>
                <td><%= sweetness %></td>
                <%-- 1. 데이터베이스에 와인 경로 입력
                2. 경로를 VO에 저장 후 가져옴
                3. 와인에 해당하는 사진이 나옴 --%>
                <td><img src="<%= wineImg%>"/></td>
            </tr>
        </table>
    </div>
    <%
        }
    %>
    <%
        }
    %>
</body>
</html>
