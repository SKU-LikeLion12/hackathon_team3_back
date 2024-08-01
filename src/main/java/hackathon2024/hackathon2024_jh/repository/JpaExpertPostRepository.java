package hackathon2024.hackathon2024_jh.repository;

import hackathon2024.hackathon2024_jh.domain.Expert;
import hackathon2024.hackathon2024_jh.domain.ExpertPost;
import hackathon2024.hackathon2024_jh.domain.GeneralPost;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaExpertPostRepository implements PostExpertRepository {
    private final EntityManager em;
    @Override
    public ExpertPost save(ExpertPost expertPost) {
        em.persist(expertPost);
        em.flush();
        em.clear();
        return expertPost;
    }

    //최신순
    @Override
    public List<ExpertPost> findAll() {
        return em.createQuery("select p from ExpertPost p order by createTime desc", ExpertPost.class).getResultList();
    }

    //좋아요순
    @Override
    public List<ExpertPost> findAllLikedesc() {
        return em.createQuery("select p from ExpertPost p order by likeSize desc", ExpertPost.class).getResultList();
    }

    //댓글순
    @Override
    public List<ExpertPost> findAllCommentdesc() {
        return em.createQuery("select p from ExpertPost p order by commentSize desc", ExpertPost.class).getResultList();
    }

    //저장순
    @Override
    public List<ExpertPost> findAllSavedesc() {
        return em.createQuery("select p from ExpertPost p order by saveSize desc", ExpertPost.class).getResultList();
    }

    @Override
    public ExpertPost findById(Long id) {
        return em.find(ExpertPost.class, id);
    }

    @Override
    public List<ExpertPost> findByExpert(Expert expert) {
        return em.createQuery("select p from ExpertPost p where p.writer = :writer order by createTime desc ", ExpertPost.class)
                .setParameter("writer",expert).getResultList();
    }

    @Override
    public void delete(ExpertPost expertPost) {
        em.remove(expertPost);
    }
}
