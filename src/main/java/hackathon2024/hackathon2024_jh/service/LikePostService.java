package hackathon2024.hackathon2024_jh.service;

import hackathon2024.hackathon2024_jh.DTO.PostDTO;
import hackathon2024.hackathon2024_jh.domain.*;
import hackathon2024.hackathon2024_jh.repository.LikePostRepository;
import hackathon2024.hackathon2024_jh.repository.SavePostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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
    public boolean checkIslike(long postId, User user) {

        Post post = generalPostService.findPost(postId);
        if (Objects.isNull(post)) {
            post= expertPostService.findPost(postId);
        }

        LikePost likePost = likePostRepository.findId(post, user);
        if(likePost==null) {
            return false;
        }

        return likePost.isLike();
    }

//    public List<PostDTO.ResponsePost> findLikePost(User user) {
//        List<Post> likeArticles = likePostRepository.findLikeArticles(user.getId());
//        List<PostDTO.ResponsePost> posts = new ArrayList<>();
//        for(Post post : likeArticles) {
//            boolean isSave = savePostService.checkIsSave(post.getId(), user);
//            posts.add(new PostDTO.ResponsePost(post, true, isSave));
//        }
//        return posts;
//    }



}
