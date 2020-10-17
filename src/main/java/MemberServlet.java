import DAO.MemberDAO;
import VO.Member;
import com.mysql.cj.Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static java.lang.System.out;

@WebServlet("/MemberServlet")
public class MemberServlet extends HttpServlet {

    private Connection con;
    private PreparedStatement pstmt;
    private DataSource dataSource;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            doHandle(request, response);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            doHandle(request, response);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {


        HttpSession session;

        MemberDAO dao = new MemberDAO();
        dataSource = dao.getDataSource();

        String command = request.getParameter("command");

        if (command != null && command.equals("addMember")) {
            String userId = request.getParameter("userId");
            String userPw = request.getParameter("userPw");
            String userName = request.getParameter("userName");
            String userEmail = request.getParameter("userEmail");

            Member member = new Member();
            member.setUserId(userId);
            member.setUserPw(userPw);
            member.setUserName(userName);
            member.setUserEmail(userEmail);

            dao.addMember(member);
            response.sendRedirect("/Welcome.html");


        } else if (command != null && command.equals("delMember")) {

            session = request.getSession();
            String userId = (String) session.getAttribute("userId");

            dao.delMember(userId);

            response.sendRedirect("/DeleteAccountMsg.jsp");

        } else if (command != null && command.equals("login")) {
            String userId = request.getParameter("userId");
            String userPw = request.getParameter("userPw");

            Member member = new Member();
            member.setUserId(userId);
            member.setUserPw(userPw);
            boolean result = dao.isExisted(member);

            if (result) {
                session = request.getSession();

                session.setAttribute("isLogon", true);
                session.setAttribute("userId", userId);
                session.setAttribute("userPw", userPw);

                // Admin
                if ((userId != null && userId.length() != 0)) {
                    if (userId.equals("admin")) {
                        response.sendRedirect("/Admin.jsp");
                    } else {
                        response.sendRedirect("/Main.jsp");
                    }
                }
            } else {
                response.sendRedirect("/LogIn.jsp");
            }

        } else if ((command != null && command.equals("modify"))) {
            session = request.getSession();
            String userId = (String) session.getAttribute("userId");
            String userPw = request.getParameter("userPw");

            String query = null;

            if (request.getParameter("modifyPw") != null) {
                query = "UPDATE school.student SET userPw=? WHERE userId=? AND userPw=?";
            }

            try {
                con = dataSource.getConnection();
                pstmt = con.prepareStatement(query);

                if (request.getParameter("modifyPw") != null) {
                    pstmt.setString(1, request.getParameter("modifyPw"));
                    pstmt.setString(2, userId);
                    pstmt.setString(3, userPw);
                }
                pstmt.executeUpdate();
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                try {
                    if (pstmt != null)
                        pstmt.close();

                    if (con != null)
                        con.close();

                    session.invalidate();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            response.sendRedirect("LogIn.jsp");
        }
    }
}
