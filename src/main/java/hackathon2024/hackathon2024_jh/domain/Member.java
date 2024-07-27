package hackathon2024.hackathon2024_jh.domain;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Member extends User {
    public Member(String userId, String password, String nickname, String birth, String gender, String phoneNum) {
        super(userId, password, nickname, birth, gender, phoneNum);
    }
}
