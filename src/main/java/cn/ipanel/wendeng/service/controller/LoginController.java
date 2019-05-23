package cn.ipanel.wendeng.service.controller;

import cn.ipanel.wendeng.service.controller.request.LoginReq;
import cn.ipanel.wendeng.service.controller.resp.Result;
import cn.ipanel.wendeng.service.service.IManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @program: wendeng
 * @description:
 * @author: hezy
 * @create: 2019-05-22 16:16
 **/
@Controller
@Slf4j
@RequestMapping(value = "auth")
public class LoginController {


    private IManagerService managerService;

    @Autowired
    public void LoginController(IManagerService managerService){
        this.managerService = managerService;
    }

    @ApiIgnore
    @ResponseBody
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public Result<Boolean> login(@RequestBody LoginReq req){
        try {
            managerService.login(req.getUsername(), req.getPassword());
            return new Result<>(true);
        } catch (Exception e) {
            log.error("{}", e.getMessage());
            return new Result<>(false, e.getMessage());
        }
    }

}
