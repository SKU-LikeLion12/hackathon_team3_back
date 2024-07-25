package hackathon2024.hackathon2024_jh.repository;

import hackathon2024.hackathon2024_jh.domain.ExpertPost;

import java.util.List;

public interface PostExpertRepository {
    ExpertPost save(ExpertPost expertPost);
    List<ExpertPost> findAll();
    ExpertPost findById(Long id);
    ExpertPost update(ExpertPost expertPost);
    void delete(ExpertPost expertPost);
}
