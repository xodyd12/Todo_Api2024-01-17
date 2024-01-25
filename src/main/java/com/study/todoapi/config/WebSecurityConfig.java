package com.study.todoapi.config;

import com.study.todoapi.filter.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 시큐리티 기본 보안설정 해제
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors()
                .and()
                .csrf().disable()
                .httpBasic().disable()
                //세션 인증은 더이상 사용 안할것임
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
        // 어떤 요청에서는 인증을 하고 어떤 요청에서는 요청을 안할 것인지 설정
                .authorizeRequests() //어떤 요청에서 인증을 할것인가?
                    .antMatchers("/","/api/auth/**").permitAll() // 이 요청은 인증을 안해도 됨 (로그인을 안하면 홈 화면은 보여짐, 로그인, 회원가입 화면도 보여짐)
//                .antMatchers(HttpMethod.POST, "api/todos").permitAll() // 이 요청은 비회원도 전부 가능하다는 요청
//                .antMatchers("/**").hasRole("ADMIN") //어드민은 전부 가능 하다는 요청
                .anyRequest().authenticated() // 그 외에 것들은 인증을 받아라\
        ;

        //토큰 인증 필터연결
        http.addFilterAfter(jwtAuthFilter, CorsFilter.class);
        return http.build();
    }

}
