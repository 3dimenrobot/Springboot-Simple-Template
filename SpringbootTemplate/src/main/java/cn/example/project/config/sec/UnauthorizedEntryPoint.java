package cn.example.project.config.sec;

import cn.example.project.module.base.Message;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * AuthenticationEntryPoint 用来解决匿名用户访问无权限资源时的异常
 */
@Component
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e)
            throws IOException, ServletException {
        PermissionUtil.handle(httpServletResponse,new Message("error","权限不足" ,e.getMessage()));
    }

//   response.sendError(
//    HttpServletResponse.SC_UNAUTHORIZED,
//            "Unauthorized: Authentication token was either missing or invalid.");
//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//        if(isAjaxRequest(request)){
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,authException.getMessage());
//        }else{
//            response.sendRedirect("/login");
//        }
//    }
//
//    public static boolean isAjaxRequest(HttpServletRequest request) {
//        String ajaxFlag = request.getHeader("X-Requested-With");
//        return ajaxFlag != null && "XMLHttpRequest".equals(ajaxFlag);
//    }
}