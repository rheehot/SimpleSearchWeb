import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MemberDAO {
    private Connection con;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private DataSource dataSource;

    public MemberDAO() {
        try {
            Context ctx = new InitialContext();
            Context envContext = (Context) ctx.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/mysql");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Member> listMembers() {
        List<Member> list = new ArrayList<Member>();
        try {
            con = dataSource.getConnection();
            String query = "SELECT * FROM school.student ";

            pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String userId = rs.getString("userId");
                String userPw = rs.getString("userPw");
                String userName = rs.getString("userName");
                String userEmail = rs.getString("userEmail");
                Date joinDate = rs.getDate("joinDate");

                Member member = new Member();
                member.setUserId(userId);
                member.setUserPw(userPw);
                member.setUserName(userName);
                member.setUserEmail(userEmail);
                member.setJoinDate(joinDate);
                list.add(member);
            }

            rs.close();
            pstmt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addMember(Member member) {
        try {
            con = dataSource.getConnection();
            String userId = member.getUserId();
            String userPw = member.getUserPw();
            String userName = member.getUserName();
            String userEmail = member.getUserEmail();

            String query = "INSERT INTO school.student (userId,userPw,userName,userEmail) values (?, ?, ?, ?)";

            pstmt = con.prepareStatement(query);
            pstmt.setString(1, userId);
            pstmt.setString(2, userPw);
            pstmt.setString(3, userName);
            pstmt.setString(4, userEmail);
            pstmt.executeUpdate();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delMember(String userId) {
        try {
            con = dataSource.getConnection();
            String query = "DELETE FROM school.student WHERE userId=?";

            pstmt = con.prepareStatement(query);
            pstmt.setString(1, userId);
            pstmt.executeUpdate();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void login(Member member) {
//
//        String userId = member.getUserId();
//        String userPw = member.getUserPw();
//
//        try {
//            con = dataSource.getConnection();
//            String query = "SELECT * FROM school.student WHERE userId=? AND userPw=?";
//
//            pstmt = con.prepareStatement(query);
//            pstmt.setString(1, userId);
//            pstmt.setString(2, userPw);
//            rs = pstmt.executeQuery();
//
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }
}
