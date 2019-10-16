package demo;

import org.junit.Test;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class MySecurity {
    @Test
    public void test1(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;
        if (principal instanceof UserDetails) {
             username = ((UserDetails)principal).getUsername();
        } else {
             username = principal.toString();
        }
        System.out.println(username);
    }
}
