package hackathon2024.hackathon2024_jh.service;

import hackathon2024.hackathon2024_jh.domain.*;
import hackathon2024.hackathon2024_jh.repository.LikePostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LikePostService {
    private final MemberService memberService;
    private final GeneralPostService generalPostService;
    private final LikePostRepository likePostRepository;
    private final ExpertPostService expertPostService;

    @Transactional
    public LikePost Islike(Long postId, String token) {
        User user;
        String role = memberService.MemberOrExpert(token);
        Post post = generalPostService.findPost(postId);
        if (Objects.isNull(post)) {
            post= expertPostService.findPost(postId);
        }
        if(Objects.equals(role, "General")){
            user = memberService.tokenToMember(token);
        }
        else{
            user = memberService.tokenToExpert(token);
        }
        LikePost likePost = likePostRepository.findId(post, user);


        if(likePost==null) {
            likePost = new LikePost(user, post, true);
            likePostRepository.save(likePost);
        }

        else{
            System.out.println(!likePost.isLike());
            boolean newLikeStatus = !likePost.isLike();
            likePost.setisLike(newLikeStatus);
            likePostRepository.save(likePost);
        }

        Long likeSize = likePostRepository.countLikeSize(postId);
        post.setLikeSize(likeSize);
        return likePostRepository.findId(post, user);

    }

    //좋아요 눌렀는지 확인
    public boolean checkIslike(long postId, String token) {
        User user;
        String role = memberService.MemberOrExpert(token);
        Post post = generalPostService.findPost(postId);
        if (Objects.isNull(post)) {
            post= expertPostService.findPost(postId);
        }
        if(Objects.equals(role, "General")){
            user = memberService.tokenToMember(token);
        }
        else{
            user = memberService.tokenToExpert(token);
        }

        LikePost likePost = likePostRepository.findId(post, user);
        if(likePost==null) {
            return false;
        }

        return likePost.isLike();
    }

}
