package hackathon2024.hackathon2024_jh.domain;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Expert extends User {
    private String image;
    private String email;
    private Boolean isExpert;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public Expert(String userId, String password, String nickname, String birth, String gender, String phoneNum,
                  String image, String email, Boolean isExpert) {
        super(userId, password, nickname, birth, gender, phoneNum);
        this.image = image;
        this.email = email;
        this.isExpert = isExpert;
        this.createTime = LocalDateTime.now();
        this.updateTime = this.createTime;
    }

    public void setIsExpert(Boolean isExpert) {
        this.isExpert = isExpert;
        this.updateTime = LocalDateTime.now();
    }
}
