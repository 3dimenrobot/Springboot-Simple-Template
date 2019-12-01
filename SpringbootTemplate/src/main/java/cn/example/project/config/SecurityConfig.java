package cn.example.project.config;

import cn.example.project.config.sec.*;
import cn.example.project.module.base.Message;
import cn.example.project.module.rbac.Resource;
import cn.example.project.module.rbac.ResourceDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    SecurityUserService securityUserService;
//
    @Autowired
    DynamicallyUrlInterceptor interceptor;

    @Autowired
    UnauthorizedEntryPoint unauthorizedEntryPoint;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityUserService).passwordEncoder(new BCryptPasswordEncoder());
    }

    /**
     * https://blog.csdn.net/sinat_33151213/article/details/89931819
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/index.html", "/static/**", "/login_p", "/favicon.ico");
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                .antMatchers("/index.html", "/static/**","/static/index.html", "/login_p", "/favicon.ico").permitAll()
                //"/login"不进行权限验证
                .anyRequest().authenticated()   //其他的需要登陆后才能访问
             .and()
                .formLogin()
                //loginProcessingUrl用于指定前后端分离的时候调用后台登录接口的名称
                .loginProcessingUrl("/user/login")
                .usernameParameter("username").passwordParameter("password")
                .successHandler(new AuthenticationSuccessHandler() { //配置登录成功的自定义处理类
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest req,
                                                        HttpServletResponse resp,
                                                        Authentication auth) throws IOException {
                        Message message = new Message("success", "登录成功!", PermissionUtil.getCurrentUser());
                        //提供给前端 设置cookie
                        message.setToken(UUID.randomUUID().toString());

                        PermissionUtil.handle(req,resp, message);
                    }
                }).failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest req,
                                                        HttpServletResponse resp,
                                                        AuthenticationException e) throws IOException {
                        Message message = null;
                        if (e instanceof BadCredentialsException ||
                                e instanceof UsernameNotFoundException) {
                            message = new Message("error", "账户名或者密码输入错误!", e.getMessage());
                        } else {
                            message = new Message("error", "登录失败!", e.getMessage());
                        }
                        PermissionUtil.handle(req,resp, message);
                    }
                })
                .and()
                    .logout().logoutUrl("/user/logout")//logoutUrl用于指定前后端分离的时候调用后台注销接口的名称
                        .logoutSuccessHandler(new LogoutSuccessHandler() {
                            @Override
                            public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {

                                Message message = new Message("success", "注销成功!", null);
                                PermissionUtil.handle(req,resp, message);
                            }
                    })
                .and().exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint)// 配置没有权限的自定义处理类
                .and()
                 .cors().disable().csrf().disable(); // 取消跨站请求伪造防护

        http.addFilterBefore(interceptor, FilterSecurityInterceptor.class);
    }
}