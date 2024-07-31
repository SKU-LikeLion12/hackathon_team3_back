package hackathon2024.hackathon2024_jh.repository;

import hackathon2024.hackathon2024_jh.domain.Post;
import hackathon2024.hackathon2024_jh.domain.SavePost;
import hackathon2024.hackathon2024_jh.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaSavePostRepository implements SavePostRepository {
    private final EntityManager em;

    @Override
    public SavePost findId(Post post, User user){
        try{

            SavePost savePost =  em.createQuery("select s from SavePost s where s.post = :ar and s.saver = :saver", SavePost.class)
                    .setParameter("ar", post)
                    .setParameter("saver", user)
                    .getSingleResult();
            System.out.println(savePost.isSave());
            return savePost;
        }catch(NoResultException err){
            return null;
        }
    }

    @Override
    public SavePost findById(Long id){
        return em.find(SavePost.class, id);
    }

    @Override
    public void save(SavePost savePost) {
        if (savePost.getId()==0){
            em.persist(savePost);
        }
        else{
            em.merge(savePost);
        }
    }

    @Override
    public Long countSaveSize(Long postId){
        Long size = em.createQuery("SELECT COUNT(s) FROM SavePost s WHERE post.id = :postId AND isSave = TRUE", Long.class)
                .setParameter("postId", postId).getSingleResult();
        return size;
    }

    @Override
    public List<Post> findSaveposts(Long userId){
        return em.createQuery("select s.post from SavePost s where saver.id = :saver and isSave = true", Post.class)
                .setParameter("saver", userId).getResultList();
    }

//    @Override
//    public Boolean checkIssave(Long a)
}
