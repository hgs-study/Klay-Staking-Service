package com.klaystakingservice.common.security.config;

import com.klaystakingservice.business.account.application.AccountService;
import com.klaystakingservice.common.security.handler.AuthFailureHandler;
import com.klaystakingservice.common.security.handler.AuthSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthFailureHandler authFailureHandler;
    private final AuthSuccessHandler authSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception{
        web.ignoring().antMatchers("/css/**","/js/**");
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/signUp","/account","/","/account/**","/staking").permitAll()
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/")
                .loginProcessingUrl("/login")
                .usernameParameter("email")
                .successHandler(authSuccessHandler)
                .failureHandler(authFailureHandler)
                .permitAll();

        http.csrf()
                .disable();

        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true);
    }


}
