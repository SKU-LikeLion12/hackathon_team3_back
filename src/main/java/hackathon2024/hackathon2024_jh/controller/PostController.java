package hackathon2024.hackathon2024_jh.controller;

import hackathon2024.hackathon2024_jh.DTO.PostDTO;
import hackathon2024.hackathon2024_jh.domain.ExpertPost;
import hackathon2024.hackathon2024_jh.domain.GeneralPost;
import hackathon2024.hackathon2024_jh.service.ExpertPostService;
import hackathon2024.hackathon2024_jh.service.GeneralPostService;
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

    //전문가 게시글 등록
    @PostMapping("/expert/post")
    public PostDTO.ResponsePostExpert expertPost(@RequestBody PostDTO.RequestPostExpert requestPostExpert) {
        String user = memberService.MemberOrExpert(requestPostExpert.getToken());
        if (Objects.equals(user, "Expert")){
            System.out.println("Expert");
            ExpertPost expertPost= expertPostService.saveExpertPost(requestPostExpert.getTitle(), requestPostExpert.getContent(), requestPostExpert.getToken());
            return new PostDTO.ResponsePostExpert(expertPost);
        }
        return null;


    }

    //전문가 게시글 전부 조회
    @GetMapping("/expert/postall")
    public List<PostDTO.ResponsePostExpert> expertPostAll(String token) {
        List<PostDTO.ResponsePostExpert> ResponsePostExpertList = new ArrayList<>();
        for(ExpertPost expertPost : expertPostService.findAll()){
            ResponsePostExpertList.add(new PostDTO.ResponsePostExpert(expertPost));
        }

        return ResponsePostExpertList;
    }

    // 전문가 특정 게시글 조회
    @GetMapping("/expert/post/{id}")
    public PostDTO.ResponsePostExpert getexpertPost(@PathVariable Long id, String token) {
        ExpertPost expertpost = expertPostService.findPost(id);
        PostDTO.ResponsePostExpert responsePostExpert = new PostDTO.ResponsePostExpert(expertpost);

        return responsePostExpert;
    }

    // 전문가 게시글 수정
    @PutMapping("/expert/post/{id}")
    public PostDTO.ResponsePostExpert updateExpertPost(@PathVariable Long id, @RequestBody PostDTO.RequestPostExpert request) {
        ExpertPost expertPost = expertPostService.updatePost(id, request.getTitle(), request.getContent(), request.getToken());
        return new PostDTO.ResponsePostExpert(expertPost);
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
            return new PostDTO.ResponsePostGeneral(generalPost);
        }
        return null;
    }

    //일반 게시글 전부 조회
    @GetMapping("/general/postall")
    public List<PostDTO.ResponsePostGeneral> generalPostAll(String token) {
        List<PostDTO.ResponsePostGeneral> responsePostGeneralList = new ArrayList<>();
        for(GeneralPost generalPost : generalPostService.findAll()){
            responsePostGeneralList.add(new PostDTO.ResponsePostGeneral(generalPost));
        }
        return responsePostGeneralList;
    }

    // 일반 특정 게시글 조회
    @GetMapping("/general/post/{id}")
    public PostDTO.ResponsePostGeneral getGeneralPost(@PathVariable Long id, String token) {
        GeneralPost generalPost = generalPostService.findPost(id);
        PostDTO.ResponsePostGeneral responsePostGeneral = new PostDTO.ResponsePostGeneral(generalPost);

        return responsePostGeneral;
    }

    @PutMapping("/general/post/{id}")
    public PostDTO.ResponsePostGeneral updateGeneralPost(@PathVariable Long id, @RequestBody PostDTO.RequestPostGeneral request) {
        GeneralPost generalPost = generalPostService.updatePost(id, request.getTitle(), request.getContent(), request.getCategory(), request.getToken());
        return new PostDTO.ResponsePostGeneral(generalPost);
    }

    @DeleteMapping("/general/post/{id}")
    public String DeleteGeneralPost(@PathVariable Long id, @RequestBody PostDTO.RemovePost removePost) {
        generalPostService.deletePost(id, removePost.getToken());
        return "삭제 완료";
    }
}
