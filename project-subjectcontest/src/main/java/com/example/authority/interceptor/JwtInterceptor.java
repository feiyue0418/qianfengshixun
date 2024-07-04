package com.example.authority.interceptor;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.authority.annotation.CommonAuth;
import com.example.authority.annotation.NoAuth;
import com.example.authority.entity.Menu;
import com.example.authority.entity.User;
import com.example.authority.enums.ResponseEnum;
import com.example.authority.exception.AuthException;
import com.example.authority.mapper.MenuMapper;
import com.example.authority.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Autowired
    private MenuMapper menuMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        if(requestURI.equals("/error")){
            return false;
        }
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        NoAuth noAuth = ((HandlerMethod) handler).getMethodAnnotation(NoAuth.class);
        if(null != noAuth){
            return true;
        }
        String token = request.getHeader("token");
        if(StringUtils.isBlank(token)){
            throw new AuthException(ResponseEnum.NOAUTH.getCode(), ResponseEnum.NOAUTH.getMsg());
        }
        String userId;
        try{
             userId = JWT.decode(token).getAudience().get(0);
        }catch(JWTDecodeException e){
            throw new AuthException(ResponseEnum.NOAUTH.getCode(), ResponseEnum.NOAUTH.getMsg());
        }
        User user = userService.getById(userId);
        if(null == user){
            throw new AuthException(ResponseEnum.NOAUTH.getCode(), ResponseEnum.NOAUTH.getMsg());
        }
        String userJson = stringRedisTemplate.opsForValue().get(token);
        User redisUser = JSON.parseObject(userJson, User.class);
        if(null != redisUser){
            if(!redisUser.getId().toString().equals(userId)){
                throw new AuthException(ResponseEnum.NOAUTH.getCode(), ResponseEnum.NOAUTH.getMsg());
            }
            //判断访问的请求是不是公用的接口，登录了就可以访问
            CommonAuth commonAuth = ((HandlerMethod) handler).getMethodAnnotation(CommonAuth.class);
            if(commonAuth != null){
                return true;
            }
            //后台验证权限
            requestURI = request.getRequestURI();
            String[] split = requestURI.split("/");
            String menuPath = "/" + split[1];
            List<Menu> menuList = menuMapper.selectByIdList(redisUser.getRoleId());
            List<String> menuPathList = menuList.stream().map(menu -> menu.getPath()).collect(Collectors.toList());
            if(menuPathList.contains(menuPath)){
                return true;
            }else{
                throw new AuthException(ResponseEnum.NOAUTHORITY.getCode(), ResponseEnum.NOAUTHORITY.getMsg());
            }
        }else{
            throw new AuthException(ResponseEnum.NOAUTH.getCode(), ResponseEnum.NOAUTH.getMsg());
        }
    }
}
