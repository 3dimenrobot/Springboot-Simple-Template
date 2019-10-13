package cn.example.project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //allow Swagger URL to be accessed without authentication
//        web.ignoring().antMatchers("/v2/api-docs",//swagger api json
//                "/swagger-resources/configuration/ui",//用来获取支持的动作
//                "/swagger-resources",//用来获取api-docs的URI
//                "/swagger-resources/configuration/security",//安全选项
//                "/swagger-ui.html");

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // TODO Auto-generated method stub
        // 开放swagger 访问
        http.authorizeRequests()
                .antMatchers("/v2/api-docs", "/swagger-resources/configuration/ui", "/swagger-resources", "/swagger-resources/configuration/security", "/swagger-ui.html", "/webjars/**").permitAll()
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable();
    }




}