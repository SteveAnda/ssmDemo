package com.dmsd.controller;


import com.dmsd.api.UserService;
import com.dmsd.pojo.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/user/{id}")

    public void findUserById(@PathVariable int id, HttpServletResponse response) throws IOException {
         response.getWriter().write(userService.findUserById(id).toString());
    }
}
