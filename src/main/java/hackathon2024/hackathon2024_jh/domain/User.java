package hackathon2024.hackathon2024_jh.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@NoArgsConstructor
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userId;  // njh123
    private String password;
    @Setter
    private String nickname;    // 노주희
    private String birth;       // 20031013
    private String gender;      // male, female
    private String phoneNum;    // 01012341234

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User(String userId, String password, String nickname, String birth, String gender, String phoneNum) {
        this.userId = userId;
        this.setPassword(password);
        this.nickname = nickname;
        this.birth = birth;
        this.gender = gender;
        this.phoneNum = phoneNum;
    }

    public void setPassword(String password) {
        this.password = passwordEncoder.encode(password);
    }

    public boolean checkPassword(String rawPassword) {
        return passwordEncoder.matches(rawPassword, this.password);
    }

}
