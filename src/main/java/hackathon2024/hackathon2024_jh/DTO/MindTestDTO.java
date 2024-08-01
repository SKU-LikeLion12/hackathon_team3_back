package hackathon2024.hackathon2024_jh.DTO;

import hackathon2024.hackathon2024_jh.domain.Member;
import hackathon2024.hackathon2024_jh.domain.MindTest;
import lombok.Data;

import java.time.LocalDateTime;

public class MindTestDTO {
    @Data
    public static class MindTestResponse {
        private String category;
        private String tester;
        private String testerId;
        private int score;
        private LocalDateTime testDate;


        public MindTestResponse(MindTest mindTest, Member member) {
            this.category = mindTest.getCategory();
            this.tester = member.getNickname();
            this.testerId = member.getUserId();
            this.score = mindTest.getScore();
            this.testDate =mindTest.getTestTime();
        }

    }

    @Data
    public static class testSave{
        private String token;
        private String category;
        private int score;
    }

    @Data
    public static class IsLogin{
        private String token;
    }
}
