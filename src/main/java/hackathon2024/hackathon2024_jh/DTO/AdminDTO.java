package hackathon2024.hackathon2024_jh.DTO;

import lombok.Data;

public class AdminDTO {
    @Data
    public static class AdminLoginRequest{
        private String userId;
        private String password;
    }
}
