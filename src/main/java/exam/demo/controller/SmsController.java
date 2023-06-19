package exam.demo.controller;

import exam.demo.dto.ReservationDto;
import exam.demo.entity.Member;

import exam.demo.entity.Reservation;
import exam.demo.service.MemberService;
import exam.demo.service.ReservationService;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.Optional;
import java.util.Random;

@Controller

public class SmsController {

    final DefaultMessageService messageService;
    final MemberService memberService;
    final ReservationService reservationService;

    public SmsController(MemberService memberService, ReservationService reservationService) {
        this.memberService = memberService;
        this.reservationService = reservationService;
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


    @GetMapping("/findMyPassword")
    public String showFindPasswordPage() {
        return "findPassword"; // 비밀번호 찾기 페이지의 Thymeleaf 템플릿 이름을 반환합니다.
    }

    @PostMapping("/findMyPassword")
    public String findPassword(@RequestParam("userName") String userName, Model model) {

        Member member = memberService.getMemberByUsername(userName);

        Random rand = new Random();

        StringBuilder tempPassword = new StringBuilder();
        for(int i = 0; i < 8; i++){
            tempPassword.append(Integer.toString(rand.nextInt(10)));;
        }

        memberService.makeTempPassword(userName, tempPassword.toString());

        Message message = new Message();
        message.setFrom("01051636609");
        message.setTo(member.getPhoneNumber()); // 전달받은 전화번호를 수신번호로 설정합니다.
        message.setText("[졸업시켜조] 임시 비밀번호는 ["+tempPassword+"] 입니다. 로그인후 비밀번호를 변경해주세요");


        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        System.out.println(response);


        return "login"; // 비밀번호를 찾은 후에 보여줄 페이지의 Thymeleaf 템플릿 이름을 반환합니다.
    }


   /* @PostMapping("/movies/reservation/confirm")
    public SingleMessageSentResponse sendReserveSms(ReservationDto reservationDto, Principal principal) throws IOException {

        Reservation reservation = reservationService.createReserve(reservationDto, principal);
        Member member = memberService.getMemberByMemberId(reservation.getMemberId());

        Message message = new Message();
        message.setFrom("01051636609");
        message.setTo(member.getPhoneNumber()); // 전달받은 전화번호를 수신번호로 설정합니다.
        message.setText("[예매정보]\n "+reservation.getReserveId()+"\n "+reservation.getTheaterName()+" \n "+reservation.getScreenroomName()+" \n "+reservation.getMovieName()+" \n "+reservation.getSeatInfo()+" \n"+reservation.getStartTime()+"");

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        System.out.println(response);

        return response;
    }

    */

    @PostMapping("/send/reserve/sms")
    public SingleMessageSentResponse sendReserveSms(HttpSession session) throws IOException {
        // 세션에서 reservation과 member 값을 가져옵니다.
        Reservation reservation = (Reservation) session.getAttribute("reservation");
        Member member = (Member) session.getAttribute("member");

        Message message = new Message();
        message.setFrom("01051636609");
        message.setTo(member.getPhoneNumber());
        message.setText("[예매정보]\n" + reservation.getReserveId() + "\n" + reservation.getTheaterName() + " \n" + reservation.getScreenroomName() + " \n" + reservation.getMovieName() + " \n" + reservation.getSeatInfo() + " \n" + reservation.getStartTime());

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        System.out.println(response);

        return response;
    }

}
