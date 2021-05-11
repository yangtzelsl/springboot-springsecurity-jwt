package com.yangtzelsl.config;

import com.yangtzelsl.filter.JwtAuthorizationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 自定义用户认证逻辑
 * 1.处理用户信息获取逻辑 UserDetailsService
 * 2.处理用户校验逻辑 UserDetails
 * 3.处理密码加密解密逻辑 PasswordEncoder
 * <p>
 * 自定义用户验证流程
 * 1.自定义登录页面 /login.html
 * 2.自定义登录成功处理 MyAuthenticationSuccessHandler
 * 3.自定义登录失败处理 MyAuthenctiationFailureHandler
 */
@Configuration
@EnableWebSecurity// 这个注解必须加，开启Security
@EnableGlobalMethodSecurity(prePostEnabled = true)//保证post之前的注解可以使用
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthorizationTokenFilter jwtAuthorizationTokenFilter;

    /**
     * http请求的安全处理
     *
     * @param httpSecurity
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // 关闭 跨站请求伪造
        httpSecurity.csrf().disable();
        // 开始请求权限配置
        httpSecurity.authorizeRequests()
                // 下面配置的这些资源放行
                .antMatchers("/",
                        "/ztree/**",
                        "/statics/**",
                        "/templates/**"
                )
                .permitAll()
                // 任何请求都要通过权限验证
                .anyRequest()
                .authenticated()
        ;

        // TODO 动态查询数据库给访问的请求赋权限（即动态绑定数据库赋予所有权限）

        //解决X-Frame-Options DENY问题
        httpSecurity.headers().frameOptions().sameOrigin();
        httpSecurity
                // 默认是走的formLogin(), 即表单模式。也可以选择走HTTP协议, 即httpBasic()
                .formLogin()
                // 指定默认的拦截URL处理地址
                .loginProcessingUrl("/login1")

                .and()
                .logout()
                .permitAll()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")

        ;

        System.out.println("configure方法被调用了...");

    }

    /**
     * 使用的BCrypt加密/解密方式，类似的还有MD5等
     *
     * @return
     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    /**
     * 身份验证，添加授权用户
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // TODO 生产环境操作是查询数据库，动态获取用户名和密码


        // 减省操作(仅做测试使用)
        // auth.inMemoryAuthentication().withUser("admin").password("123456").authorities("/");
        // auth.inMemoryAuthentication().withUser("da").password("123456").authorities("/");
        //inMemoryAuthentication 从内存中获取
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("user1").password(new BCryptPasswordEncoder().encode("123456")).roles("USER");

        System.out.println("configure2 调用");
    }

}
