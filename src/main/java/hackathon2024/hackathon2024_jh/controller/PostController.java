package hackathon2024.hackathon2024_jh.controller;

import hackathon2024.hackathon2024_jh.DTO.PostDTO;
import hackathon2024.hackathon2024_jh.domain.ExpertPost;
import hackathon2024.hackathon2024_jh.service.ExpertPostService;
import hackathon2024.hackathon2024_jh.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final MemberService memberService;
    private final ExpertPostService expertPostService;

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
}
