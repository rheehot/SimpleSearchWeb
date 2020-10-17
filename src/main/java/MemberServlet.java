import DAO.MemberDAO;
import VO.Member;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/MemberServlet")
public class MemberServlet extends HttpServlet {

    private Connection con;
    private PreparedStatement pstmt;
    private DataSource dataSource;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doHandle(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doHandle(request, response);
    }

    protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session;

        MemberDAO dao = new MemberDAO();
        dataSource = dao.getDataSource();

        String command = request.getParameter("command");

        // 멤버 회원가입
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

            // 멤버 회원탈퇴
            } else if (command != null && command.equals("delMember")) {

                session = request.getSession();
                String userId = (String) session.getAttribute("userId");

                dao.delMember(userId);

                // 탈퇴 버튼 클릭 시 마지막 인사와 welcome 페이지로 돌아가는 버튼
                response.sendRedirect("/DeleteAccountMsg.jsp");

              // 로그인
            } else if (command != null && command.equals("login")) {
                String userId = request.getParameter("userId");
                String userPw = request.getParameter("userPw");

                Member member = new Member();
                member.setUserId(userId);
                member.setUserPw(userPw);
                boolean result = dao.isExisted(member);

                // 로그인 정보 일치 시 세션 저장
                if (result) {
                    session = request.getSession();

                    session.setAttribute("isLogon", true);
                    session.setAttribute("userId", userId);
                    session.setAttribute("userPw", userPw);

                    /*
                    1. Admin 아이디 경우 멤버 정보를 모두 볼 수 있는 Admin.jsp
                    2. 아닐 경우 와인 검색하는 Main.jsp
                     */
                    if ((userId != null && userId.length() != 0)) {
                        if (userId.equals("admin")) {
                            response.sendRedirect("/Admin.jsp");
                        } else {
                            response.sendRedirect("/Main.jsp");
                        }
                    }
                } else { // 세션이 존재하지 않을 경우 LogIn.jsp
                    response.sendRedirect("/LogIn.jsp");
                }

            // 멤버 자신의 비밀번호 수정
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
