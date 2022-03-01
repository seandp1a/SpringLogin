package com.usong.login.service;

import com.usong.login.dao.UserInfoDao;
import com.usong.login.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    UserInfoDao userInfoDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo dbUserInfo = userInfoDao.getUserByName(username);
        if(dbUserInfo == null){
            throw new UsernameNotFoundException("此帳號不存在");
        }
        List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_"+dbUserInfo.getAccess());
        return new User(dbUserInfo.getName(),dbUserInfo.getPassword(),auths);
    }
}
