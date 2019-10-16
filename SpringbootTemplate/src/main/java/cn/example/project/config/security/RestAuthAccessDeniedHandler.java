package cn.example.project.config.security;

import cn.example.project.module.base.Message;
import com.alibaba.fastjson.JSON;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理没有权限的类
 * @author zhoukebo
 * @date 2018/9/5
 */
@Component
public class RestAuthAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException e) throws IOException, ServletException {
        Message message = new Message("error","没有权限");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(message));
    }
}