package hackathon2024.hackathon2024_jh.repository;

import hackathon2024.hackathon2024_jh.domain.GeneralPost;

import java.util.List;

public interface PostGeneralRepository {
    GeneralPost save(GeneralPost generalPost);
    List<GeneralPost> findAll();
    GeneralPost findById(Long id);
    void delete(GeneralPost generalPost);
}
