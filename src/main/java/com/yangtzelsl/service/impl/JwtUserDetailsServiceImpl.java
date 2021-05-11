package com.yangtzelsl.service.impl;

import com.yangtzelsl.model.SecurityUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: JwtUserDetailsService
 * @Author luis.liu
 * @Date: 2021/5/10 15:33
 * @Version 1.0
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {

        // 1.根据该用户名查询在数据库中是否存在
        // 2.存在则查询对应的用户权限
        // 3.将该权限添加到security
        System.out.println("JwtUserDetailsService:" + user);
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("USER"));
        return new SecurityUserDetails(user, authorityList);
    }

}
