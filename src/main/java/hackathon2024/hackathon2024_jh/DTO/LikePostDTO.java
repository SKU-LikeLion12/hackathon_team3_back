package hackathon2024.hackathon2024_jh.DTO;

import hackathon2024.hackathon2024_jh.domain.LikePost;
import lombok.Data;

public class LikePostDTO {
    @Data
    public static class LikePostResponse{
        private boolean isLike;
        private String liker;
        private Long postId;

        public LikePostResponse(LikePost likePost){
            this.postId = likePost.getPost().getId();
            this.liker = likePost.getLiker().getUserId();
            this.isLike = likePost.isLike();
        }
    }

    @Data
    public static class LikeCreateRequest{
        private String token;
    }
}
