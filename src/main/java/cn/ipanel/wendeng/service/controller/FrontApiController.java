package cn.ipanel.wendeng.service.controller;

import cn.ipanel.wendeng.service.controller.resp.ListResp;
import cn.ipanel.wendeng.service.entity.VideoData;
import cn.ipanel.wendeng.service.service.IVideoDateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    @RequestMapping(value = "news",method = RequestMethod.GET)
    public String queryNews(@RequestParam Integer page, @RequestParam Integer size, ModelMap map){
        Page videoData = videoDateService.findAll(PageRequest.of(page,size));
        Integer pages = videoData.getTotalPages();
        long number = videoData.getTotalElements();
        List<VideoData> list = videoData.getContent();
        log.info("videoPages:{},videoNumber:{}",pages,number);
        map.put("videoData",new ListResp(pages,number,list));
        return "news";
    }
}
