package cn.ipanel.wendeng.service.spider.processor;

import cn.ipanel.wendeng.service.entity.VideoData;
import cn.ipanel.wendeng.service.service.IVideoDateService;
import cn.ipanel.wendeng.service.spider.req.Item;
import cn.ipanel.wendeng.service.spider.req.VideoModel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: wendeng
 * @description:
 * @author: hezy
 * @create: 2019-05-20 13:41
 **/
@Slf4j
@Component
public class TelevisionProcessor implements PageProcessor{

    public static Map<String,VideoData> videoDataMap = new ConcurrentHashMap<>();

    private Site site = Site.me().setTimeOut(20000).setRetryTimes(5).setSleepTime(15000).setCharset("UTF-8");

    private IVideoDateService videoDateService;


    @Autowired
    public void TelevisionProcessor(IVideoDateService videoDateService){
        this.videoDateService = videoDateService;
    }

    @Override
    public Site getSite(){
        return site;
    }

    @Override
    public void process(Page page){
        String url = page.getRequest().getUrl();
        //列表页
        if(url.contains("lgdata")){
            Json json = page.getJson();
            List<Item> videoDataList = json.toList(Item.class);
            //查出已经爬取过的详情页url，做去重
            List<String> apiUrlList = new ArrayList<>();
            videoDateService.findAll().forEach(item->apiUrlList.add(item.getApiUrl()));
            //添加详情页链接
            List<String> videos = new ArrayList<>();
            videoDataList.forEach(item->{
                log.info("item:{}",item.getItemId());
                //构造详情页数据链接
                String formatURL = item.getUrl();
                String subUrl = formatURL.substring(0,formatURL.lastIndexOf("/"));
                String lastUrl = formatURL.substring(formatURL.lastIndexOf("/")+1, formatURL.lastIndexOf("."));
                String videoUrl = new StringBuffer(subUrl).append("/data").append(lastUrl).append(".js").toString();
                //除去已经爬取过的详情页
                if(!apiUrlList.contains(videoUrl)){
                    videos.add(videoUrl);
                }
                //列表页取数据
                VideoData videoData = new VideoData();
                videoData.setTitle(item.getTitle());
                videoData.setItemId(item.getItemId());
                videoData.setChannelId(item.getChannelIds().get(0));
                videoData.setThumbImage(item.getThumbImage());
                videoData.setHtmlUrl(item.getUrl());
                videoData.setPublishTime(item.getPublishTime());
                TelevisionProcessor.videoDataMap.put(videoUrl,videoData);
            });
            //详情页链接添加
            page.addTargetRequests(videos);
        }else{//详情页
            String videoData = page.getRawText();
            String videoJSON = videoData.substring(videoData.indexOf("{"), videoData.lastIndexOf("}")+1);
            String json = JSON.toJSONString(JSONPath.read(videoJSON,"$..list[0]"));
            VideoModel videoModel = JSON.parseObject(json,VideoModel.class);
            if(null != videoModel){
                log.info("video:{},name:{},editor:{},id:{}",videoModel.getFirst_name(),videoModel.getName(),videoModel.getEditor(),videoModel.get_id());
            }
            //列表页数据和详情页数据
            VideoData video = TelevisionProcessor.videoDataMap.get(page.getRequest().getUrl());
            //保存数据
            if(null != video){
                video.setContent(videoModel.getContent());
                video.setOssUrl(videoModel.getOssUrl());
                video.setApiUrl(page.getRequest().getUrl());
                video.setDataId(videoModel.get_id());
                video.setSource(videoModel.getSource());
                video.setEditor(videoModel.getEditor());
                video.setIsSync(0);
                videoDateService.addVideoData(video);
            }
        }
    }

}
