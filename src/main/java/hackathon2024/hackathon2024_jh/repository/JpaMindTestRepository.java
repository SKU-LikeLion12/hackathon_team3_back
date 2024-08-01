package hackathon2024.hackathon2024_jh.repository;

import hackathon2024.hackathon2024_jh.domain.Member;
import hackathon2024.hackathon2024_jh.domain.MindTest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaMindTestRepository implements MindTestRepository {
    private final EntityManager em;

    @Override
    public void save(MindTest mindTest) {
        MindTest existingMindTest = findByMemberAndCategory(mindTest.getTester(), mindTest.getCategory());
        if (existingMindTest==null){
            em.persist(mindTest);
        }
        else{
            existingMindTest.setScore(mindTest.getScore());
            existingMindTest.setTestTime(mindTest.getTestTime());
            em.merge(existingMindTest);
            em.flush();
        }
    }

    @Override
    public MindTest findByMemberAndCategory(Member member, String category){
        try{
            return em.createQuery("select t from MindTest t where t.tester = :tester and t.category = :category", MindTest.class)
                    .setParameter("tester", member)
                    .setParameter("category", category)
                    .getSingleResult();
        }catch (NoResultException err){
            return null;
        }
    }

    @Override
    public List<MindTest> findTestByUser(Member member) {
        return em.createQuery("select t from MindTest t where t.tester = :tester",MindTest.class)
                .setParameter("tester", member).getResultList();
    }
}
