package hackathon2024.hackathon2024_jh.DTO;

import hackathon2024.hackathon2024_jh.domain.Comment;
import lombok.Data;

import java.time.LocalDateTime;

public class CommentDTO {
    @Data
    public static class CommentResponse {
        private String content;
        private LocalDateTime createDate;
        private boolean isUpdate;
        private String writer;
        private String writer_id;

        public CommentResponse(Comment comment) {
            this.content = comment.getContent();
            this.writer = comment.getMember().getNickname();
            this.writer_id = comment.getMember().getUserId();
            this.createDate = comment.getCreateDate();
            if(comment.getCreateDate().equals(comment.getUpdateDate())){
                this.isUpdate = false;
            }else{
                this.isUpdate = true;
            }
        }
    }

    @Data
    public static class CommentCreateRequest {
        private Long articleId;
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
