package hackathon2024.hackathon2024_jh.DTO;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class MailDTO {

    private String from;
    private String to;
    private String subject;
    private String content;

    public void setFrom(String from) {
        this.from = from;
    }

    // 수신자 설정 메서드
    public void setTo(String to) {
        this.to = to;
    }
}
