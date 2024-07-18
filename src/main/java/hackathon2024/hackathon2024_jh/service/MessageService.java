package hackathon2024.hackathon2024_jh.service;


import hackathon2024.hackathon2024_jh.DTO.MessageDTO;
import hackathon2024.hackathon2024_jh.repository.SmsCertification;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final SmsCertification smsCertification;
    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

    @Value("${coolsms.apikey}")
    private String apiKey;

    @Value("${coolsms.apisecret}")
    private String apiSecret;

    @Value("${coolsms.fromnumber}")
    private String fromNumber;

    public String createRandomNumber() {
        //System.out.println(apiKey+ apiSecret+ fromNumber);
        Random rand = new Random();
        StringBuilder randomNum = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            String random = Integer.toString(rand.nextInt(10));
            randomNum.append(random);
        }

        return randomNum.toString();
    }

    public String sendSMS(String phoneNumber) {
        DefaultMessageService messageService =  NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");
        // Message 패키지가 중복될 경우 net.nurigo.sdk.message.model.Message로 치환하여 주세요
        Message message = new Message();
        message.setFrom(fromNumber);
        message.setTo(phoneNumber);
        String randomNum = createRandomNumber();
        String text = "[끄적임] 본인 확인을 위해 인증번호 ["+randomNum+"]를 입력해주세요.";
        message.setText(text);

        try {
            // send 메소드로 ArrayList<Message> 객체를 넣어도 동작합니다!
            smsCertification.createSmsCertification(phoneNumber,randomNum);
            messageService.send(message);
            return "문자 전송이 완료되었습니다.";
        } catch (NurigoMessageNotReceivedException exception) {
            // 발송에 실패한 메시지 목록을 확인할 수 있습니다!
            System.out.println(exception.getFailedMessageList());
            System.out.println(exception.getMessage());
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }


        return null;
    }

    public String verifySms(MessageDTO.CertificationNum requestDto) {
        if (isVerify(requestDto)) {
            return "인증번호가 일치하지 않습니다.";

        }
        smsCertification.deleteSmsCertification(requestDto.getPhoneNumber());

        return "인증 완료되었습니다.";
    }

    private boolean isVerify(MessageDTO.CertificationNum requestDto) {
        return !(smsCertification.hasKey(requestDto.getPhoneNumber()) &&
                smsCertification.getSmsCertification(requestDto.getPhoneNumber())
                        .equals(requestDto.getRandomNumber()));
    }




}
