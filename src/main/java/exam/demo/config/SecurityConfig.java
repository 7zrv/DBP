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
                .invalidSessionUrl("/login")
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
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