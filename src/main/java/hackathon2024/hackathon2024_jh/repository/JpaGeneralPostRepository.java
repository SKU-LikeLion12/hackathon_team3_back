package hackathon2024.hackathon2024_jh.repository;

import hackathon2024.hackathon2024_jh.domain.ExpertPost;
import hackathon2024.hackathon2024_jh.domain.GeneralPost;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaGeneralPostRepository implements PostGeneralRepository {
    private final EntityManager em;

    @Override
    public GeneralPost save(GeneralPost generalPost) {
        em.persist(generalPost);
        em.flush();
        em.clear();
        return generalPost;
    }

    @Override
    public List<GeneralPost> findAll() {
        return em.createQuery("select p from GeneralPost p", GeneralPost.class).getResultList();
    }

    @Override
    public GeneralPost findById(Long id) {
        return em.find(GeneralPost.class, id);
    }



    @Override
    public void delete(GeneralPost generalPost) {
        em.remove(generalPost);
    }
}
