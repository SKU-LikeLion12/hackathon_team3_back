package hackathon2024.hackathon2024_jh.repository;

import hackathon2024.hackathon2024_jh.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaMemberRepository implements MemberRepository {
    private final EntityManager em;
    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Member findById(Long id) {
        return em.find(Member.class, id);

    }

    @Override
    public Member findByUserId(String userId) {
        try{
            return em.createQuery("select m from Member m where m.userId=:userId",Member.class)
                    .setParameter("userId",userId).getSingleResult();
        }
        catch(NoResultException e){
            return null;
        }

    }
}
