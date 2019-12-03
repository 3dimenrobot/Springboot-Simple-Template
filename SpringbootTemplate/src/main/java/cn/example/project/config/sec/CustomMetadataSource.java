package cn.example.project.config.sec;

import cn.example.project.module.rbac.Resource;
import cn.example.project.module.rbac.ResourceDB;
import cn.example.project.module.rbac.ResourceRepo;
import cn.example.project.module.rbac.ResourceService;
import cn.hutool.log.StaticLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 *  //此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。如果不在权限表中则放行。
 *     //因为我不想每一次来了请求，都先要匹配一下权限表中的信息是不是包含此url，
 *     // 我准备直接拦截，不管请求的url 是什么都直接拦截，然后在MyAccessDecisionManager的decide 方法中做拦截还是放行的决策。
 *     //所以此方法的返回值不能返回 null 此处我就随便返回一下。
 */
@Component
public class CustomMetadataSource  implements
        FilterInvocationSecurityMetadataSource {
    @Autowired
    ResourceRepo repo;

    List<RestfulGrantedAuthority> allAuthors;

    /**
     * 加载权限表中所有权限;判断匹配的资源是否在列表中，如果存在，则返回对应的资源，不存在，则返回login;
     * 实际上相当于没有做任何限制
     * @param o
     * @return
     * @throws IllegalArgumentException
     */
    // 此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。如果不在权限表中则放行。
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        HttpServletRequest request = ((FilterInvocation) o).getRequest();

        // 如果是login_p，不需要任何权限；不会进入MyAccessDecisionManager
//        if ("/login_p".equals(request.getRequestURI())) {
//            return null;
//        }
        List<Resource> resList = ResourceDB.findAllResource();// TODO repo.findAll();
        StaticLog.info("CustomMetadataSource:getAttributes:>>>>>>>>>>>>>>>>>>>>");
        allAuthors = PermissionUtil.toPermissionList(resList);
        RestfulGrantedAuthority author = PermissionUtil.matchFromAuthors(request, allAuthors);

        if(author != null){
            return  SecurityConfig.createList(author.getAuthority());
        }
        // 数据库表中没有匹配上的资源，未找到资源
        return SecurityConfig.createList(RestfulGrantedAuthority.URL_NOT_FOUND);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
