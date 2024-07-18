package hackathon2024.hackathon2024_jh.repository;

import hackathon2024.hackathon2024_jh.domain.Member;

public interface MemberRepository {
    Member save(Member member);
    Member findById(Long id);
    Member findByUserId(String userId);

}
