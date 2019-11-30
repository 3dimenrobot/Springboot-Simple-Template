package cn.example.project.config.sec;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * 参考 https://www.cnblogs.com/wuyoucao/p/10863419.html
 */
public class SecurityUser implements UserDetails {

    private String username;

    private String password;

    /***
     * 正常情况下，角色和权限是两回事，
     * 所以我们还需要重写getAuthorities方法，将用户的角色和权限关联起来
     */
    private List<GrantedAuthority> authorities;

    public SecurityUser(String username, String password, List<GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override //获取校验用密码
    public String getPassword() {
        return this.password;
    }

    @Override //获取校验用户名
    public String getUsername() {
        return this.username;
    }

    @Override  //账户是否未过期
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override //账户是否未锁定
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override //帐户密码是否未过期，一般有的密码要求性高的系统会使用到，比较每隔一段时间就要求用户重置密码
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override //账户是否可用
    public boolean isEnabled() {
        return true;
    }
}