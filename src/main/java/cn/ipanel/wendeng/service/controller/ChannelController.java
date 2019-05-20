package cn.ipanel.wendeng.service.controller;

import cn.ipanel.wendeng.service.controller.resp.Result;
import cn.ipanel.wendeng.service.entity.Channel;
import cn.ipanel.wendeng.service.global.Globals;
import cn.ipanel.wendeng.service.service.IChannelService;
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
 * @create: 2019-05-16 10:52
 **/
@RestController
@RequestMapping(value = "/wendeng/videolistapi")
public class ChannelController {

    private IChannelService videoListUrlService;

    @Autowired
    public void VideoListUrlController(IChannelService videoListUrlService){
        this.videoListUrlService = videoListUrlService;
    }

    @ApiOperation(value = "查询视频源链接", tags = Globals.API_BACK)
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Result<List<Channel>> queryVideoListApiUrl(){
        return new Result<>(videoListUrlService.findAll());
    }
}
