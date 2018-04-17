package com.dmsd.controller;

import com.dmsd.api.LoginService;
import com.dmsd.pojo.SystemResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public SystemResult login(String username, String password, HttpServletRequest request, HttpServletResponse response){

//        this.checkLoginInfo(username, password);
//        request.getSession().setAttribute("isLogin", true);
//        String token = System.currentTimeMillis()+"";
//        return "success";

        SystemResult result = loginService.login(username, password, request, response);

        return result;
    }


    @RequestMapping("/token/{token}")
    @ResponseBody
    public Object getUserByToken(@PathVariable String token, String callback){

            SystemResult result = loginService.getUserByToken(token);
            //支持jsonp调用
            if (null != callback){

            }
            return result;
    }
}
