package hackathon2024.hackathon2024_jh.repository;

import hackathon2024.hackathon2024_jh.domain.Expert;
import hackathon2024.hackathon2024_jh.domain.ExpertPost;
import hackathon2024.hackathon2024_jh.domain.GeneralPost;

import java.util.List;

public interface PostExpertRepository {
    ExpertPost save(ExpertPost expertPost);
    List<ExpertPost> findAll();

    //좋아요순
    List<ExpertPost> findAllLikedesc();

    //댓글순
    List<ExpertPost> findAllCommentdesc();

    //저장순
    List<ExpertPost> findAllSavedesc();

    ExpertPost findById(Long id);

    List<ExpertPost> findByExpert(Expert expert);

    void delete(ExpertPost expertPost);
}
