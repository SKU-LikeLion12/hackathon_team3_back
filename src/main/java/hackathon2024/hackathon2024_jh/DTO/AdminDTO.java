package hackathon2024.hackathon2024_jh.DTO;

import lombok.Data;

import java.time.LocalDateTime;

public class AdminDTO {
    @Data
    public static class AdminLoginRequest{
        private String Id;
        private String Password;

        public AdminLoginRequest(String Id, String Password) {
            this.Id = Id;
            this.Password = Password;
        }
    }



    @Data
    public static class expertList{
        private Long Id;
        private String userId;
        private String name;
        private String gender;
        private String phone;
        private String email;
        private Boolean isExpert;
        private String birth;
        private LocalDateTime createDate;
        private LocalDateTime updateDate;

        public expertList(Long id, String userId, String nickname,
                          String gender, String phoneNum, String email,
                          Boolean isExpert, String birth, LocalDateTime createTime, LocalDateTime updateTime) {
            this.Id = id;
            this.userId = userId;
            this.name = nickname;
            this.gender = gender;
            this.phone = phoneNum;
            this.email = email;
            this.isExpert = isExpert;
            this.birth = birth;
            this.createDate = createTime;
            this.updateDate = updateTime;

        }


    }
}
