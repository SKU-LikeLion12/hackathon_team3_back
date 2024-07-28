package hackathon2024.hackathon2024_jh.DTO;

import hackathon2024.hackathon2024_jh.domain.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {

    @Data
    public static class CommentResponse {
        private Long id;
        private String content;
        private LocalDateTime createDate;
        private boolean isUpdate;
        private String writer;
        private String writerId;
        private String role;
        private Boolean isFix;

        // Constructor for CommentResponse based on Member
        public CommentResponse(Comment comment, User user) {
            this.id = comment.getId();
            this.isFix = comment.getIsFix();
            this.content = comment.getContent();
            this.createDate = comment.getCreateDate();
            if (user instanceof Member) {
                this.role = WriterType.MEMBER.name(); // 'MEMBER'
            } else if (user instanceof Expert) {
                this.role = WriterType.EXPERT.name(); // 'EXPERT'
            }
            this.writer = user.getNickname();
            this.writerId = user.getUserId();
            this.isUpdate = !comment.getCreateDate().equals(comment.getUpdateDate());
        }


    }

    @Data
    public static class CommentCreateRequest {
//        private Long postId;
        private String token;
        private String content;
    }

    @Data
    public static class CommentUpdateRequest {
        //private Long commentId;
        private String token;
        private String content;
    }

    @Data
    public static class CommentDeleteRequest {
        //private Long commentId;
        private String token;
    }
}
