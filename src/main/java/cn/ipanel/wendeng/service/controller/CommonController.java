package cn.ipanel.wendeng.service.controller;

import cn.ipanel.wendeng.service.controller.resp.ManagerVO;
import cn.ipanel.wendeng.service.controller.resp.Result;
import cn.ipanel.wendeng.service.global.Globals;
import cn.ipanel.wendeng.service.service.IManagerService;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @program: wendeng
 * @description:
 * @author: hezy
 * @create: 2019-05-22 17:15
 **/
@Controller
public class CommonController {

    @Resource
    IManagerService managerService;

    @RequestMapping(value = "")
    public String index(ModelMap map) {
        return indexHtml(map, "");
    }
    @RequestMapping(value = "login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "loginSuccess")
    @ResponseBody
    public Result<Boolean> loginSuccess() {
        return new Result<>(true);
    }

    @RequestMapping(value = "index")
    public String indexHtml(ModelMap map, @RequestParam(value = "info", required = false) String info) {
        if (!Globals.isEmpty(info)) {
            System.out.println(info);
        }
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        ManagerVO vo = managerService.queryManager(username);
        if (null == vo) {
            return "login";
        }
        map.addAttribute("name", vo.getName());
        map.addAttribute("roles", vo.getRoles());
        map.addAttribute("time",  Globals.timeQuantum());
        return "index";
    }
}
