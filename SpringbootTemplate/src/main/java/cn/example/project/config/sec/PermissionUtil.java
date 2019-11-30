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
     * 通用的响应方法
     * @param resp
     * @param message
     * @throws IOException
     */
    public static void handle(HttpServletResponse resp, Message message) throws IOException {
        resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
//        Message message = new Message("error", "权限不足，请联系管理员!");
        out.write(JSON.toJSONString(message));
        out.flush();
        out.close();
    }

    public static SecurityUser getCurrentUser() {
        return (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static List<Resource> createWhiteList(String[] whiteList) {
        List<Resource> list= new ArrayList<>();
        int count = 1;
        for (String s : whiteList) {
            Resource rw00 = new Resource(10000+count,"", ResourceLevel.Page_Resource, s , s ,null,"GET","read");
            list.add(rw00);
            count ++;
        }
        return list;
    }
}

