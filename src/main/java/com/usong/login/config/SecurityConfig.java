package com.usong.login.config;

import com.usong.login.filter.LoginAuthenticationFilter;
import com.usong.login.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.GenericApplicationListenerAdapter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.context.DelegatingApplicationListener;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.*;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.security.web.session.SimpleRedirectSessionInformationExpiredStrategy;

import javax.annotation.Resource;
import java.util.Arrays;


@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyUserDetailService userDetailsService;
    @Autowired
    DelegatingApplicationListener delegatingApplicationListener;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*
        * 若使用 auth.inMemoryAuthentication() 可以直接把帳號密碼寫在memory中
        * 但此處帳號密碼需要從資料庫取得
        * 所以要用 auth.userDetailsService()
        * 並把我自定義的 userDetailsService 帶進去
        *
        * 在我的 userDetailsService中
        * 我覆寫了 loadUserByUsername() 這個 function
        * 因為他再驗證帳密時會調用到 loadUserByUsername()
        * 於是我去控制他要去哪個 DB 撈正確的帳號密碼來做驗證
        *
        * 後面的 passwordEncoder 是要告訴驗證器說我的密碼編碼方式
        * 因為 DB 裡面的密碼不會也不應該存明碼
        * 所以這裡就必須要讓驗證器用「與 DB 密碼相同的編碼方式」去將登入者打的密碼進行加密並進行比對
        *
        * 以下為幾種加密的說明
        *   BCryptPasswordEncoder：Spring Security 推薦使用，使用BCrypt單向hash演算法來加密。
        *   MessageDigestPasswordEncoder：用作傳統的加密方式加密(支持MD5、SHA-1、SHA-256...)
        *   DelegatingPasswordEncoder：最常用的，根據加密類型id進行不同方式的加密，兼容性強
        *   NoOpPasswordEncoder：明文， 不做加密
        * */
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /**
         * 此處實現 前後端分離登入功能
         * 登入成功轉址到 index.html
         * API 拜訪權限在此設定
         * Server 的 Session 也可在此做控制
         * 因為搭配SPA，只有登入成功會由server端進行轉址，後續轉址工作必須交由前端操作
         * */

       http
               .exceptionHandling() // exception發生時的處裡
               /* 當用戶發出 Request 出現認證異常
                * 該異常會先在 ExceptionTranslationFilter 這層被捕獲
                * 並調用 handleSpringSecurityException 處裡異常
                * 他會判斷是 AuthenticationException 或是 AccessDeniedException
                * */
               .authenticationEntryPoint(new MyAuthenticationEntryPoint())
               .accessDeniedHandler(new MyAccessDeniedHandler())
               .and()
               .addFilterAt(loginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
               .authorizeRequests()
               // ▼ 要ROLE_前墜
               .antMatchers(HttpMethod.GET,"/hasRootAuthority").hasAuthority("ROLE_ROOT")
               .antMatchers(HttpMethod.GET,"/hasRootAndUSERAuthority").hasAnyAuthority("ROLE_ROOT,ROLE_USER")
               // ▼ 不用ROLE_前墜
               .antMatchers(HttpMethod.GET,"/hasRootRole").hasAnyRole("ROOT")
               .antMatchers(HttpMethod.GET,"/hasAnyRole").hasAnyRole("ROOT,USER")
               .antMatchers("/USER/**").hasAnyAuthority("ROLE_USER")
               .antMatchers("/ROOT/**").hasAnyAuthority("ROLE_ROOT")
               .antMatchers("/**.js","/**.css","/","/**.ico","/**.txt").permitAll()
               .anyRequest().authenticated() // 沒被設置的路徑.無權便禁止訪問，須放在最後一行！
               .and()
               .logout()
               .logoutUrl("/api/logout")
               .logoutSuccessHandler(new MyLogoutSuccessHandler())
               .and().csrf().disable();

    }


    @Bean
    LoginAuthenticationFilter loginAuthenticationFilter() throws Exception {
        LoginAuthenticationFilter filter = new LoginAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setAuthenticationSuccessHandler(new MyAuthenticationSuccessHandler());
        filter.setAuthenticationFailureHandler(new MyAuthenticationFailureHandler());
        filter.setFilterProcessesUrl("/api/login");
        return filter;
    }


}

