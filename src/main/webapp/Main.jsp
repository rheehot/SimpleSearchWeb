<%@ page contentType="text/html;charset=UTF-8" language="java"
    import="java.util.*" import="VO.*"%>
<%@ page import="DAO.MemberDAO" %>
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
        <div>
            <h1><%=userId%> 님 환영합니다.
            </h1><a href="/Modify.jsp">회원정보 수정</a>
        </div>
    <%
        } else {
    %>
    <%
            response.sendRedirect("/LogIn.jsp");
        }
    %>

    <div>
        <form method="post" action="Main.jsp">
            와인검색 : <input type="text" name="name"><br/>
            <input type="submit" value="조회하기">
        </form>

        <%
            String name = request.getParameter("name");
            Wine wine = new Wine();
            wine.setSweetness(name);

            MemberDAO dao = new MemberDAO();
            List listWines = dao.listWines(wine);
        %>

        <%
            for (int i = 0; i < listWines.size(); i++) {
                Wine wine1 = (Wine) listWines.get(i);
                String wineName = wine1.getWineName();
                String kind = wine1.getKind();
                String origin = wine1.getOrigin();
                String sweetness = wine1.getSweetness();
        %>

    </div>
    <div>
        <table border="1" bordercolor="black" width="50%" align="center" style="word-break: break-all">
            <tr align="center">
                <td>와인이름</td>
                <td>와인종류</td>
                <td>생산지</td>
                <td>당도(1 ~ 10)</td>
            </tr>
            <tr align="center">
                <td><%= wineName %></td>
                <td><%= kind %></td>
                <td><%= origin %></td>
                <td><%= sweetness %></td>
            </tr>
        </table>
    </div>

    <%
        }
    %>
</body>
</html>
