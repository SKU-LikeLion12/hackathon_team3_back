package hackathon2024.hackathon2024_jh.service;

import hackathon2024.hackathon2024_jh.domain.Expert;
import hackathon2024.hackathon2024_jh.domain.ExpertPost;
import hackathon2024.hackathon2024_jh.domain.GeneralPost;
import hackathon2024.hackathon2024_jh.domain.Member;
import hackathon2024.hackathon2024_jh.repository.ExpertRepository;
import hackathon2024.hackathon2024_jh.repository.PostExpertRepository;
import hackathon2024.hackathon2024_jh.repository.PostGeneralRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GeneralPostService {
    private final ExpertRepository expertRepository;
    private final MemberService memberService;
    private final JwtUtility jwtUtility;
    private final PostGeneralRepository postGeneralRepository;

    @Transactional
    public GeneralPost saveGeneralPost(String title, String content, String category, String token) {
        Member member = memberService.tokenToMember(token);
        GeneralPost generalPost = new GeneralPost(title, content, member, category);
        return postGeneralRepository.save(generalPost);
    }

    public List<GeneralPost> findAll() {
        return postGeneralRepository.findAll();
    }

    public GeneralPost findPost(Long id) {
        return postGeneralRepository.findById(id);
    }

    @Transactional
    public GeneralPost updatePost(Long id, String title, String content, String category, String token) {
        GeneralPost generalPost = postGeneralRepository.findById(id);
        Member member = memberService.tokenToMember(token);
        if(member == generalPost.getWriter()){
            generalPost.update(title, content);
        }
        return generalPost;
    }

    @Transactional
    public void deletePost(Long id, String token) {
        GeneralPost generalPost = postGeneralRepository.findById(id);
        Member member = memberService.tokenToMember(token);
        if(member == generalPost.getWriter()) {
            postGeneralRepository.delete(generalPost);
        }
    }
}
