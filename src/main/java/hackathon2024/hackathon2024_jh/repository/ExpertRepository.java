package hackathon2024.hackathon2024_jh.repository;

import hackathon2024.hackathon2024_jh.domain.Expert;

import java.util.List;


public interface ExpertRepository {
    Expert save(Expert member);
    Expert findById(Long id);
    Expert findByUserId(String userId);

    List<Expert> findAll();
    void changeIsExpert(Expert expert, Boolean isExpert);

    Boolean getIsExpert(String userId);
}
