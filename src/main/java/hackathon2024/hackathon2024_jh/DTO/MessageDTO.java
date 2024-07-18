package hackathon2024.hackathon2024_jh.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class MessageDTO {
    @Data
    public static class RequestMessage{
        private String phoneNumber;
    }


    @Data
    public static class CertificationNum{
        private String phoneNumber;
        private String randomNumber;
    }
}
