import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/MemberServlet")
public class MemberServlet extends HttpServlet {

    private Connection con;
    private PreparedStatement pstmt;
    private ResultSet rs;
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

        MemberDAO dao = new MemberDAO();
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

        } else if (command != null && command.equals("delMember")) {
            String userId = request.getParameter("userId");
            dao.delMember(userId);

        } else if (command != null && command.equals("login")) {

            String userId = request.getParameter("userId");
            String userPw = request.getParameter("userPw");


            HttpSession session = request.getSession();

            try {
                con = dataSource.getConnection();
                String query = "SELECT * FROM school.student WHERE userId=? AND userPw=?";
                pstmt = con.prepareStatement(query);
                pstmt.setString(1, userId);
                pstmt.setString(2, userPw);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    session.setAttribute("userId", userId);
                }
                response.sendRedirect("/Main.jsp");

            } catch (SQLException throwables) {
                throwables.printStackTrace();

            } finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    if (pstmt != null)
                        pstmt.close();

                    if (con != null)
                        con.close();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
}
