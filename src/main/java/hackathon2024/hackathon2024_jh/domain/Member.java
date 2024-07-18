package hackathon2024.hackathon2024_jh.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Entity
@NoArgsConstructor
@Getter
public class Member {
    @Id @GeneratedValue
    private Long id;    //1
    @Column(unique = true)
    private String userId;  //njh123
    private String password;
    @Setter
    private String nickname;    //T라미숙해
    private String birth;          //20031013
    private String gender;      // male, female
    private String phoneNum;    //01012341234



    public Member(String userId, String password, String nickname,
                  String birth, String gender,
                  String phoneNum) {
        this.userId = userId;
        this.setPassword(password);
        this.nickname = nickname;
        this.birth = birth;
        this.gender = gender;
        this.phoneNum = phoneNum;
    }

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void setPassword(String password) {
        this.password = passwordEncoder.encode(password);
    }

    public boolean checkPassword(String rawpassword) {
        return passwordEncoder.matches(rawpassword, this.password);
    }
}
