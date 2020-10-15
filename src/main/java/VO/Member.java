package VO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class Member {

    private String userId;
    private String userPw;
    private String userName;
    private String userEmail;
    private Date joinDate;
}
