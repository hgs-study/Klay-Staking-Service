package com.klaystakingservice.common.security.config;

import com.klaystakingservice.business.account.application.AccountService;
import com.klaystakingservice.common.redis.RedisUtil;
import com.klaystakingservice.common.security.cookie.CookieUtil;
import com.klaystakingservice.common.security.handler.AuthFailureHandler;
import com.klaystakingservice.common.security.handler.AuthSuccessHandler;
import com.klaystakingservice.common.security.jwt.CustomAuthenticationFilter;
import com.klaystakingservice.common.security.jwt.JwtAuthenticationFilter;
import com.klaystakingservice.common.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAuthenticationFilter customAuthenticationFilter;


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // authenticationManager를 Bean 등록합니다.
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception{
        web.ignoring().antMatchers("/css/**","/js/**");
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .headers().frameOptions().disable();


        http
            .httpBasic().disable() // rest api 만을 고려하여 기본 설정은 해제하겠습니다.
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰
            .and()
            .authorizeRequests()
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/accounts/**","/orders/**","/stakings/**","/wallets/**").hasRole("USER")
            .anyRequest().permitAll()
            .and()
            .addFilter(jwtAuthenticationFilter)
            .addFilterBefore(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);



    }


}
