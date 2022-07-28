package com.usong.login.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    /*
    * 使用 SpringSecurity 的登入功能一定會進來此層 Filter
    *
    * 為了實現前後端分離，
    * 這邊設定讓使用者發出的 登入request 其 ContentType 必須是 json
    * 且 body 的內容，其 key&value 必須為 account&password
    *
    *
    * 在使用原生登入頁面( 有用.formLogin() )的情況
    * 若是要用 postman 等軟體打進來此層filter
    * ContentType 必須是 FormData 且 key&value 必須為 username&password
    * */

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 登入request 其 ContentType 必須是 json
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE) ||
            request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
            ObjectMapper mapper = new ObjectMapper();
            UsernamePasswordAuthenticationToken authRequest = null;
            try (InputStream stream = request.getInputStream()) {
                Map<String, String> body = mapper.readValue(stream, Map.class);
                authRequest = new UsernamePasswordAuthenticationToken(
                        body.get("account"), body.get("password")
                );
                /*
                * 此處先將 Request Body 中的 account&password 取出來 (account&password名稱可以自定義)
                * 並生成一個 UsernamePasswordAuthenticationToken物件 (以下簡稱UPAT)
                * 最後再把這個UPAT物件塞進 AuthenticationManager 物件並返回
                *
                * 注意此時的 UPAT 中的許可權是沒有值的
                * UPAT是處理登入成功回撥方法中的一個引數，裡面包含了使用者資訊、請求資訊等引數。
                * */
            } catch (IOException e) {
                e.printStackTrace();
                authRequest = new UsernamePasswordAuthenticationToken("", "");
            } finally {
                setDetails(request, authRequest);
                // 把 UPAT 物件塞進 AuthenticationManager 物件並返回
                return this.getAuthenticationManager().authenticate(authRequest);
            }
        }
        else {
            return super.attemptAuthentication(request, response);
        }
        // 資料來源 https://www.cnblogs.com/woodwhales/p/10702267.html
    }

}
