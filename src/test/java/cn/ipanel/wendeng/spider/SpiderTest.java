package cn.ipanel.wendeng.spider;

import cn.ipanel.wendeng.service.entity.VideoListApiUrl;
import cn.ipanel.wendeng.service.service.IVideoListUrlService;
import cn.ipanel.wendeng.service.spider.req.Item;
import cn.ipanel.wendeng.service.spider.req.VideoModel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * @program: wendeng
 * @description:
 * @author: hezy
 * @create: 2019-05-16 11:23
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAsync
public class SpiderTest {

    private VideoListApiUrl videoListApiUrl;
    private IVideoListUrlService videoListUrlService;
    private HtmlTask htmlTask;
    private BlockingQueue<Item> itemQueue;

    @Autowired
    public void SpiderTest(IVideoListUrlService videoListUrlService,HtmlTask htmlTask, BlockingQueue<Item> itemQueue){
        this.videoListUrlService = videoListUrlService;
        this.htmlTask = htmlTask;
        this.itemQueue = itemQueue;
    }

    @Test
    public void spider() throws Exception{
        videoListApiUrl = videoListUrlService.findAll().get(0);
        log.info("test {}", JSON.toJSON(videoListApiUrl));
        String url = videoListApiUrl.getApiUrl();
        String data = HttpClient.doGet(url);
        List<Item> itemList= JSON.parseArray(data,Item.class);
        log.info("data:{}", itemList.size());
        //https://www.xuexi.cn/12d757297d44705e3228f79b938b530d/cf94877c29e1c685574e0226618fb1be.html
        //https://www.xuexi.cn/12d757297d44705e3228f79b938b530d/datacf94877c29e1c685574e0226618fb1be.js
        long start = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        for(int i=0;i<itemList.size();i++){
            String formatUrl = itemList.get(i).getUrl();
            log.info("formatUrl:{}", formatUrl);
            log.info("name:{}",itemList.get(i).getTitle());
            String subUrl = formatUrl.substring(0,formatUrl.lastIndexOf("/"));
            String lastUrl = formatUrl.substring(formatUrl.lastIndexOf("/")+1, formatUrl.lastIndexOf("."));
            //log.info("subUrl:{},lastUrl:{},jsUrl:{}/data{}.js",subUrl,lastUrl,subUrl,lastUrl);
            String videoUrl = new StringBuffer(subUrl).append("/data").append(lastUrl).append(".js").toString();
            String videoData = HttpClient.doGet(videoUrl);
            String videoJSON = videoData.substring(videoData.indexOf("{"), videoData.lastIndexOf("}")+1);
           // log.info("videoJson:{}", videoJSON);
            //实测"$..list"提示语法错误,"$.fpjalz6o4gsso001.list"没问题
            String json = JSON.toJSONString(JSONPath.read(videoJSON,"$..list[0]"));
           // log.info("json:{}", json);
            VideoModel videoModel = JSON.parseObject(json,VideoModel.class);
            //log.info("Video:{}",JSON.toJSONString(videoModel));
            if(null != videoModel)
            log.info("video:{}",videoModel.get_id());
        }
        long end = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        log.info("duration:{}",end-start);
    }

    @Test
    public void thredsTask() throws Exception{
        videoListApiUrl = videoListUrlService.findAll().get(0);
        log.info("test {}", JSON.toJSON(videoListApiUrl));
        String url = videoListApiUrl.getApiUrl();
        String data = HttpClient.doGet(url);
        List<Item> itemList= JSON.parseArray(data,Item.class);
        log.info("data:{}", itemList.size());
        long start = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        for(int i=0;i<itemList.size();i++){
            htmlTask.getItem(itemList.get(i));
        }
        long end = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    @Test
    public void threadsTask2() throws Exception{
        htmlTask.getVideo();
        videoListApiUrl = videoListUrlService.findAll().get(0);
        log.info("test {}", JSON.toJSON(videoListApiUrl));
        String url = videoListApiUrl.getApiUrl();
        String data = HttpClient.doGet(url);
        List<Item> itemList= JSON.parseArray(data,Item.class);
        log.info("data:{}", itemList.size());
        for(int i=0;i<itemList.size();i++){
            itemQueue.put(itemList.get(i));
        }
    }
}
