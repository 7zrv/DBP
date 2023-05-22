package exam.demo.controller;

import exam.demo.dto.MemberDto;
import exam.demo.entity.Member;
import exam.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;


@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public String SignupForm(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(MemberDto memberDto) {

        String encryptedPassword = passwordEncoder.encode(memberDto.getPassword());
        memberDto.setPassword(encryptedPassword);

        Member member = new Member(memberDto);
        memberService.saveMember(member);

        return "redirect:/";
    }


    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }
    //로그인 오류시 에러 메세지
    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "login";
    }

    //사용자 정보 페이지
    @GetMapping("/mypage")
    public String myPage(Model model, Principal principal) {
        String username = principal.getName();
        Member member = memberService.getMemberByUsername(username);
        model.addAttribute("member", member);
        return "mypage";
    }

    // 회원 탈퇴 처리
    @GetMapping("/withdraw")
    public String withdrawUser(Authentication authentication) {
        String username = authentication.getName();
        memberService.withdrawMember(username);
        return "redirect:/"; // 회원 탈퇴 후 리디렉션할 URL
    }

    // 비밀번호 변경 처리
    @PostMapping("/changePassword")
    public String changePassword(Authentication authentication,
                                 @RequestParam String currentPassword,
                                 @RequestParam String newPassword) {
        String username = authentication.getName();
        memberService.changePassword(username, currentPassword, newPassword);
        return "redirect:mypage";
    }


}
