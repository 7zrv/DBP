package exam.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/user/**").authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/movies/register").hasRole("ADMIN")
                .anyRequest().permitAll()

                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/loginProc")
                .failureUrl("/login/error")
                .successHandler((request, response, authentication) -> {
                    request.getSession().setAttribute("loggedIn", true);
                    response.sendRedirect("/");
                })

                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler((request, response, authentication) -> {
                    request.getSession().removeAttribute("loggedIn");
                    response.sendRedirect(getRedirectUrl(request));
                })

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .invalidSessionUrl("/login") // 세션이 유효하지 않을 경우 리다이렉트할 URL 설정
                .maximumSessions(1) // 동일한 사용자의 중복 세션 허용 여부 설정
                .maxSessionsPreventsLogin(false) // 중복 로그인 시 이전 세션 종료 여부 설정
                .expiredUrl("/login?expired")
                .and()

                .and()
                .build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private String getRedirectUrl(HttpServletRequest request) {
        String referer = request.getHeader("Referer"); // 이전 페이지의 URL 가져오기
        if (referer != null) {
            return referer;
        }
        return "/"; // 이전 페이지의 URL이 없으면 기본적으로 홈 페이지로 리다이렉트
    }
}