package cn.ipanel.wendeng.service.spider;

import cn.ipanel.wendeng.service.service.IChannelService;
import cn.ipanel.wendeng.service.spider.processor.TelevisionProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: wendeng
 * @description:爬取学习强国学习电视台栏目数据https://www.xuexi.cn/0809b8b6ab8a81a4f55ce9cbefa16eff/ae60b027cb83715fd0eeb7bb2527e88b.html
 * @author: hezy
 * @create: 2019-05-20 11:41
 **/
@Component
public class TelevisionSpider {

    private IChannelService videoListUrlService;
    private TelevisionProcessor televisionProcessor;

    @Autowired
    public void TelevisionSpider(IChannelService videoListUrlService,TelevisionProcessor televisionProcessor){
        this.videoListUrlService = videoListUrlService;
        this.televisionProcessor = televisionProcessor;
    }

    public void doUpdate(){
        doTelevisionUpdate();
    }

    private void doTelevisionUpdate(){

        final List<String> pageUrl = new ArrayList<>();
        //获取各个栏目的数据链接
        videoListUrlService.findAll().forEach(info->pageUrl.add(info.getApiUrl()));

        Spider.create(televisionProcessor).addUrl(pageUrl.toArray(new String[pageUrl.size()])).thread(5).run();

    }



}
