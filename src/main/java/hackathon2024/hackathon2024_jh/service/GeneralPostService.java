package hackathon2024.hackathon2024_jh.service;

import hackathon2024.hackathon2024_jh.DTO.PostDTO;
import hackathon2024.hackathon2024_jh.domain.*;
import hackathon2024.hackathon2024_jh.repository.ExpertRepository;
import hackathon2024.hackathon2024_jh.repository.PostExpertRepository;
import hackathon2024.hackathon2024_jh.repository.PostGeneralRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    public List<GeneralPost> findAll(String category) {
        return postGeneralRepository.findAll(category);
    }

    public GeneralPost findPost(Long id) {
        return postGeneralRepository.findById(id);
    }

    public List<PostDTO.ResponsePost> findPostByMember(Member member) {
        List<GeneralPost> myGeneralPosts = postGeneralRepository.findByMember(member);
        List<PostDTO.ResponsePost> myPosts = new ArrayList<>();
        for (GeneralPost generalPost : myGeneralPosts) {
            myPosts.add(new PostDTO.ResponsePost(generalPost,false,false));
        }
        return myPosts;
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