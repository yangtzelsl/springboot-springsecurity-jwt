package com.yangtzelsl.model;

import com.yangtzelsl.model.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;

/**
 * @Description: SecurityUserDetails
 * @Author luis.liu
 * @Date: 2021/5/10 15:34
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SecurityUserDetails extends SysUser implements UserDetails {

    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public SecurityUserDetails(String userName, Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
        this.setUsername(userName);
        // TODO 实际生产操作要从DB查询
        String encode = new BCryptPasswordEncoder().encode("123456");
        this.setPassword(encode);
        // TODO 实际生产操作要从DB查询
        this.setAuthorities(authorities);
    }

    /**
     * 账户是否过期
     *
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 是否禁用
     *
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 密码是否过期
     *
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否启用
     *
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
