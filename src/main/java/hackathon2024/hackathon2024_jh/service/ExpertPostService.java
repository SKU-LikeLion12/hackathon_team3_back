package hackathon2024.hackathon2024_jh.service;

import hackathon2024.hackathon2024_jh.DTO.PostDTO;
import hackathon2024.hackathon2024_jh.domain.Expert;
import hackathon2024.hackathon2024_jh.domain.ExpertPost;
import hackathon2024.hackathon2024_jh.domain.GeneralPost;
import hackathon2024.hackathon2024_jh.domain.Member;
import hackathon2024.hackathon2024_jh.repository.CommentRepository;
import hackathon2024.hackathon2024_jh.repository.ExpertRepository;
import hackathon2024.hackathon2024_jh.repository.MemberRepository;
import hackathon2024.hackathon2024_jh.repository.PostExpertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExpertPostService {
    private final MemberService memberService;
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

    public List<ExpertPost> findAllLikeSize() {
        return postExpertRepository.findAllLikedesc();
    }

    public List<ExpertPost> findAllCommentSize() {
        return postExpertRepository.findAllCommentdesc();
    }

    public List<ExpertPost> findAllSaveSize() {
        return postExpertRepository.findAllSavedesc();
    }

    public ExpertPost findPost(Long id) {
        return postExpertRepository.findById(id);
    }

    public List<PostDTO.ResponsePost> findPostByExpert(Expert expert) {
        List<ExpertPost> myExpertPosts = postExpertRepository.findByExpert(expert);
        List<PostDTO.ResponsePost> myPosts = new ArrayList<>();
        for (ExpertPost expertPost : myExpertPosts) {
            myPosts.add(new PostDTO.ResponsePost(expertPost,false,false));
        }
        return myPosts;
    }


    @Transactional
    public ExpertPost updatePost(Long id, String title, String content, String token) {
        ExpertPost expertPost = postExpertRepository.findById(id);
        Expert expert = memberService.tokenToExpert(token);
        if(expert == expertPost.getWriter()){
            expertPost.update(title, content);
        }
        return expertPost;
    }

    @Transactional
    public void deletePost(Long id, String token) {
        ExpertPost expertPost = postExpertRepository.findById(id);
        Expert expert = memberService.tokenToExpert(token);
        if(expert == expertPost.getWriter()) {
            postExpertRepository.delete(expertPost);
        }
    }
}
