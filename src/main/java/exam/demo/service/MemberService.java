package exam.demo.service;

import exam.demo.entity.Member;
import exam.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입 메서드
    public void saveMember(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
    }

    //중복확인 메서드
    private void validateDuplicateMember(Member member) {
        memberRepository.findByUserName(member.getUsername())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 가입된 회원입니다.");
                });
    }

    public Member getMemberByUsername(String username) {
        Optional<Member> optionalMember = memberRepository.findByUserName(username);
        return optionalMember.orElse(null);
    }


    //회원탈퇴 메서드
    public void withdrawMember(String username) {
        Member member = getMemberByUsername(username);
        memberRepository.delete(member);
    }

    //비밀번호 변경 메서드
    public void changePassword(String username, String currentPassword, String newPassword){
        Member member = getMemberByUsername(username);
        if (passwordEncoder.matches(currentPassword, member.getPassword())) {
            member.updatePassword(passwordEncoder.encode(newPassword));
        } else {
            throw new IllegalArgumentException("현재 비밀번호를 잘못 입력했습니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findByUserName(username);

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            return member;
        }

        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
