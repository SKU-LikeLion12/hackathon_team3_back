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
public class JpaCommentRepository {
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

    public List<Comment> findArticleComments(ExpertPost expertPost) {
        return em.createQuery("select a from Comment a where a.post = :ar", Comment.class)
                .setParameter("ar", expertPost).getResultList();
    }

    public List<Comment> findMemberComments(Member member) {
        return em.createQuery("select a from Comment a where a.member = :m", Comment.class)
                .setParameter("m", member).getResultList();
    }

    public List<ExpertPost> findMemberCommentsArticle(Member member) {
        return em.createQuery("select DISTINCT c.post from Comment c where c.member = :m", ExpertPost.class)
                .setParameter("m", member).getResultList();
    }
}
