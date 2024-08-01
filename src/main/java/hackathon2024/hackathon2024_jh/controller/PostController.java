package hackathon2024.hackathon2024_jh.controller;

import hackathon2024.hackathon2024_jh.DTO.LikePostDTO;
import hackathon2024.hackathon2024_jh.DTO.PostDTO;
import hackathon2024.hackathon2024_jh.DTO.SavePostDTO;
import hackathon2024.hackathon2024_jh.domain.*;
import hackathon2024.hackathon2024_jh.service.*;
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
    private final SavePostService savePostService;
    private final CommentService commentService;

    //전문가 게시글 등록
    @PostMapping("/expert/post")
    public PostDTO.ResponsePostExpert expertPost(@RequestBody PostDTO.RequestPostExpert requestPostExpert) {
        String user = memberService.MemberOrExpert(requestPostExpert.getToken());
        if (Objects.equals(user, "Expert")){
            System.out.println("Expert");
            ExpertPost expertPost= expertPostService.saveExpertPost(requestPostExpert.getTitle(), requestPostExpert.getContent(), requestPostExpert.getToken());
            return new PostDTO.ResponsePostExpert(expertPost, false, false);
        }
        return null;


    }

    //전문가 게시글 전부 조회
    @GetMapping("/expert/postall/{order}")
    public List<PostDTO.ResponsePostExpert> expertPostAll(@PathVariable int order, @RequestBody(required = false) PostDTO.isLogin request) {
        List<PostDTO.ResponsePostExpert> ResponsePostExpertList = new ArrayList<>();
        List<ExpertPost> expertPosts = new ArrayList<>();
        if(order==1) expertPosts = expertPostService.findAll();
        else if (order==2)expertPosts = expertPostService.findAllLikeSize();
        else if (order==3)expertPosts = expertPostService.findAllCommentSize();
        else if(order==4)expertPosts = expertPostService.findAllSaveSize();
        for(ExpertPost expertPost : expertPosts){
            if (request != null) {//로그인 한 상태
                User user;
                String role = memberService.MemberOrExpert(request.getToken());
                if(Objects.equals(role, "General")){
                    user = memberService.tokenToMember(request.getToken());
                }
                else{
                    user = memberService.tokenToExpert(request.getToken());
                }
                boolean isLike = likePostService.checkIslike(expertPost.getId(), user);
                boolean isSave = savePostService.checkIsSave(expertPost.getId(),user);
                ResponsePostExpertList.add(new PostDTO.ResponsePostExpert(expertPost, isLike, isSave));
            }
            else{
                ResponsePostExpertList.add(new PostDTO.ResponsePostExpert(expertPost, false, false));
            }

        }

        return ResponsePostExpertList;
    }

    // 전문가 특정 게시글 조회
    @GetMapping("/expert/post/{id}")
    public PostDTO.ResponsePostExpert getexpertPost(@PathVariable Long id, @RequestBody(required = false) PostDTO.isLogin request) {
        ExpertPost expertpost = expertPostService.findPost(id);
        if (request != null) {    //로그인 한 상태
            User user;
            String role = memberService.MemberOrExpert(request.getToken());
            if(Objects.equals(role, "General")){
                user = memberService.tokenToMember(request.getToken());
            }
            else{
                user = memberService.tokenToExpert(request.getToken());
            }
            boolean isLike = likePostService.checkIslike(expertpost.getId(), user);
            boolean isSave = savePostService.checkIsSave(expertpost.getId(),user);
            return new PostDTO.ResponsePostExpert(expertpost, isLike, isSave);
        }

        return new PostDTO.ResponsePostExpert(expertpost, false, false);
    }

    // 전문가 게시글 수정
    @PutMapping("/expert/post/{id}")
    public PostDTO.ResponsePostExpert updateExpertPost(@PathVariable Long id, @RequestBody(required = false) PostDTO.RequestPostExpert request) {
        ExpertPost expertPost = expertPostService.updatePost(id, request.getTitle(), request.getContent(), request.getToken());
        User user = memberService.tokenToExpert(request.getToken());
        boolean isLike = likePostService.checkIslike(id, user);
        return new PostDTO.ResponsePostExpert(expertPost,isLike, false);
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
            return new PostDTO.ResponsePostGeneral(generalPost, false, false);
        }
        return null;
    }

    //일반 게시글 전부 조회
    @GetMapping("/general/postall/{category}/{order}")
    public List<PostDTO.ResponsePostGeneral> generalPostAll(@PathVariable String category,
                                                            @PathVariable int order,
                                                            @RequestBody(required = false) PostDTO.isLogin request) {
        List<PostDTO.ResponsePostGeneral> responsePostGeneralList = new ArrayList<>();
        List<GeneralPost> generalPosts = new ArrayList<>();
        if(order==1) generalPosts = generalPostService.findAll(category);
        else if (order==2)generalPosts = generalPostService.findAllLikeSize(category);
        else if (order==3)generalPosts = generalPostService.findAllCommentSize(category);
        else if(order==4)generalPosts = generalPostService.findAllSaveSize(category);
        for(GeneralPost generalPost : generalPosts){
            if (request != null) {    //로그인 한 상태
                User user;
                String role = memberService.MemberOrExpert(request.getToken());
                if(Objects.equals(role, "General")){
                    user = memberService.tokenToMember(request.getToken());
                }
                else{
                    user = memberService.tokenToExpert(request.getToken());
                }
                boolean isLike = likePostService.checkIslike(generalPost.getId(), user);
                boolean isSave = savePostService.checkIsSave(generalPost.getId(),user);
                responsePostGeneralList.add(new PostDTO.ResponsePostGeneral(generalPost, isLike, isSave));
            }else {
                responsePostGeneralList.add(new PostDTO.ResponsePostGeneral(generalPost, false, false));
            }
        }
        return responsePostGeneralList;
    }


    // 일반 특정 게시글 조회
    @GetMapping("/general/post/{id}")
    public PostDTO.ResponsePostGeneral getGeneralPost(@PathVariable Long id, @RequestBody(required = false) PostDTO.isLogin request) {
        GeneralPost generalPost = generalPostService.findPost(id);
        if (request != null) {    //로그인 한 상태
            User user;
            String role = memberService.MemberOrExpert(request.getToken());
            if(Objects.equals(role, "General")){
                user = memberService.tokenToMember(request.getToken());
            }
            else{
                user = memberService.tokenToExpert(request.getToken());
            }
            boolean isLike = likePostService.checkIslike(generalPost.getId(), user);
            boolean isSave = savePostService.checkIsSave(generalPost.getId(),user);
            return new PostDTO.ResponsePostGeneral(generalPost, isLike, isSave);
        }

        return new PostDTO.ResponsePostGeneral(generalPost, false, false);
    }

    @PutMapping("/general/post/{id}")
    public PostDTO.ResponsePostGeneral updateGeneralPost(@PathVariable Long id, @RequestBody PostDTO.RequestPostGeneral request) {
        GeneralPost generalPost = generalPostService.updatePost(id, request.getTitle(), request.getContent(), request.getCategory(), request.getToken());
        User user;
        String role = memberService.MemberOrExpert(request.getToken());
        if(Objects.equals(role, "General")){
            user = memberService.tokenToMember(request.getToken());
        }
        else{
            user = memberService.tokenToExpert(request.getToken());
        }
        boolean isLike = likePostService.checkIslike(generalPost.getId(), user);
        boolean isSave = savePostService.checkIsSave(generalPost.getId(),user);
        return new PostDTO.ResponsePostGeneral(generalPost, isLike, isSave);

    }

    @DeleteMapping("/general/post/{id}")
    public String DeleteGeneralPost(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        token = token.substring(7);
        generalPostService.deletePost(id, token);
        return "삭제 완료";
    }

    //내가 작성한 게시글 목록
    @GetMapping("/post/myposts")
    public List<PostDTO.ResponsePost> getPosts(@RequestHeader("Authorization") String token) {
        token = token.substring(7);
        String role = memberService.MemberOrExpert(token);

        if(Objects.equals(role, "General")){
            Member member = memberService.tokenToMember(token);
            return generalPostService.findPostByMember(member);
        }
        else{
            Expert expert = memberService.tokenToExpert(token);
            return expertPostService.findPostByExpert(expert);
        }

    }

    //내가 댓글단 게시글 목록
    @GetMapping("/post/mycommentposts")
    public List<PostDTO.ResponsePost> getCommentPosts(@RequestBody(required = false) PostDTO.isLogin request) {
        String role = memberService.MemberOrExpert(request.getToken());

        if(Objects.equals(role, "General")){
            Member member = memberService.tokenToMember(request.getToken());
            return commentService.getPostsFromGeneralComment(member);
        }
        else{
            Expert expert = memberService.tokenToExpert(request.getToken());
            return commentService.getPostsFromExpertComment(expert);
        }

    }



    //좋아요 토글
    @PostMapping("/post/like/{postId}")
    public LikePostDTO.LikePostResponse clickLike(@RequestBody LikePostDTO.LikeCreateRequest request, @PathVariable("postId") Long id){
        LikePost likePost = likePostService.Islike(id, request.getToken());
        return new LikePostDTO.LikePostResponse(likePost);
    }

    //저장 토글
    @PostMapping("/post/save/{postId}")
    public SavePostDTO.SavePostResponse clickSave(@RequestBody SavePostDTO.SaveCreateRequest request, @PathVariable("postId") Long id){
        SavePost savePost = savePostService.Issave(id, request.getToken());
        return new SavePostDTO.SavePostResponse(savePost);
    }

    //내가 저장한 게시글 목록
    @GetMapping("/post/saves")
    public List<PostDTO.ResponsePost> findSaveArticles(@RequestBody PostDTO.isLogin request){
        User user = memberService.tokenToMember(request.getToken());
        return savePostService.findSavePost(user);
    }


}
