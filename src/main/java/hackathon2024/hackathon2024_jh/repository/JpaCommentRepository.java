package hackathon2024.hackathon2024_jh.repository;

import hackathon2024.hackathon2024_jh.domain.Comment;
import hackathon2024.hackathon2024_jh.domain.Post;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@RequiredArgsConstructor
public class JpaCommentRepository implements CommentRepository{
    private final EntityManager em;

    @Override
    public Comment findById(Long id) {
        return em.find(Comment.class, id);
    }

    @Override
    public void saveComment(Comment comment) {
        em.persist(comment);
    }

    @Override
    public void deleteComment(Comment comment) {
        em.remove(comment);
    }

    @Override
    public List<Comment> findPostsComments(Post post) {
        return em.createQuery("select p from Comment p where p.post = :po", Comment.class)
                .setParameter("po", post).getResultList();
    }

}
