package exam.demo.entity;


import exam.demo.dto.MemberDto;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@NoArgsConstructor
@Getter
@Entity
@Table(name = "MEMBER")
public class Member implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="memberID")
    private Long memberId;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    public Member(String user_name, String password, String email )
    {
        this.userName = user_name;
        this.password = password;
        this.email = email;
        this.roleType = RoleType.USER;
    }

    public Member(MemberDto memberDto)
    {
        this.userName = memberDto.getUserName();
        this.password = memberDto.getPassword();
        this.email = memberDto.getEmail();
        this.roleType = RoleType.USER;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + roleType.name()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isEnabled() {
        // 사용자 계정이 활성화되어 있다고 가정합니다.
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        // 사용자 계정이 만료되지 않았다고 가정합니다.
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // 사용자 계정이 잠겨있지 않다고 가정합니다.
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 사용자의 인증 정보가 만료되지 않았다고 가정합니다.
        return true;
    }

}
