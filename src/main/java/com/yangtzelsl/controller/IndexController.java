package com.yangtzelsl.controller;

import com.yangtzelsl.model.SysUser;
import com.yangtzelsl.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Description: IndexController
 * @Author luis.liu
 * @Date: 2021/5/11 11:00
 * @Version 1.0
 */
@Controller
public class IndexController {

    @Autowired
    @Qualifier("jwtUserDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @ResponseBody
    @RequestMapping("/index")
    public String index() {
        return "hello";
    }

    @ResponseBody
    @RequestMapping("/addUser")
    public String addUser() {
        return "add user success";
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> map, HttpServletRequest request) {

        String username = map.get("username");
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        String token = jwtTokenUtil.generateToken(userDetails);

        return token;
    }

    @PostMapping("/generateToken")
    public String generateToken(@RequestBody SysUser sysUser, HttpServletRequest request) {
        return null;
    }
}
