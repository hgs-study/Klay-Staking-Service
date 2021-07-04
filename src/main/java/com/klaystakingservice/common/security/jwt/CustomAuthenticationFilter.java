package com.klaystakingservice.common.security.jwt;

import com.klaystakingservice.business.account.application.AccountService;
import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.common.redis.RedisUtil;
import com.klaystakingservice.common.security.cookie.CookieUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CookieUtil cookieUtil;
    private final AccountService accountService;
    private final RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        log.debug("===== [OncePerRequestFilter] doFilterInternal start =====");
//        final Cookie jwtToken = cookieUtil.getCookie(request, JwtProperties.ACCESS_TOKEN_NAME);
//        final Cookie refreshJwtToken = cookieUtil.getCookie(request, JwtProperties.REFRESH_TOKEN_NAME);
        String accessToken = jwtTokenProvider.resolveToken(request);

        String userKey = null;
        String jwt = null;
        String refreshJwt = null;
        String refreshUserKey = null;

        try{

            if(accessToken != null){
                userKey = jwtTokenProvider.getUserKeyByToken(accessToken);
            }

            if(userKey != null){
                UserDetails userDetails = accountService.findUserDetailsByUserKey(userKey);

                if(isValidJwt(accessToken)){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }catch (ExpiredJwtException e){ //Jwt 만료 시
            Cookie refreshToken = cookieUtil.getCookie(request, JwtProperties.REFRESH_TOKEN_NAME);

            if(refreshToken!=null){
                refreshJwt = refreshToken.getValue();
            }
        }catch(Exception e){

        }

        try{
            if(refreshJwt != null){

                refreshUserKey = redisUtil.getData(refreshJwt);

                if(refreshUserKey.equals(jwtTokenProvider.getUserKeyByToken(refreshJwt))){

                    UserDetails userDetails = accountService.findUserDetailsByUserKey(refreshUserKey);
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                    Account account = accountService.findByUserKey(refreshUserKey);
                    String newToken =jwtTokenProvider.createToken(account.getUserKey(),
                                                                  account.getRoles(),
                                                                  JwtProperties.ACCESS_TOKEN_EXPIRATION_TIME);
                    Cookie newAccessToken = cookieUtil.createCookie(JwtProperties.ACCESS_TOKEN_NAME, newToken, JwtProperties.ACCESS_TOKEN_EXPIRATION_TIME);

                    response.addCookie(newAccessToken);
                }
            }
        }catch(ExpiredJwtException e){

        }
        chain.doFilter(request,response);

    }


    private boolean isValidJwt(String token) {
        log.debug("====== isValidJwt start======");
        return token != null && jwtTokenProvider.validateToken(token);
    }
}
