package cn.ipanel.wendeng.service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: wendeng
 * @description:
 * @author: hezy
 * @create: 2019-05-22 16:16
 **/
@Controller
@RequestMapping(value = "/wendeng/")
public class LoginController {

    @RequestMapping(value = "/login")
    public String login(){
        return "login";
    }

}
