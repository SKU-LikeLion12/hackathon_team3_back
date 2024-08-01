package hackathon2024.hackathon2024_jh.repository;

import hackathon2024.hackathon2024_jh.domain.GeneralPost;
import hackathon2024.hackathon2024_jh.domain.Member;
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

    //최신순 정렬
    @Override
    public List<GeneralPost> findAll(String category) {
        return em.createQuery("select p from GeneralPost p where p.category = :c order by createTime desc ", GeneralPost.class)
                .setParameter("c",category).getResultList();
    }

    //좋아요순 정렬
    @Override
    public List<GeneralPost> findAllLikedesc(String category) {
        return em.createQuery("select p from GeneralPost p where p.category = :c order by likeSize desc ", GeneralPost.class)
                .setParameter("c",category).getResultList();
    }

    //댓글순
    @Override
    public List<GeneralPost> findAllCommentdesc(String category) {
        return em.createQuery("select p from GeneralPost p where p.category = :c order by commentSize desc ", GeneralPost.class)
                .setParameter("c",category).getResultList();
    }

    //저장순
    @Override
    public List<GeneralPost> findAllSavedesc(String category) {
        return em.createQuery("select p from GeneralPost p where p.category = :c order by saveSize desc ", GeneralPost.class)
                .setParameter("c",category).getResultList();
    }

    @Override
    public GeneralPost findById(Long id) {
        return em.find(GeneralPost.class, id);
    }

    @Override
    public List<GeneralPost> findByMember(Member member) {
        return em.createQuery("select p from GeneralPost p where p.writer = :writer order by createTime desc ", GeneralPost.class)
                .setParameter("writer",member).getResultList();
    }

    @Override
    public void delete(GeneralPost generalPost) {
        em.remove(generalPost);
    }
}
