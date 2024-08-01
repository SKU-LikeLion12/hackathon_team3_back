package hackathon2024.hackathon2024_jh.repository;

import hackathon2024.hackathon2024_jh.domain.Member;
import hackathon2024.hackathon2024_jh.domain.MindTest;

import java.util.List;

public interface MindTestRepository {

    void save(MindTest mindTest);

    MindTest findByMemberAndCategory(Member member, String category);

    List<MindTest> findTestByUser(Member member);
}
