package com.nc13.movie.config;

import com.nc13.movie.service.UserAuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, UserAuthService userAuthService) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/WEB-INF/**").permitAll()
                        .requestMatchers("/images/**").permitAll()
                        .requestMatchers("/","/user/*").permitAll()
                        .requestMatchers("/movie/write").hasAnyAuthority("ADMIN")
                        .anyRequest().authenticated())
                .formLogin((formLogIn)-> formLogIn
                        .loginPage("/")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/user/showMenu")
                        .loginProcessingUrl("/user/auth")
                ).userDetailsService(userAuthService);

        return httpSecurity.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
