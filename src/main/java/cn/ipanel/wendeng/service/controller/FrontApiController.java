package cn.ipanel.wendeng.service.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @program: wendeng
 * @description:
 * @author: hezy
 * @create: 2019-05-23 17:05
 **/
@Slf4j
@Controller
@RequestMapping(value = "front")
public class FrontApiController {


    @ApiIgnore
    @RequestMapping(value = "news",method = RequestMethod.GET)
    public String queryNews(ModelMap map){
        return "news";
    }
}
