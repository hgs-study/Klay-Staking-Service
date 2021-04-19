package com.klaystakingservice.common.security.handler;

import com.klaystakingservice.common.error.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Manager;
import org.apache.catalina.session.StandardSession;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
@Slf4j
public class AuthFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String message = "";
        if (exception instanceof AuthenticationServiceException) {
            message = "존재하지 않는 사용자입니다.";

        } else if(exception instanceof BadCredentialsException) {
            message = "아이디 또는 비밀번호가 틀립니다.";

        } else if(exception instanceof LockedException) {
            message ="잠긴 계정입니다.";

        } else if(exception instanceof DisabledException) {
            message= "비활성화된 계정입니다.";

        } else if(exception instanceof AccountExpiredException) {
            message ="만료된 계정입니다.";

        } else if(exception instanceof CredentialsExpiredException) {
            message ="비밀번호가 만료되었습니다.";
        }

        HttpSession session = request.getSession();
        session.setAttribute("AuthenticationFailure",message);
        // 로그인 페이지로 다시 포워딩
        RequestDispatcher dispatcher = request.getRequestDispatcher("/");
        dispatcher.forward(request, response);
    }
}
