package hackathon2024.hackathon2024_jh.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

public class MemberDTO {

    @AllArgsConstructor
    @Data
    //@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class GeneralMemberCreateRequest{
        private String nickname;
        private String userId;
        private String password;
        private String birth;
        private String gender;
        private String phoneNum;

    }

    @Data
    public static class ExpertMemberCreateRequest {
        private String userId;
        private String password;
        private String nickname;
        private String birth;
        private String gender;
        private String phoneNum;
        private String email;
        private MultipartFile image;


        public ExpertMemberCreateRequest() {
        }
    }

    @Data
    public static class MemberLoginRequest{
        private String userId;
        private String password;
    }
}
