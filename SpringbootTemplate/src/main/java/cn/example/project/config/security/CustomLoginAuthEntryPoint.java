package cn.example.project.config.security;

import cn.example.project.module.base.Message;
import com.alibaba.fastjson.JSON;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 授权认证失败后调用
 */
public class CustomLoginAuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException e) throws IOException, ServletException {
            Message message = new Message("error","没有权限");
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(message));
    }
}