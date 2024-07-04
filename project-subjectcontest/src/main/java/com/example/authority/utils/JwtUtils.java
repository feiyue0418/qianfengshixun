package com.example.authority.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.authority.entity.User;
import com.example.authority.enums.ResponseEnum;
import com.example.authority.exception.AuthException;
import com.example.authority.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtUtils {

    @Autowired
    private UserService userService;

    private static UserService staticUserService;

    @PostConstruct
    public void setStaticUserService(){
        staticUserService = this.userService;
    }

    public static String generateToken(String userId,String sign){
        return JWT.create().withAudience(userId)
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600 * 24 * 1000))
                .sign(Algorithm.HMAC256(sign));
    }

    public static User getCurrentUser(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("token");
        String userId;
        if(StringUtils.isNotBlank(token)){
            try{
                userId = JWT.decode(token).getAudience().get(0);
                User user = staticUserService.getById(userId);
                if(null != user){
                    return user;
                }else{
                    throw new AuthException(ResponseEnum.NOAUTH.getCode(), ResponseEnum.NOAUTH.getMsg());
                }
            }catch(JWTDecodeException e){
                throw new AuthException(ResponseEnum.NOAUTH.getCode(), ResponseEnum.NOAUTH.getMsg());
            }
        }else{
            throw new AuthException(ResponseEnum.NOAUTH.getCode(), ResponseEnum.NOAUTH.getMsg());
        }
    }
}
