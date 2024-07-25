package hackathon2024.hackathon2024_jh.repository;

import hackathon2024.hackathon2024_jh.domain.Expert;
import hackathon2024.hackathon2024_jh.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaExpertRepository implements ExpertRepository{
    private final EntityManager em;
    @Override
    public Expert save(Expert expert) {
        em.persist(expert);
        return expert;
    }

    @Override
    public Expert findById(Long id) {
        return em.find(Expert.class, id);
    }

    @Override
    public Expert findByUserId(String userId) {
        try {
            return em.createQuery("select e from Expert e where e.userId=:userId", Expert.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Expert> findAll() {
        return em.createQuery("select e from Expert e order by createTime desc ", Expert.class).getResultList();
    }

    @Override
    public void changeIsExpert(Expert expert, Boolean isExpert) {
        Expert managedExpert = em.find(Expert.class, expert.getId());
        if (managedExpert != null) {
            managedExpert.setIsExpert(isExpert);
            em.flush();
            em.clear();
        }
    }
}
