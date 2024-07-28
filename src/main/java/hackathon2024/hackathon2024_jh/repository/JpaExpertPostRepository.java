package hackathon2024.hackathon2024_jh.repository;

import hackathon2024.hackathon2024_jh.domain.ExpertPost;
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

    @Override
    public List<ExpertPost> findAll() {
        return em.createQuery("select p from ExpertPost p", ExpertPost.class).getResultList();
    }

    @Override
    public ExpertPost findById(Long id) {
        return em.find(ExpertPost.class, id);
    }


    @Override
    public void delete(ExpertPost expertPost) {
        em.remove(expertPost);
    }
}
