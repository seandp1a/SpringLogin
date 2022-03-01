package com.usong.login.config;

import com.usong.login.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyUserDetailService userDetailsService;
    @Autowired
    private MySessionExpiredStrategy sessionExpiredStrategy;
    @Autowired
    private MySessionInvalidStrategy sessionInvalidStrategy;
    @Autowired
    private MyAccessDeniedHandler  accessDeniedHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // userDetailsService 自定義街口
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
         * 使用Spring Security 預設登入頁面 formLogin
         * 登入成功轉址到 index.html
         * API 拜訪權限在此設定
         * Server 的 Session 也可在此做控制
         * 因為搭配SPA，只有登入成功會由server端進行轉址，後續轉址工作必須交由前端操作
         * */
        http
                .logout()
                .logoutUrl("/api/logout") // 設定登出api的路徑
                .permitAll();
        http
                .formLogin() // 啟用Spring Security預設登入頁面
                .defaultSuccessUrl("/#/") // 登入後導向路徑
                .and()
                .authorizeRequests() // 設置 request 拜訪權限及登入驗證
                .antMatchers(HttpMethod.GET,"/hasRootAuthority").hasAuthority("ROLE_ROOT") // 要ROLE_前墜
                .antMatchers(HttpMethod.GET,"/hasRootAndUSERAuthority").hasAnyAuthority("ROLE_ROOT,ROLE_USER")
                .antMatchers(HttpMethod.GET,"/hasRootRole").hasAnyRole("ROOT") // 不用ROLE_前墜
                .antMatchers(HttpMethod.GET,"/hasAnyRole").hasAnyRole("ROOT,USER")
                .antMatchers("/USER/**").hasAnyAuthority("ROLE_USER")
                .antMatchers("/ROOT/**").hasAnyAuthority("ROLE_ROOT")
                .anyRequest().authenticated() // 沒被設置的路徑.禁止訪問
                .and().exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler) // 拜訪權限不足處理
                .and().csrf().disable();
        http
                .sessionManagement()
                .maximumSessions(1) // 某帳號同時最高上線數
                .maxSessionsPreventsLogin(false) // 某帳號已達最高上線數後，true->禁止下一個登入，false->踢掉前面的使用者
                .expiredSessionStrategy(sessionExpiredStrategy) // Session 無效處裡
                .and()
                .invalidSessionStrategy(sessionInvalidStrategy); // Session 超時處裡

    }


}

