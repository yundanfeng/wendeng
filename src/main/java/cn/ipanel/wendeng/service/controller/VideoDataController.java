package cn.ipanel.wendeng.service.controller;

import cn.ipanel.wendeng.service.controller.resp.Result;
import cn.ipanel.wendeng.service.entity.VideoData;
import cn.ipanel.wendeng.service.global.Globals;
import cn.ipanel.wendeng.service.service.IVideoDateService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: wendeng
 * @description:
 * @author: hezy
 * @create: 2019-05-15 17:41
 **/
@RestController
@RequestMapping(value = "/wendeng/video")
public class VideoDataController {

    private IVideoDateService videoDateService;

    @Autowired
    public void VideoDataController(IVideoDateService videoDateService){
        this.videoDateService = videoDateService;
    }

    @ApiOperation(value = "查询视频", tags = Globals.API_BACK)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Result<List<VideoData>> queryVideos(){
        return new Result<>(videoDateService.findAll());
    }
}
