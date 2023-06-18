package exam.demo.controller;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Random;

@Controller
public class SmsController {

    final DefaultMessageService messageService;

    public SmsController() {
        // 반드시 계정 내 등록된 유효한 API 키, API Secret Key를 입력해주셔야 합니다!
        this.messageService = NurigoApp.INSTANCE.initialize("NCS91UYD0KMJ6WHE", "NPESTXLDHG9NDDCYIJQAXVGFGL6JN5VL", "https://api.coolsms.co.kr");
    }

    @PostMapping("/send/certNum")
    public SingleMessageSentResponse sendCertSms(@RequestParam("phoneNumber") String phoneNumber, HttpServletRequest request) {
        Random rand = new Random();

        StringBuilder certiNum = new StringBuilder();
        for(int i = 0; i < 6; i++){
            certiNum.append(Integer.toString(rand.nextInt(10)));;
        }

        Message message = new Message();
        message.setFrom("01051636609");
        message.setTo(phoneNumber); // 전달받은 전화번호를 수신번호로 설정합니다.
        message.setText("[졸업시켜조] 인증번호는 ["+certiNum+"] 입니다.");

        HttpSession session = request.getSession();
        session.setAttribute("certificationNumber", certiNum.toString());
        session.setMaxInactiveInterval(180); // 인증번호 세션의 유효 시간(초)을 설정합니다. 필요에 따라 조정 가능합니다.


        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        System.out.println(response);

        return response;
    }


    @PostMapping("/verifyCode")
    @ResponseBody
    public boolean verifyCode(@RequestParam("verificationCode") String verificationCode, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String storedCode = (String) session.getAttribute("certificationNumber");

        boolean isVerified = verificationCode.equals(storedCode);

        return isVerified;
    }
}
