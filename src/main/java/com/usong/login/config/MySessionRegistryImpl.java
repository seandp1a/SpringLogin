package com.usong.login.config;

import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;


public class MySessionRegistryImpl extends SessionRegistryImpl {
    @Override
    public List<SessionInformation> getAllSessions(Object principal, boolean includeExpiredSessions) {
        String Username =((UserDetails) principal).getUsername();
        System.out.println("1111111-"+Username);
        return super.getAllSessions(principal, includeExpiredSessions);
    }
}
