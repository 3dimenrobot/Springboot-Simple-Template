//package cn.example.project.config.security;
//
//import cn.example.project.module.rbac.User;
//import cn.example.project.module.rbac.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
////https://www.dineshonjava.com/spring-security-tutorial-using-spring-boot/
//@Service
//public class CurrentUserDetailsService implements UserDetailsService {
//    private final UserService userService;
//
//    @Autowired
//    public CurrentUserDetailsService(UserService userService) {
//        this.userService = userService;
//    }
//
////    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
////        User user = userService.getUserByUsername(username);
////        return new UserDetails(user);
////    }
//}