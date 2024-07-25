package hackathon2024.hackathon2024_jh.service;

import hackathon2024.hackathon2024_jh.domain.Expert;
import hackathon2024.hackathon2024_jh.repository.ExpertRepository;
import hackathon2024.hackathon2024_jh.repository.MemberRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {
    private final ExpertRepository expertRepository;
    private final JavaMailSenderImpl mailSender;

    public List<Expert> findAll() {
        return expertRepository.findAll();
    }

    public Expert findById(Long id) {
        return expertRepository.findById(id);
    }

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Transactional
    public Expert changeIsExpert(Expert expert, Boolean isExpert) {
        expertRepository.changeIsExpert(expert, isExpert);
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

            String res;
            if (isExpert) {
                res = "승인 완료";
            } else {
                res = "승인 반려";
            }

            String title = "[끄적임] " + expert.getNickname() + "님, 전문가 자격 요청이 " + res + "되었습니다.";
            String content = generateEmailContent(expert.getNickname(), res);

            helper.setFrom(mailFrom);
            helper.setTo(expert.getEmail());
            helper.setSubject(title);
            helper.setText(content, true);

            mailSender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return expertRepository.findById(expert.getId());
    }

    private String generateEmailContent(String nickname, String result) {
        String contentBody;
        if (result.equals("승인 완료")) {
            contentBody = "<p>안녕하세요, 끄적임 관리자입니다.</p>" +
                    "<p>귀하의 전문가 승인 요청이 <b>수락</b> 되었습니다.</p>" +
                    "<p>끄적임에서 전문가로 활동이 가능하며, 고민이 있는 분들에게 위로의 한 마디를 건네주세요. 🍀</p>";
        } else {
            contentBody = "<p>안녕하세요, 끄적임 관리자입니다.</p>" +
                    "<p>귀하의 전문가 승인이 <b>반려</b>되었습니다.</p>" +
                    "<p>승인이 가능한 서류인지 다시금 확인 부탁드리며, 재신청 부탁드리겠습니다.</p>";
        }

        return "<!DOCTYPE html>" +
                "<html lang='ko'>" +
                "<head>" +
                "<meta charset='UTF-8'>" +
                "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "<title>" + result + "</title>" +
                "<style>" +
                "body {font-family: Arial, sans-serif; margin: 0; padding: 0;}" +
                ".header {background-color: #2DB400; color: white; padding: 20px; text-align: center;}" +
                ".content {padding: 20px;}" +
                ".content p {font-size: 16px; line-height: 1.5;}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='header'>" +
                "<h1>" + "[끄적임] " + nickname + "님, 전문가 자격 요청이 <b>" + result + "</b> 되었습니다." + "</h1>" +
                "</div>" +
                "<div class='content'>" +
                contentBody +
                "<p>감사합니다.</p>" +
                "<p>[끄적임] 팀 드림</p>" +
                "</div>" +
                "</body>" +
                "</html>";
    }



}
