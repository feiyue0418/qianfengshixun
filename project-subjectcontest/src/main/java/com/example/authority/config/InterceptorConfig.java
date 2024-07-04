package com.example.authority.config;

import com.example.authority.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/**").excludePathPatterns(
                "/user/login",
                "/user/register",
                "/notice/findAll",
                "/swagger-resources/**"
                ,"/webjars/**"
                ,"/v2/**"
                ,"/swagger-ui.html/**"
        );
    }

    /**
     * 映射路径修改:这段代码意思就配置一个拦截器， 如果访问路径是addResourceHandler中的filepath 这个路径
     * 那么就 映射到访问本地的addResourceLocations 的参数的这个路径上，
     * 这样就可以让别人访问服务器的本地文件了，比如本地图片或者本地音乐视频什么的。
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/sysFile/show/**").addResourceLocations("file:D:\\temp\\files\\");
    }
}
