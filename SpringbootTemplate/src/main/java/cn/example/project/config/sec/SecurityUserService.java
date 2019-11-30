package cn.example.project.config.sec;

import cn.example.project.module.rbac.*;
import cn.hutool.log.LogFactory;
import cn.hutool.log.StaticLog;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SecurityUserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    /**
     * admin/123456 的加密值
     * //        User user = new User();
     * //        user.setUsername("admin");
     * //        user.setUsername("$2a$10$glDmERY6TuLaoFQwQLBKxO4aZ4/ZF4mkka9w.eyMoumhK4QR6GLQm");
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }

        List<Role> roles = user.getRoles();
        //获取用户权限
        List<GrantedAuthority> authorities = new ArrayList<>();

        //将所有的角色对应的资源权限全部放入user对应的grantedAuthority集合中
//        for (Role role : roles) {
//            List<Resource> resources = role.getResources();
//
//            for (Resource resource : resources) {
//                GrantedAuthority grantedAuthority = new RestfulGrantedAuthority(resource.getUrl(),resource.getMethod());
//                authorities.add(grantedAuthority);
//            }
//        }

        List<Resource> resources = ResourceDB.find_RBAC_Resources();
        for (Resource resource : resources) {
            GrantedAuthority grantedAuthority = new RestfulGrantedAuthority(resource.getUrl(),resource.getMethod());
            authorities.add(grantedAuthority);
        }

        StaticLog.info("\r\n\r\n\n");
        StaticLog.info("Username:"+user.getUsername() + ";Password: " + user.getPassword()
                + ";authorities + " + JSON.toJSONString(authorities) );

        return new SecurityUser(user.getUsername(), user.getPassword(), authorities);
    }
}
