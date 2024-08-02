package com.risi.risi.configuration;

import com.risi.risi.security.CustomAuthenticationSuccessHandler;
import com.risi.risi.security.JwtRequestFilter;
import com.risi.risi.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private CustomAuthenticationSuccessHandler successHandler;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated()
                );
        http
                .formLogin((auth) -> auth
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/home")
                        .successHandler(successHandler)
                );

        http
                .csrf(AbstractHttpConfigurer::disable);
        http
			.sessionManagement((auth) -> auth
                .sessionFixation((sessionFixation) -> sessionFixation
                        .newSession()
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true))
        );

        http
                .logout((auth) -> auth
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                );

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
