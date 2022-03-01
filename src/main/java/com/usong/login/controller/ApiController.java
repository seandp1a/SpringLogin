package com.usong.login.controller;

import com.usong.login.pojo.UserInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    @GetMapping("/hasRootAuthority")
    public String hasRootAuthority(){
        return "ROOT access only!";
    }

    @GetMapping("/hasRootAndUserAuthority")
    public String hasRootAndUserAuthority(){
        return "ROOT & USER access only!";
    }

    @GetMapping("/hasRootRole")
    public String hasRootRole(){
        return "ROOT access only!";
    }

    @GetMapping("/hasAnyRole")
    public String hasAnyRole(){
        return "ROOT & USER access only!";
    }

    @GetMapping("/getAuthenticatedUserInfo")
    public UserInfo getAuthenticatedUserInfo(){
        UserInfo result = new UserInfo();
        // Security會從sessionID取得登入資訊如角色、權限等
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        result.setName(authentication.getName());
        result.setAccess(authentication.getAuthorities().iterator().next().toString());
        return result;
    }

}
