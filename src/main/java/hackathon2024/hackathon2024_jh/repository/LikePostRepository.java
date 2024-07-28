package hackathon2024.hackathon2024_jh.repository;

import hackathon2024.hackathon2024_jh.domain.LikePost;
import hackathon2024.hackathon2024_jh.domain.Post;
import hackathon2024.hackathon2024_jh.domain.User;

import java.util.List;

public interface LikePostRepository {
    LikePost findId(Post post, User user);

    LikePost findById(Long id);

    void save(LikePost likePost);

    Long countLikeSize(Long postId);

    List<Post> findLikeArticles(Long userId);
}
