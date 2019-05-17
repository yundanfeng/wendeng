package cn.ipanel.wendeng.spider;

import cn.ipanel.wendeng.service.spider.req.Item;
import cn.ipanel.wendeng.service.spider.req.VideoModel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;

/**
 * @program: wendeng
 * @description:
 * @author: hezy
 * @create: 2019-05-17 12:14
 **/
@Slf4j
@Component
public class HtmlTask {

    private BlockingQueue<Item> itemQueue;

    @Autowired
    public void HtmlTask(BlockingQueue<Item> itemQueue){
        this.itemQueue = itemQueue;
    }

    @Async("myAsync")
    public void getItem(Item item) throws Exception{
        log.info("formateURl:{}",item.getUrl());
        itemQueue.put(item);
        String subUrl = item.getUrl().substring(0,item.getUrl().lastIndexOf("/"));
        String lastUrl = item.getUrl().substring(item.getUrl().lastIndexOf("/")+1, item.getUrl().lastIndexOf("."));
        //log.info("subUrl:{},lastUrl:{},jsUrl:{}/data{}.js",subUrl,lastUrl,subUrl,lastUrl);
        String videoUrl = new StringBuffer(subUrl).append("/data").append(lastUrl).append(".js").toString();
        String videoData;
        videoData = HttpClient.doGet(videoUrl);
        String videoJSON = videoData.substring(videoData.indexOf("{"), videoData.lastIndexOf("}")+1);
        //log.info("videoJson:{}", videoJSON);
        //实测"$..list"提示语法错误,"$.fpjalz6o4gsso001.list"没问题
        String json = JSON.toJSONString(JSONPath.read(videoJSON,"$..list[0]"));
        VideoModel videoModel = JSON.parseObject(json,VideoModel.class);
        log.info("Video:{}", videoModel.get_id());
    }

    @Async("myAsync")
    public void getVideo() throws Exception{
        Item item;
        while(true){
            item = itemQueue.take();
            log.info("item:{}",item.getUrl());
        }
    }
}
