package hackathon2024.hackathon2024_jh.DTO;

import hackathon2024.hackathon2024_jh.domain.Expert;
import hackathon2024.hackathon2024_jh.domain.ExpertPost;
import lombok.Data;

import java.time.LocalDateTime;

public class PostDTO {
    @Data
    public static class RequestPostExpert {
        private String title;
        private String content;
        private String token;
    }

    @Data
    public static class ResponsePostExpert {
        private Long id;
        private String title;
        private String content;
        private String writer;
        private Long commentSize;
        private Long likeSize;
        private Long saveSize;
        private Boolean IsExpertPost;
        private LocalDateTime createDate;
        private boolean isChange;

        public ResponsePostExpert(ExpertPost expertPost) {
            this.id = expertPost.getId();
            this.title = expertPost.getTitle();
            this.content = expertPost.getContent();
            this.writer = expertPost.getWriter().getNickname();
            this.commentSize = expertPost.getCommentSize();
            this.likeSize = expertPost.getLikeSize();
            this.saveSize = expertPost.getSaveSize();
            this.IsExpertPost = expertPost.getIsExpertPost();
            this.createDate = expertPost.getCreateTime();
            if(expertPost.getCreateTime().equals(expertPost.getUpdateTime())){
                this.isChange = false;
            }else{
                this.isChange = true;
            }
        }
    }
}
