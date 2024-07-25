package hackathon2024.hackathon2024_jh.controller;

import static hackathon2024.hackathon2024_jh.DTO.MemberDTO.*;

import hackathon2024.hackathon2024_jh.DTO.MailDTO;
import hackathon2024.hackathon2024_jh.DTO.MessageDTO;
import hackathon2024.hackathon2024_jh.domain.Expert;
import hackathon2024.hackathon2024_jh.domain.Member;
import hackathon2024.hackathon2024_jh.repository.ExpertRepository;
import hackathon2024.hackathon2024_jh.repository.MemberRepository;
import hackathon2024.hackathon2024_jh.service.MemberService;
import hackathon2024.hackathon2024_jh.service.MessageService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MessageService messageService;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final ExpertRepository expertRepository;

    @PostMapping("/signup/general")
    public String signUpGeneral(@RequestBody GeneralMemberCreateRequest request) {
        memberService.signUpGeneral(request.getUserId(), request.getPassword(), request.getNickname(),
                                request.getBirth(), request.getGender(),
                                request.getPhoneNum());

        String token = memberService.login(request.getUserId(), request.getPassword());

        return token;
    }
    @GetMapping("/signup/checkId/{checkId}")
    public String signUpCheckId(@PathVariable("checkId") String checkId) {
        Member memberGeneral = memberRepository.findByUserId(checkId);
        Expert memberExpert = expertRepository.findByUserId(checkId);
        //이미 멤버가 있다는 것
        if(memberGeneral != null || memberExpert!=null) return "이미 존재하는 아이디 입니다.";
        return "사용 가능한 아이디입니다.";
    }

    @PostMapping("/login")
    public String login(@RequestBody MemberLoginRequest request){
        return memberService.login(request.getUserId(),request.getPassword());
    }
    @Value("${spring.mail.username}")
    private String mailFrom;

    @PostMapping("/signup/Expert")
    public String signUpExpert(@RequestPart("userId") String userId,
                               @RequestPart("password") String password,
                               @RequestPart("nickname") String nickname,
                               @RequestPart("birth") String birth,
                               @RequestPart("gender") String gender,
                               @RequestPart("phoneNum") String phoneNum,
                               @RequestPart("email") String email,
                               @RequestPart("image") MultipartFile image) throws MessagingException, IOException {



        MailDTO mailDTO = new MailDTO();
        mailDTO.setFrom(mailFrom); // 발신자 이메일 주소 설정 필요
        mailDTO.setTo(email); // 수신자 이메일 주소 설정 필요
        // ExpertMemberCreateRequest 객체 설정
        ExpertMemberCreateRequest request = new ExpertMemberCreateRequest();
        request.setUserId(userId);
        request.setNickname(nickname);
        request.setPassword(password);
        request.setGender(gender);
        request.setPhoneNum(phoneNum);
        request.setEmail(email);
        request.setBirth(birth);
        request.setImage(image);
        memberService.signUpExpert(request, mailDTO);


        return "승인 요청 완료";
    }

    @PostMapping("/check/sendSMS")
    public String sendSMS(@RequestBody MessageDTO.RequestMessage message) {
        //return messageService.createRandomNumber();
        return messageService.sendSMS(message.getPhoneNumber());
    }

    @GetMapping("/check/certification")
    public String sendSMS(@RequestBody MessageDTO.CertificationNum certification) {
        return messageService.verifySms(certification);

    }



}
