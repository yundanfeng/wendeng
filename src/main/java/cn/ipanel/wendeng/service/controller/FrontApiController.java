package cn.ipanel.wendeng.service.controller;

import cn.ipanel.wendeng.service.entity.VideoData;
import cn.ipanel.wendeng.service.service.IVideoDateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    IVideoDateService videoDateService;

    @ApiIgnore
    @RequestMapping(value = "news/{id}",method = RequestMethod.GET)
    public String queryNews(@PathVariable(value = "id") Integer id, ModelMap map){
        List<VideoData> videoDataList = videoDateService.findAll(PageRequest.of(id,10)).getContent();
        map.put("list",videoDataList);
        return "news";
    }
}
