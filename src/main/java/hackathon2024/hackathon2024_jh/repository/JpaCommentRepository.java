package hackathon2024.hackathon2024_jh.repository;

import hackathon2024.hackathon2024_jh.domain.Comment;
import hackathon2024.hackathon2024_jh.domain.Post;
import hackathon2024.hackathon2024_jh.domain.WriterType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
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
        em.flush();
    }

    @Override
    public void deleteComment(Comment comment) {
        em.remove(comment);
    }

    @Override
    public List<Comment> findPostsComments(Post post) {
        return em.createQuery("select p from Comment p where p.post = :po order by createDate desc", Comment.class)
                .setParameter("po", post).getResultList();
    }

    @Override
    public Comment findFirstExpertComment(Post post) {
        try {
            return em.createQuery(
                            "SELECT c FROM Comment c " +
                                    "WHERE c.post = :post AND c.writerType = :writerType " +
                                    "ORDER BY c.createDate ASC",
                            Comment.class)
                    .setParameter("post", post)
                    .setParameter("writerType", WriterType.EXPERT)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // or handle the case where no comment is found
        }
    }

    @Override
    public Long countCommentSize(Post post){
        return em.createQuery("select count(c) from Comment c WHERE post.id = :post", Long.class)
                .setParameter("post", post.getId()).getSingleResult();
    }


}
