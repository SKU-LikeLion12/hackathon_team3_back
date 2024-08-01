package hackathon2024.hackathon2024_jh.repository;

import hackathon2024.hackathon2024_jh.domain.GeneralPost;
import hackathon2024.hackathon2024_jh.domain.Member;

import java.util.List;

public interface PostGeneralRepository {
    GeneralPost save(GeneralPost generalPost);
    List<GeneralPost> findAll(String category);

    //좋아요순 정렬
    List<GeneralPost> findAllLikedesc(String category);

    //댓글순
    List<GeneralPost> findAllCommentdesc(String category);

    //저장순
    List<GeneralPost> findAllSavedesc(String category);

    GeneralPost findById(Long id);

    List<GeneralPost> findByMember(Member member);

    void delete(GeneralPost generalPost);
}
