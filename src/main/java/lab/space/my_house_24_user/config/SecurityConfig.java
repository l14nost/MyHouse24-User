package lab.space.my_house_24_user.config;

import lab.space.my_house_24_user.entity.User;
import lab.space.my_house_24_user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserRepository userRepository;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/assets/css/**", "/assets/img/**",
                                "/assets/js/**","/assets/vendor/**",
                                "/assets/images/**","/auth/**",
                                "/files/**","/login/**","/register/**", "/privacy-policy/**", "/error/**", "/access-denied").permitAll()
                        .anyRequest().hasAuthority("ACTIVE")
                )
                .oauth2Login(oauth2 ->
                        oauth2
                                .loginPage("/login")
                                .successHandler((request, response, authentication) -> {

                                    OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
                                    Optional<User> user = userRepository.findUserByEmail(oauthUser.getAttribute("email"));
                                    if (user.isPresent()){
                                        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user.get().getEmail(), user.get().getPassword(), user.get().getAuthorities()));
                                    }
                                    else {
                                        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
                                    }

                                    response.sendRedirect("/cabinet/profile");
                                })
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .rememberMe(rememberMe -> rememberMe.rememberMeParameter("remember-me"))
                .logout(form -> form
                        .logoutSuccessUrl("/login?logout")
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
