package hackathon2024.hackathon2024_jh.controller;

import hackathon2024.hackathon2024_jh.DTO.MindTestDTO;
import hackathon2024.hackathon2024_jh.domain.Member;
import hackathon2024.hackathon2024_jh.domain.MindTest;
import hackathon2024.hackathon2024_jh.service.MemberService;
import hackathon2024.hackathon2024_jh.service.MindTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MindTestController {
    
    private final MemberService memberService;
    private final MindTestService mindTestService;

    //우울증 설문조사
    @PostMapping("/test/result")
    public MindTestDTO.MindTestResponse TestSave(@RequestBody MindTestDTO.testSave request) {
        Member member = memberService.tokenToMember(request.getToken());
        MindTest mindTest = mindTestService.saveMindTest(member,request.getCategory(), request.getScore());

        return new MindTestDTO.MindTestResponse(mindTest, member);


    }

    @GetMapping("test/result")
    public List<MindTestDTO.MindTestResponse> TestGet(@RequestBody MindTestDTO.IsLogin request) {
        Member member = memberService.tokenToMember(request.getToken());
        return mindTestService.getMindTest(member);
    }
}
