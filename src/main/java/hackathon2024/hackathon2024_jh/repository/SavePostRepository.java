package hackathon2024.hackathon2024_jh.repository;

import hackathon2024.hackathon2024_jh.domain.Post;
import hackathon2024.hackathon2024_jh.domain.SavePost;
import hackathon2024.hackathon2024_jh.domain.User;

import java.util.List;

public interface SavePostRepository {

    SavePost findId(Post post, User user);

    SavePost findById(Long id);

    void save(SavePost savePost);

    Long countSaveSize(Long postId);

    List<Post> findSaveposts(Long userId);
}
