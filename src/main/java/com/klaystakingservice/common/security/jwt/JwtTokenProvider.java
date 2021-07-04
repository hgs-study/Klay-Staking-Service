package com.klaystakingservice.common.security.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider {

    private String secretKey = JwtProperties.SECRET;

    //객체 초기화, secretKey를 base64로 인코딩한다.
    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT 토큰 생성
    public String createToken(String userKey, List<String> roles, long expireTime) {
        log.debug("=========createToken start ======");
        Claims claims = Jwts.claims().setSubject(userKey); // JWT payload 에 저장되는 정보단위
        claims.put("roles", roles); // 정보는 key / value 쌍으로 저장된다.
        Date now = new Date();

        return Jwts.builder()
                    .setClaims(claims) // 정보 저장
                    .setIssuedAt(now) // 토큰 발행 시간 정보
                    .setExpiration(new Date(now.getTime() + expireTime)) // set Expire Time
                    .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과 signature 에 들어갈 secret값 세팅
                    .compact();
    }

    // 토큰에서 회원 정보 추출
    public String getUserKeyByToken(String token) {
        return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
    }

    // Request의 Header에서 token 값을 가져옵니다. "Authorization" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {

        String token = request.getHeader(JwtProperties.REQUEST_HEADER_NAME);
        if (StringUtils.hasText(token) && token.startsWith(JwtProperties.TOKEN_PREFIX)) {
            token = token.replace(JwtProperties.TOKEN_PREFIX,"");

        }
        return token;
//        return request.getHeader(JwtProperties.REQUEST_HEADER_NAME);
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {

            Jws<Claims> claims = Jwts.parser()
                                     .setSigningKey(secretKey)
                                     .parseClaimsJws(jwtToken);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            e.printStackTrace();
            log.error("=== 잘못된 형식의 토큰 ===");
        } catch (ExpiredJwtException e) {
            log.error(e.getMessage());
            log.error("=== 만료된 토큰 ===");
        } catch (UnsupportedJwtException e) {
            log.error(e.getMessage());
            log.error("=== 지원되지 않는 Jwt 형식 ===");
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            log.error("=== 잘못된 토큰 ===");
        }

        return false;
    }
}
