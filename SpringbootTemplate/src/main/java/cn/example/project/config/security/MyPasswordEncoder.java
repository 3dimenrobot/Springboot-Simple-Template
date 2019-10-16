package cn.example.project.config.security;

import cn.hutool.log.LogFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyPasswordEncoder implements PasswordEncoder {

    @Override // 加密
    public String encode(CharSequence rawPassword) {
        // 虽然每次生成的编码不一致，但是 BCrypt.checkpw("123456","$2a$10$glDmERY6TuLaoFQwQLBKxO4aZ4/ZF4mkka9w.eyMoumhK4QR6GLQm") 依然为true
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(rawPassword.toString().trim());
    }

    //判断密码是否匹配
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return BCrypt.checkpw(rawPassword.toString().trim(),encodedPassword);
    }

    public static void main(String[] args) {
        String s1 = new MyPasswordEncoder().encode("123456");
        String s2 = new MyPasswordEncoder().encode("123456");
        LogFactory.get().info(s1);
        LogFactory.get().info(s2);
        LogFactory.get().info(BCrypt.checkpw("123456",s2) + "");
        LogFactory.get().info(BCrypt.checkpw("123456","$2a$10$glDmERY6TuLaoFQwQLBKxO4aZ4/ZF4mkka9w.eyMoumhK4QR6GLQm") + "");

    }
}
