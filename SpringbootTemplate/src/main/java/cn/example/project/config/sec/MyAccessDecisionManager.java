package cn.example.project.config.sec;

import cn.hutool.log.StaticLog;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 *   //authentication 是释CustomUserService中循环添加到 GrantedAuthority 对象中的权限信息集合.
 *   //object 包含客户端发起的请求的requset信息，可转换为 HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
 *   //configAttributes 为MyInvocationSecurityMetadataSource的getAttributes(Object object)这个方法返回的结果，此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。如果不在权限表中则放行。
 */
@Service
public class  MyAccessDecisionManager implements AccessDecisionManager {
    // decide 方法是判定是否拥有权限的决策方法，
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        // 1. 获取匹配的Authority字符串
        ConfigAttribute firstEle = collection.iterator().next();

        StaticLog.info("CustomMetadataSource:getAttributes:>>>>>>>>>>>>>>>>>>>>");

        // 2.1 如果在表中未找到资源 // 直接放过 //404
        if(firstEle.getAttribute().equals(RestfulGrantedAuthority.URL_NOT_FOUND)){
//            throw new AccessDeniedException("资源不存在"); //
            StaticLog.info(">>>>>>>>>>>RestfulGrantedAuthority.URL_NOT_FOUND");
            return;
        }

        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new AccessDeniedException("未登录，没有授权");
        }

        // 2.1 是白名单// 放过
//        if(firstEle.getAttribute().equals(RestfulGrantedAuthority.WHITE_RESOURCE)){
//            return;
//        }


        // 表中存在；但是需要判断权限集合中是否存在
        // 如果资源在权限表中，需要判断是否包含在当前角色的权限表
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        for (GrantedAuthority ga : authentication.getAuthorities()) {
            RestfulGrantedAuthority rga = (RestfulGrantedAuthority) ga;
            String url = rga.getPermissionUrl();
            String method = rga.getMethod();
            AntPathRequestMatcher matcher = new AntPathRequestMatcher(url);
            //url  method 当权限表权限的method为ALL时表示拥有此路径的所有请求方式权利。
            StaticLog.info("matches:>>" + matcher.matches(request) + "   :method:" + (request.getMethod().equals(method) || "ALL".equals(method)));
            if (matcher.matches(request)&& (request.getMethod().equals(method) || "ALL".equals(method))) {
                return;
            }
        }

        throw new AccessDeniedException("权限不足!");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
