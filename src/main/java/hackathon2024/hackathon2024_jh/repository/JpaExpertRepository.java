package hackathon2024.hackathon2024_jh.repository;

import hackathon2024.hackathon2024_jh.domain.Expert;
import hackathon2024.hackathon2024_jh.domain.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
        return null;
    }

    @Override
    public Expert findByUserId(String userId) {
        return null;
    }
}
