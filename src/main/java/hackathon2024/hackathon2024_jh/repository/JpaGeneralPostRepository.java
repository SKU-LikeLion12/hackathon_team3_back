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
    public List<GeneralPost> findAll(String category) {
        return em.createQuery("select p from GeneralPost p where p.category = :c order by createTime desc ", GeneralPost.class)
                .setParameter("c",category).getResultList();
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
