package cn.example.project.config.sec;

import cn.example.project.module.base.Message;
import cn.example.project.module.rbac.Resource;
import cn.example.project.module.rbac.ResourceLevel;
import cn.hutool.log.StaticLog;
import com.alibaba.fastjson.JSON;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PermissionUtil {
    /**
     *  判断请求是否属于权限列表中的一员
     * @param request
     * @param authors
     * @return
     */
    public static RestfulGrantedAuthority matchFromAuthors(HttpServletRequest request,List<RestfulGrantedAuthority> authors){
        String url, method;
        AntPathRequestMatcher matcher;
        for (RestfulGrantedAuthority rga : authors) {
                url = rga.getPermissionUrl();
                method = rga.getMethod();
                matcher = new AntPathRequestMatcher(url);
                //url  method 当权限表权限的method为ALL时表示拥有此路径的所有请求方式权利。
                StaticLog.info("url=" + url + ";method=" + method +":   " + request.getRequestURL() + request.getMethod() );
                if (matcher.matches(request)&& (request.getMethod().equals(method) || "ALL".equals(method))) {
                    return rga;
                }
        }
        return null;
    }

    /**
     * 通过资源列表生成权限列表
     * @param resources
     * @return
     */
    public static List<RestfulGrantedAuthority>  toPermissionList(List<Resource> resources){
        List<RestfulGrantedAuthority> list = new ArrayList<>();
        for (Resource resource : resources) {
            list.add(new RestfulGrantedAuthority(resource.getUrl(),resource.getMethod()));
        }
        return list;
    }

    /**
     * 手动为response添加允许跨域请求的响应头；也就是浏览器端可以接收本次请求结果，处理。
     * @param resp
     */
    public static void corsConfig(HttpServletResponse resp){
        resp.setHeader("Access-Control-Allow-Origin","*");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH, DELETE, PUT");
        resp.setHeader("Access-Control-Max-Age", "3600");
        resp.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    }

    /**
     * 通用的响应方法
     * @param resp
     * @param message
     * @throws IOException
     */
    public static void handle(HttpServletResponse resp, Message message) throws IOException {
//        corsConfig(resp);
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
//        Message message = new Message("error", "权限不足，请联系管理员!");
        out.write(JSON.toJSONString(message));
//        out.flush();
//        out.close();
    }

    /**
     * 通用的响应方法
     * @param resp
     * @param message
     * @throws IOException
     */
    public static void handle(HttpServletRequest req,HttpServletResponse resp, Message message) throws IOException {
        corsConfig(req,resp);
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
//        Message message = new Message("error", "权限不足，请联系管理员!");
        out.write(JSON.toJSONString(message));
//        out.flush();
//        out.close();
    }

    private static void corsConfig(HttpServletRequest req, HttpServletResponse resp) {
//        resp.setHeader("Access-Control-Allow-Origin","*");
        resp.setHeader("Access-Control-Allow-Origin",req.getHeader("origin"));
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH, DELETE, PUT,OPTIONS");
        resp.setHeader("Access-Control-Max-Age", "3600"); // 1小时
//        resp.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Content-Length, Authorization, Accept, X-Requested-With , yourHeaderFeild");
    }


    public static SecurityUser getCurrentUser() {
        return (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

