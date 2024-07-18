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
public class Expert {
    @Id
    @GeneratedValue
    private Long id;    //1
    @Column(unique = true)
    private String userId;  //njh123
    private String password;
    @Setter
    private String nickname;    //노주희
    private String birth;          //20031013
    private String gender;      // male, female
    private String phoneNum;    //01012341234
    private String image;

    private Boolean isExpert;

    public Expert(String userId, String password, String nickname,
                  String birth, String gender,
                  String phoneNum, String image, boolean isExpert) {
        this.userId = userId;
        this.setPassword(password);
        this.nickname = nickname;
        this.birth = birth;
        this.gender = gender;
        this.phoneNum = phoneNum;
        this.image = image;
        this.isExpert = isExpert;
    }

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void setPassword(String password) {
        this.password = passwordEncoder.encode(password);
    }

    public boolean checkPassword(String rawpassword) {
        return passwordEncoder.matches(rawpassword, this.password);
    }
}
