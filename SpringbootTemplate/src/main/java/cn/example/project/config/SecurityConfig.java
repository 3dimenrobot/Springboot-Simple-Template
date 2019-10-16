package cn.example.project.config;

import cn.example.project.config.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    SecurityUserService securityUserService;
    @Autowired
    MyPasswordEncoder passwordEncoder;
    @Autowired
    RestAuthAccessDeniedHandler restAuthAccessDeniedHandler;
    // 覆盖认证方法
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityUserService).passwordEncoder(passwordEncoder);
    }


    //allow Swagger URL to be accessed without authentication
//        web.ignoring().antMatchers("/v2/api-docs",//swagger api json
//                "/swagger-resources/configuration/ui",//用来获取支持的动作
//                "/swagger-resources",//用来获取api-docs的URI
//                "/swagger-resources/configuration/security",//安全选项
//                "/swagger-ui.html");

//  http.
//          // ... here goes your custom security configuration
//          authorizeRequests().
//    antMatchers(AUTH_WHITELIST).permitAll().  // whitelist Swagger UI resources
//    // ... here goes your custom security configuration
//    antMatchers("/**").authenticated();  // require authentication for any endpoint that's not whitelisted

//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        // TODO Auto-generated method stub
//        // 开放swagger 访问
//        http.authorizeRequests()
//                .antMatchers("/v2/api-docs", "/swagger-resources/configuration/ui", "/swagger-resources",
//                        "/swagger-resources/configuration/security", "/swagger-ui.html", "/webjars/**","/rbac/**").permitAll()
////                .antMatchers("/**").authenticated() // 让所有url都可以访问，用于测试
////                .and()
////                .authorizeRequests()
////                .anyRequest()
////                .authenticated()
//                .and()
//                .csrf().disable();
//    }
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors().and()//新加入
                .csrf() //跨站
                .disable() //关闭跨站检测
                .authorizeRequests()//验证策略策略链
                .antMatchers("/public/**").permitAll()//无需验证路径
                .antMatchers("/login").permitAll()// 放行登录
                .antMatchers(HttpMethod.GET, "/rbac/user/*").hasAuthority("query")//拥有权限才可访问
                .antMatchers(HttpMethod.GET, "/rbac/menu/**").hasAnyAuthority("query","queryAll")// 拥有任一权限即可访问
                //角色类似，hasRole(),hasAnyRole()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                //未登录跳转页面,设置了authenticationentrypoint后无需设置未登录跳转页面;AuthenticationEntryPoint 用来解决匿名用户访问无权限资源时的异常
//                .loginPage("/public/unlogin")
                .loginProcessingUrl("/login") //处理表单中自定义的登录URL;用于指定前后端分离的时候调用后台登录接口的名称
                //登录成功转发接口
                .successForwardUrl("/account/loginSuccess")
                .failureForwardUrl("/account/loginFailed")
                //修改用户名的表单name,有需要的话可以改为id，默认为username
                .usernameParameter("username")
                .passwordParameter("password")//修改密码的表单name，默认为password
                .and()
                //配置没有权限的自定义处理类; AccessDeineHandler 用来解决认证过的用户访问无权限资源时的异常
                .exceptionHandling().authenticationEntryPoint(new CustomLoginAuthEntryPoint()).accessDeniedHandler(restAuthAccessDeniedHandler)
                .and()
                .logout()// 自定义登出
                 // 于指定前后端分离的时候调用后台注销接口的名称
                .logoutUrl("/logout") // 自定义登出api，无需自己实现
                .logoutSuccessUrl("account/logoutSuccess");
    }




}