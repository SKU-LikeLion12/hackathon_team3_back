package hackathon2024.hackathon2024_jh.DTO;

import hackathon2024.hackathon2024_jh.domain.LikePost;
import hackathon2024.hackathon2024_jh.domain.SavePost;
import lombok.Data;

public class SavePostDTO {
    @Data
    public static class SavePostResponse{
        private boolean isSave;
        private String saver;
        private Long postId;

        public SavePostResponse(SavePost savePost){
            this.postId = savePost.getPost().getId();
            this.saver = savePost.getSaver().getUserId();
            this.isSave = savePost.isSave();
        }
    }

    @Data
    public static class SaveCreateRequest{
        private String token;
    }

}
