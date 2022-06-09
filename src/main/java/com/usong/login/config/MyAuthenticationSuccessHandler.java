package com.usong.login.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 驗證成功的帳號
        String account = authentication.getName();
        // 驗證成功的權限
        Collection collection = authentication.getAuthorities();
        String authority = collection.iterator().next().toString();

//        HttpSession session = request.getSession();
//        session.setAttribute("logged_in",account);
//        session.setAttribute("user_type",authority);

        Map<String,String> result = new HashMap<>();
        result.put("authority",authority);
        result.put("message","登入成功");
        result.put("account",account);

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(200);
        PrintWriter writer =response.getWriter();
        writer.write(new ObjectMapper().writeValueAsString(result));
        writer.flush();
        writer.close();
    }
}
