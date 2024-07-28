package hackathon2024.hackathon2024_jh.repository;

import hackathon2024.hackathon2024_jh.domain.LikePost;
import hackathon2024.hackathon2024_jh.domain.Post;
import hackathon2024.hackathon2024_jh.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaLikePostRepository implements LikePostRepository {
    private final EntityManager em;

    @Override
    public LikePost findId(Post post, User user){
        try{

            LikePost likePost =  em.createQuery("select l from LikePost l where l.post = :ar and l.liker = :liker", LikePost.class)
                    .setParameter("ar", post)
                    .setParameter("liker", user)
                    .getSingleResult();
            System.out.println(likePost.isLike());
            return likePost;
        }catch(NoResultException err){
            return null;
        }
    }

    @Override
    public LikePost findById(Long id){
        return em.find(LikePost.class, id);
    }

    @Override
    public void save(LikePost likePost) {
        if (likePost.getId()==0){
            em.persist(likePost);
        }
        else{
            em.merge(likePost);
        }
    }

    @Override
    public Long countLikeSize(Long postId){
        Long size = em.createQuery("SELECT COUNT(l) FROM LikePost l WHERE post.id = :postId AND isLike = TRUE", Long.class)
                .setParameter("postId", postId).getSingleResult();
        return size;
    }

    @Override
    public List<Post> findLikeArticles(Long userId){
        return em.createQuery("select l.post from LikePost l where liker.id = :liker and isLike = true", Post.class)
                .setParameter("liker", userId).getResultList();
    }
}
