package hackathon2024.hackathon2024_jh.controller;

import hackathon2024.hackathon2024_jh.DTO.LikePostDTO;
import hackathon2024.hackathon2024_jh.DTO.PostDTO;
import hackathon2024.hackathon2024_jh.domain.ExpertPost;
import hackathon2024.hackathon2024_jh.domain.GeneralPost;
import hackathon2024.hackathon2024_jh.domain.LikePost;
import hackathon2024.hackathon2024_jh.domain.User;
import hackathon2024.hackathon2024_jh.service.ExpertPostService;
import hackathon2024.hackathon2024_jh.service.GeneralPostService;
import hackathon2024.hackathon2024_jh.service.LikePostService;
import hackathon2024.hackathon2024_jh.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import retrofit2.http.POST;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final MemberService memberService;
    private final ExpertPostService expertPostService;
    private final GeneralPostService generalPostService;
    private final LikePostService likePostService;

    //전문가 게시글 등록
    @PostMapping("/expert/post")
    public PostDTO.ResponsePostExpert expertPost(@RequestBody PostDTO.RequestPostExpert requestPostExpert) {
        String user = memberService.MemberOrExpert(requestPostExpert.getToken());
        if (Objects.equals(user, "Expert")){
            System.out.println("Expert");
            ExpertPost expertPost= expertPostService.saveExpertPost(requestPostExpert.getTitle(), requestPostExpert.getContent(), requestPostExpert.getToken());
            return new PostDTO.ResponsePostExpert(expertPost, false);
        }
        return null;


    }

    //전문가 게시글 전부 조회
    @GetMapping("/expert/postall")
    public List<PostDTO.ResponsePostExpert> expertPostAll(@RequestBody(required = false) PostDTO.isLogin request) {
        List<PostDTO.ResponsePostExpert> ResponsePostExpertList = new ArrayList<>();
        for(ExpertPost expertPost : expertPostService.findAll()){

            if (request != null) {    //로그인 한 상태
                boolean isLike = likePostService.checkIslike(expertPost.getId(), request.getToken());
                ResponsePostExpertList.add(new PostDTO.ResponsePostExpert(expertPost, isLike));
            }
            else{
                ResponsePostExpertList.add(new PostDTO.ResponsePostExpert(expertPost, false));
            }

        }

        return ResponsePostExpertList;
    }

    // 전문가 특정 게시글 조회
    @GetMapping("/expert/post/{id}")
    public PostDTO.ResponsePostExpert getexpertPost(@PathVariable Long id, @RequestBody(required = false) PostDTO.isLogin request) {
        ExpertPost expertpost = expertPostService.findPost(id);
        if (request != null) {    //로그인 한 상태
            boolean isLike = likePostService.checkIslike(id, request.getToken());

            return new PostDTO.ResponsePostExpert(expertpost, isLike);
        }

        return new PostDTO.ResponsePostExpert(expertpost, false);
    }

    // 전문가 게시글 수정
    @PutMapping("/expert/post/{id}")
    public PostDTO.ResponsePostExpert updateExpertPost(@PathVariable Long id, @RequestBody(required = false) PostDTO.RequestPostExpert request) {
        ExpertPost expertPost = expertPostService.updatePost(id, request.getTitle(), request.getContent(), request.getToken());

        boolean isLike = likePostService.checkIslike(id, request.getToken());
        return new PostDTO.ResponsePostExpert(expertPost,isLike);
    }

    // 전문가 게시글 삭제
    @DeleteMapping("/expert/post/{id}")
    public String DeleteExpertPost(@PathVariable Long id, @RequestBody PostDTO.RemovePost removePost) {
        expertPostService.deletePost(id, removePost.getToken());
        return "삭제 완료";

    }

    //일반 게시글 작성
    @PostMapping("/general/post")
    public PostDTO.ResponsePostGeneral generalPost(@RequestBody PostDTO.RequestPostGeneral request) {
        String user = memberService.MemberOrExpert(request.getToken());
        if (Objects.equals(user, "General")){
            GeneralPost generalPost= generalPostService.saveGeneralPost(request.getTitle(),request.getContent(), request.getCategory(), request.getToken());
            return new PostDTO.ResponsePostGeneral(generalPost, false);
        }
        return null;
    }

    //일반 게시글 전부 조회
    @GetMapping("/general/postall/{category}")
    public List<PostDTO.ResponsePostGeneral> generalPostAll(@PathVariable String category, @RequestBody(required = false) PostDTO.isLogin request) {
        List<PostDTO.ResponsePostGeneral> responsePostGeneralList = new ArrayList<>();
        for(GeneralPost generalPost : generalPostService.findAll(category)){
            if (request != null) {    //로그인 한 상태
                boolean isLike = likePostService.checkIslike(generalPost.getId(), request.getToken());
                responsePostGeneralList.add(new PostDTO.ResponsePostGeneral(generalPost, isLike));
            }else {
                responsePostGeneralList.add(new PostDTO.ResponsePostGeneral(generalPost, false));
            }
        }
        return responsePostGeneralList;
    }

    // 일반 특정 게시글 조회
    @GetMapping("/general/post/{id}")
    public PostDTO.ResponsePostGeneral getGeneralPost(@PathVariable Long id, @RequestBody(required = false) PostDTO.isLogin request) {
        GeneralPost generalPost = generalPostService.findPost(id);
        if (request != null) {    //로그인 한 상태
            boolean isLike = likePostService.checkIslike(id, request.getToken());

            return new PostDTO.ResponsePostGeneral(generalPost, isLike);
        }

        return new PostDTO.ResponsePostGeneral(generalPost, false);
    }

    @PutMapping("/general/post/{id}")
    public PostDTO.ResponsePostGeneral updateGeneralPost(@PathVariable Long id, @RequestBody PostDTO.RequestPostGeneral request) {
        GeneralPost generalPost = generalPostService.updatePost(id, request.getTitle(), request.getContent(), request.getCategory(), request.getToken());

        boolean isLike = likePostService.checkIslike(id, request.getToken());
        return new PostDTO.ResponsePostGeneral(generalPost, isLike);

    }

    @DeleteMapping("/general/post/{id}")
    public String DeleteGeneralPost(@PathVariable Long id, @RequestBody PostDTO.RemovePost removePost) {
        generalPostService.deletePost(id, removePost.getToken());
        return "삭제 완료";
    }

    //좋아요 토글
    @PostMapping("/post/like/{postId}")
    public LikePostDTO.LikePostResponse clickLike(@RequestBody LikePostDTO.LikeCreateRequest request, @PathVariable("postId") Long id){
        LikePost likePost = likePostService.Islike(id, request.getToken());
        return new LikePostDTO.LikePostResponse(likePost);
    }

    @GetMapping("/post/likes")
    public List<PostDTO.ResponsePost> findlikeArticles(@RequestBody PostDTO.isLogin request){
        String role = memberService.MemberOrExpert(request.getToken());
        User user;
        if(Objects.equals(role, "General")){
             user = memberService.tokenToMember(request.getToken());
        }
        else{
            user = memberService.tokenToExpert(request.getToken());
        }
        List<PostDTO.ResponsePost> responseArticles = likePostService.findLikePost(user);
        return responseArticles;
    }
}
