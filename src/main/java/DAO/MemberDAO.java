package DAO;

import VO.Member;
import VO.Wine;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MemberDAO {
    private Connection con;
    private PreparedStatement pstmt;
    private DataSource dataSource;
    private ResultSet rs;
    public DataSource getDataSource() {
        return dataSource;
    }

    public MemberDAO() {
        try {
            Context ctx = new InitialContext();
            Context envContext = (Context) ctx.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/mysql");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 특정 멤버 혹은 전체 멤버 보여주는 메서드
    public List<Member> memberList(Member member) {
        List memberList = new ArrayList();
        String id = member.getUserId();

        try {
            con = dataSource.getConnection();
            String query = "SELECT * FROM school.student ";

            if ((id != null && id.length() != 0)) {
                query += " WHERE userId=?";
                pstmt = con.prepareStatement(query);
                pstmt.setString(1, id);
            } else {
                pstmt = con.prepareStatement(query);
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String userId = rs.getString("userId");
                String userName = rs.getString("userName");
                String userEmail = rs.getString("userEmail");
                Date joinDate = rs.getDate("joinDate");

                Member member1 = new Member();
                member1.setUserId(userId);
                member1.setUserName(userName);
                member1.setUserEmail(userEmail);
                member1.setJoinDate(joinDate);
                memberList.add(member1);
            }

            rs.close();
            pstmt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return memberList;
    }


    // 특정 혹은 전체 와인 리스트 보여주는 메서드
    public List<Member> listWines(Wine wine) {
        List wineList = new ArrayList();
        String name = wine.getSweetness();
        try {
            con = dataSource.getConnection();
            String query = "SELECT * FROM school.wine ";

            if ((name != null && name.length() != 0)) {
                query += " WHERE sweetness=?";
                pstmt = con.prepareStatement(query);
                pstmt.setString(1, name);
            } else {
                pstmt = con.prepareStatement(query);
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String wineName = rs.getString("wineName");
                String kind = rs.getString("kind");
                String origin = rs.getString("origin");
                String sweetness = rs.getString("sweetness");
                String wineImg = rs.getString("wineImg");

                Wine wine1 = new Wine();
                wine1.setWineName(wineName);
                wine1.setKind(kind);
                wine1.setOrigin(origin);
                wine1.setSweetness(sweetness);
                wine1.setWineImg(wineImg);
                wineList.add(wine1);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wineList;
    }


    // 회원가입
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


    // 회원탈퇴
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


    // 해당 아이디에 대해 세션이 있는지 확인
    public boolean isExisted(Member member) {

        boolean result = false;
        String userId = member.getUserId();
        String userPw = member.getUserPw();

        try {
            con = dataSource.getConnection();
            String query1 = "SELECT IF(1, 'true', 'false') as result from school.student where userId=? and userPw=?";

            pstmt = con.prepareStatement(query1);

            pstmt.setString(1, userId);
            pstmt.setString(2, userPw);

            ResultSet rs = pstmt.executeQuery();
            rs.next();

            result = Boolean.parseBoolean(rs.getString("result"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
