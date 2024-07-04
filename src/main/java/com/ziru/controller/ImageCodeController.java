package com.ziru.controller;


import com.ziru.common.VerifyCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@Slf4j
public class ImageCodeController {

    String code="";

    /**
     * 生成验证码
     * @param response
     * @param session
     * @throws IOException
     */
    @RequestMapping("/createVerify")
    public void createVerify(HttpServletResponse response, HttpSession session) throws IOException {
        VerifyCode vc = new VerifyCode();
        vc.drawImage(response.getOutputStream());
        //设置浏览器不缓存
        response.setHeader("Pragma","no-cache");
        response.setHeader("Cache-Control","no-cache");
        code = vc.getCode();
        log.info("生成的系统的验证码是:"+code);
    }

    /**
     * 验证验证码是否正确
     */
    @RequestMapping("/checkVerify")
    @ResponseBody
    public boolean checkVerify(@RequestParam("verify") String verify){
        //verify 是前端传递过来的，str是自动生成的， 比较
        if(verify.equalsIgnoreCase(code)){
            return  true;
        }
        return  false;
    }
}
