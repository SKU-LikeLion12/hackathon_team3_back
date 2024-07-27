package hackathon2024.hackathon2024_jh.service;

import hackathon2024.hackathon2024_jh.domain.Expert;
import hackathon2024.hackathon2024_jh.domain.ExpertPost;
import hackathon2024.hackathon2024_jh.repository.ExpertRepository;
import hackathon2024.hackathon2024_jh.repository.MemberRepository;
import hackathon2024.hackathon2024_jh.repository.PostExpertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExpertPostService {
    private final ExpertRepository expertRepository;
    private final MemberService memberService;
    private final JwtUtility jwtUtility;
    private final PostExpertRepository postExpertRepository;

    @Transactional
    public ExpertPost saveExpertPost(String title, String content, String token) {
        Expert expert = memberService.tokenToExpert(token);
        ExpertPost expertPost = new ExpertPost(title, content, expert, null);
        return postExpertRepository.save(expertPost);
    }
    public List<ExpertPost> findAll() {
        return postExpertRepository.findAll();
    }

    public ExpertPost findPost(Long id) {
        return postExpertRepository.findById(id);
    }
}
