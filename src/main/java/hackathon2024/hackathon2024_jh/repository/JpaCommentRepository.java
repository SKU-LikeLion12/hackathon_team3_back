package hackathon2024.hackathon2024_jh.repository;

import hackathon2024.hackathon2024_jh.domain.Comment;
import hackathon2024.hackathon2024_jh.domain.ExpertPost;
import hackathon2024.hackathon2024_jh.domain.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@RequiredArgsConstructor
public class JpaCommentRepository implements CommentRepository{
    private final EntityManager em;
    public Comment findById(Long id) {
        return em.find(Comment.class, id);
    }

    public void saveComment(Comment comment) {
        em.persist(comment);
    }

    public void deleteComment(Comment comment) {
        em.remove(comment);
    }


}
