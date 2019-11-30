package cn.example.project.config.sec;


import cn.hutool.log.StaticLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import javax.servlet.*;
import java.io.IOException;

@Service
public class DynamicallyUrlInterceptor extends AbstractSecurityInterceptor implements Filter {

    @Autowired
    private FilterInvocationSecurityMetadataSource securityMetadataSource;

    @Autowired
    private MyAccessDecisionManager accessDecisionManager;

    // 显示覆盖设置一下，注入到Intercepter
    @Autowired
    public void setMyAccessDecisionManager(MyAccessDecisionManager myAccessDecisionManager) {
        super.setAccessDecisionManager(myAccessDecisionManager);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(servletRequest, servletResponse, filterChain);
        invoke(fi);
    }

    /**
     * //fi里面有一个被拦截的url
     * 里面调用MyInvocationSecurityMetadataSource的getAttributes(Object object)这个方法获取fi对应的所有权限
     * 再调用MyAccessDecisionManager的decide方法来校验用户的权限是否足够
     *
     * @param filterInvocation
     * @throws IOException
     * @throws ServletException
     */
    public void invoke(FilterInvocation filterInvocation) throws IOException, ServletException {

        InterceptorStatusToken token = super.beforeInvocation(filterInvocation);
        try {
            StaticLog.info("trycatchInvoke:>>>>>>>>>>>>>>>>>>>>");
            //执行下一个拦截器
            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }


    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }
}
