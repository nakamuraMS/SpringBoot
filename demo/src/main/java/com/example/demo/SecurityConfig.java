package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login").permitAll() // ログインページは誰でもアクセス可
                .requestMatchers("/api/**").permitAll() // APIエンドポイントは誰でもアクセス可
                .anyRequest().authenticated()          // それ以外はログイン必須
            )
            .formLogin(form -> form
                .loginPage("/login")           // ログインページのURL
                .defaultSuccessUrl("/memos")   // ログイン成功後の遷移先
                .failureUrl("/login?error")    // ログイン失敗時の遷移先
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout") // ログアウト後の遷移先
                .permitAll()
            )
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/**") // ← APIエンドポイントはCSRF除外
            );

        return http.build();
    }

    // パスワードをBCryptでハッシュ化するBean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}