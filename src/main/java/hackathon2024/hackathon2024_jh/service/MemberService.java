package hackathon2024.hackathon2024_jh.service;

import hackathon2024.hackathon2024_jh.DTO.MailDTO;
import hackathon2024.hackathon2024_jh.DTO.MemberDTO;
import hackathon2024.hackathon2024_jh.domain.Expert;
import hackathon2024.hackathon2024_jh.domain.Member;
import hackathon2024.hackathon2024_jh.domain.User;
import hackathon2024.hackathon2024_jh.repository.ExpertRepository;
import hackathon2024.hackathon2024_jh.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final ExpertRepository expertRepository;
    private final JwtUtility jwtUtility;
    private final JavaMailSenderImpl mailSender;

    @Value("${jwt.secretKey}")
    private String SECRET_KEY;
    public String MemberOrExpert(String token){
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token)
                    .getBody();

            return claims.get("role", String.class);
        } catch (Exception e) {
            // 예외 처리 (예: 토큰이 유효하지 않은 경우)
            e.printStackTrace();
            return null;
        }
    }

    public Member tokenToMember(String token) {
        return memberRepository.findByUserId(jwtUtility.validateToken(token).getSubject());
    }

    public Expert tokenToExpert(String token) {
        return expertRepository.findByUserId(jwtUtility.validateToken(token).getSubject());
    }


    public User UserfindById(Long id){
        if (memberRepository.findById(id)==null){
            return expertRepository.findById(id);
        }
        return memberRepository.findById(id);
    }

    public User UserfindByUserId(String userId){
        if (memberRepository.findByUserId(userId)==null){
            return expertRepository.findByUserId(userId);
        }
        return memberRepository.findByUserId(userId);
    }

    @Transactional
    public User changeName(String token, String nickname){
        Member member = tokenToMember(token);
        if(member==null){
            Expert expert = tokenToExpert(token);
            expert.setNickname(nickname);
            return expert;
        }
        assert member != null;
        member.setNickname(nickname);


        return member;
    }


    @Transactional
    public Member signUpGeneral(String userId, String password, String nickname,
                               String birth, String gender,
                               String phoneNum){

        //없으면 회원가입 저장
        return  memberRepository.save(new Member(userId, password, nickname,
                birth, gender, phoneNum));
    }



    @Transactional
    public Expert signUpExpert(MemberDTO.ExpertMemberCreateRequest request, MailDTO mailDTO) throws MessagingException, IOException {



        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

            File imgFile = convertMultipartFileToFile(request.getImage());

            String title = "[끄적임] 전문가 자격 승인을 요청하였습니다.";
            String content = "<p>관리자에게 전문가 자격 승인 요청을 전달하였습니다.</p>" +
                    "<p>승인 완료시, 이메일을 통해 안내 드릴 예정입니다. </p>";

            System.out.println(mailDTO.getTo());
            helper.setFrom(mailDTO.getFrom());
            helper.setTo(mailDTO.getTo());
            helper.setSubject(title);
            helper.setText(content, true);


            helper.addInline("email_img", imgFile);
            mailSender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        Expert expert = new Expert(request.getUserId(), request.getPassword(), request.getNickname(),
                request.getBirth(), request.getGender(), request.getPhoneNum(),
                request.getImage().getOriginalFilename(), request.getEmail(), false);
        expertRepository.save(expert);

        return expert;
    }

    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }



    public String login(String userId, String passwd){
        Member member = memberRepository.findByUserId(userId);
        Expert expert = expertRepository.findByUserId(userId);

        if(member != null && member.checkPassword(passwd)){
            return jwtUtility.generateJwtToken(member.getUserId(), "General");
        }
        if(expert != null && expert.checkPassword(passwd)){
            Boolean isExpert = expertRepository.getIsExpert(userId);
            if(isExpert){
                return jwtUtility.generateJwtToken(expert.getUserId(), "Expert");
            }
            else{
                return "전문가 자격 승인 요청이 처리되지 않았습니다.";
            }
        }

        return "아이디 혹은 비밀번호가 일치하지 않습니다.";
    }

}
