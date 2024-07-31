package hackathon2024.hackathon2024_jh.DTO;

import hackathon2024.hackathon2024_jh.domain.ExpertPost;
import hackathon2024.hackathon2024_jh.domain.GeneralPost;
import hackathon2024.hackathon2024_jh.domain.Post;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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
        private Boolean isExpertPost;
        private LocalDateTime createDate;
        private boolean isLike;
        private boolean isChange;

        public ResponsePostExpert(ExpertPost expertPost, Boolean isLike) {
            this.id = expertPost.getId();
            this.title = expertPost.getTitle();
            this.content = expertPost.getContent();
            this.writer = expertPost.getWriter().getNickname();
            this.commentSize = expertPost.getCommentSize();
            this.likeSize = expertPost.getLikeSize();
            this.saveSize = expertPost.getSaveSize();
            this.isExpertPost = expertPost.getIsExpertPost();
            this.createDate = expertPost.getCreateTime();
            this.isChange = !expertPost.getCreateTime().equals(expertPost.getUpdateTime());
            this.isLike = isLike;
        }
    }

    @Data
    public static class ResponsePostGeneral {
        private static final Map<String, String> categoryMap = new HashMap<>();

        static {
            categoryMap.put("a", "일반 고민");
            categoryMap.put("b", "진로/취업");
            categoryMap.put("c", "학교");
            categoryMap.put("d", "직장");
            categoryMap.put("e", "대인 관계");
            categoryMap.put("f", "썸/연애");
            categoryMap.put("g", "결혼/육아");
            categoryMap.put("h", "이별/이혼");
            categoryMap.put("i", "가족");
            categoryMap.put("j", "성 생활");
            categoryMap.put("k", "외모");
            categoryMap.put("l", "금전");
            categoryMap.put("m", "LGBT");
        }

        private Long id;
        private String title;
        private String content;
        private String writer;
        private String category;
        private Long commentSize;
        private Long likeSize;
        private Long saveSize;
        private Boolean isExpertPost;
        private LocalDateTime createDate;
        private boolean isLike;
        private boolean isChange;

        public ResponsePostGeneral(GeneralPost generalPost, Boolean isLike) {
            this.id = generalPost.getId();
            this.title = generalPost.getTitle();
            this.content = generalPost.getContent();
            this.writer = generalPost.getWriter().getNickname();
            this.category = categoryMap.get(generalPost.getCategory());
            this.commentSize = generalPost.getCommentSize();
            this.likeSize = generalPost.getLikeSize();
            this.saveSize = generalPost.getSaveSize();
            this.isExpertPost = generalPost.getIsExpertPost();
            this.createDate = generalPost.getCreateTime();
            this.isChange = !generalPost.getCreateTime().equals(generalPost.getUpdateTime());
            this.isLike = isLike;
        }
    }


    @Data
    public static class ResponsePost {
        private static final Map<String, String> categoryMap = new HashMap<>();

        static {
            categoryMap.put("a", "일반 고민");
            categoryMap.put("b", "진로/취업");
            categoryMap.put("c", "학교");
            categoryMap.put("d", "직장");
            categoryMap.put("e", "대인 관계");
            categoryMap.put("f", "썸/연애");
            categoryMap.put("g", "결혼/육아");
            categoryMap.put("h", "이별/이혼");
            categoryMap.put("i", "가족");
            categoryMap.put("j", "성 생활");
            categoryMap.put("k", "외모");
            categoryMap.put("l", "금전");
            categoryMap.put("m", "LGBT");
        }

        private Long id;
        private String title;
        private String content;
        private String writer;
        private String category;
        private Long commentSize;
        private Long likeSize;
        private Long saveSize;
        private Boolean isExpertPost;
        private LocalDateTime createDate;
        private boolean isLike;
        private boolean isChange;

        public ResponsePost(Post post, Boolean isLike) {
            this.id = post.getId();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.commentSize = post.getCommentSize();
            this.likeSize = post.getLikeSize();
            this.saveSize = post.getSaveSize();

            this.createDate = post.getCreateTime();
            this.isChange = !post.getCreateTime().equals(post.getUpdateTime());
            this.isLike = isLike;

            if (post instanceof ExpertPost) {
                ExpertPost expertPost = (ExpertPost) post;
                this.writer = expertPost.getWriter().getNickname();
                this.isExpertPost = true;
                this.category = "Expert Category"; // 혹은 다른 ExpertPost 관련 필드
            } else if (post instanceof GeneralPost) {
                GeneralPost generalPost = (GeneralPost) post;
                this.writer = generalPost.getWriter().getNickname(); // GeneralPost에 writer가 있다고 가정
                this.isExpertPost = false;
                this.category = categoryMap.get(generalPost.getCategory());
            } else {
                throw new IllegalArgumentException("Unknown post type");
            }
        }
    }

    @Data
    public static class RequestPostGeneral {
        private String title;
        private String content;
        private String category;
        private String token;
    }

    @Data
    public static class RemovePost {
        private String token;
    }

    @Data
    public static class isLogin {
        private String token;
    }
}
