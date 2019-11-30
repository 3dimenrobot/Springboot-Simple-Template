package cn.example.project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * 配置允许跨域  localhost:9900/aa 登录 localhost:9900/bb 也认可登录
 */
@Configuration
public class WebMvcConfg  implements  WebMvcConfigurer {
    /**
     * 配置静态资源
     * https://blog.csdn.net/sinat_33151213/article/details/89931819
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/public/**").addResourceLocations("classpath:/public/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("*");

    }
}