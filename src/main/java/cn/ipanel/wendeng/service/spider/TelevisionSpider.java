package cn.ipanel.wendeng.service.spider;

import cn.ipanel.wendeng.service.service.IChannelService;
import cn.ipanel.wendeng.service.spider.processor.TelevisionProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import us.codecraft.webmagic.Spider;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: wendeng
 * @description:爬取学习强国学习电视台栏目数据https://www.xuexi.cn/0809b8b6ab8a81a4f55ce9cbefa16eff/ae60b027cb83715fd0eeb7bb2527e88b.html
 * @author: hezy
 * @create: 2019-05-20 11:41
 **/
public class TelevisionSpider {

    private IChannelService videoListUrlService;

    @Autowired
    public void TelevisionSpider(IChannelService videoListUrlService){
        this.videoListUrlService = videoListUrlService;
    }

    public void doUpdate(){

    }

    private void doTelevisionUpdate(){

        final List<String> pageUrl = new ArrayList<>();
        //获取各个栏目的数据链接
        videoListUrlService.findAll().forEach(info->pageUrl.add(info.getApiUrl()));

        Spider spider = Spider.create(new TelevisionProcessor()).addUrl((String[]) pageUrl.toArray());

    }



}
