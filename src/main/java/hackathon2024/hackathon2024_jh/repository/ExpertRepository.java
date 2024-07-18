package hackathon2024.hackathon2024_jh.repository;

import hackathon2024.hackathon2024_jh.domain.Expert;


public interface ExpertRepository {
    Expert save(Expert member);
    Expert findById(Long id);
    Expert findByUserId(String userId);
}
