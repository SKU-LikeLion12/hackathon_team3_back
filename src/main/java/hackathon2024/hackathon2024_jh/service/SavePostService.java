
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
public class SavePostService {
    private final MemberService memberService;
    private final GeneralPostService generalPostService;
    private final ExpertPostService expertPostService;
    private final SavePostRepository savePostRepository;
    private final LikePostRepository likePostRepository;
    private final LikePostService likePostService;
//    private final LikePostService likePostService;

    @Transactional
    public SavePost Issave(Long postId, String token) {
        User user = memberService.tokenToMember(token);
        Post post = generalPostService.findPost(postId);
        if (Objects.isNull(post)) {
            post= expertPostService.findPost(postId);
        }

        SavePost savePost = savePostRepository.findId(post, user);


        if(savePost==null) {
            savePost = new SavePost(user, post, true);
            savePostRepository.save(savePost);
        }

        else{
            System.out.println(!savePost.isSave());
            boolean newSaveStatus = !savePost.isSave();
            savePost.setisSave(newSaveStatus);
            savePostRepository.save(savePost);
        }

        Long saveSize = savePostRepository.countSaveSize(postId);
        post.setSaveSize(saveSize);
        return savePostRepository.findId(post, user);

    }

    //저장 눌렀는지 확인
    public boolean checkIsSave(long postId, User user) {
        //String role = memberService.MemberOrExpert(token);
        Post post = generalPostService.findPost(postId);
        if (Objects.isNull(post)) {
            post= expertPostService.findPost(postId);
        }

        SavePost savePost = savePostRepository.findId(post, user);
        if(savePost==null) {
            return false;
        }

        return savePost.isSave();
    }

    public List<PostDTO.ResponsePost> findSavePost(User user) {
        List<Post> savePosts = savePostRepository.findSaveposts(user.getId());
        List<PostDTO.ResponsePost> posts = new ArrayList<>();
        for(Post post : savePosts) {
            boolean isLike = likePostService.checkIslike(post.getId(), user);

            posts.add(new PostDTO.ResponsePost(post, isLike, true));
        }
        return posts;
    }



}
