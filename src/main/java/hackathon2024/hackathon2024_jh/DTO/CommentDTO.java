package hackathon2024.hackathon2024_jh.DTO;

import hackathon2024.hackathon2024_jh.domain.Comment;
import hackathon2024.hackathon2024_jh.domain.Expert;
import hackathon2024.hackathon2024_jh.domain.Member;
import hackathon2024.hackathon2024_jh.domain.WriterType;
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

        // Constructor for CommentResponse based on Member
        public CommentResponse(Comment comment, Member member) {
            this.id = comment.getId();
            this.content = comment.getContent();
            this.createDate = comment.getCreateDate();
            this.role = WriterType.MEMBER.name(); // 'MEMBER'
            this.writer = member.getNickname();
            this.writerId = member.getUserId();
            this.isUpdate = !comment.getCreateDate().equals(comment.getUpdateDate());
        }

        // Constructor for CommentResponse based on Expert
        public CommentResponse(Comment comment, Expert expert) {
            this.id = comment.getId();
            this.content = comment.getContent();
            this.createDate = comment.getCreateDate();
            this.role = WriterType.EXPERT.name(); // 'EXPERT'
            this.writer = expert.getNickname();
            this.writerId = expert.getUserId();
            this.isUpdate = !comment.getCreateDate().equals(comment.getUpdateDate());
        }
    }

    @Data
    public static class CommentCreateRequest {
        private Long postId;
        private String token;
        private String content;
    }

    @Data
    public static class CommentUpdateRequest {
        private Long commentId;
        private String token;
        private String content;
    }

    @Data
    public static class CommentDeleteRequest {
        private Long commentId;
        private String token;
    }
}
