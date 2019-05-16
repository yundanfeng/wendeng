package cn.ipanel.wendeng.spider;

import cn.ipanel.wendeng.service.entity.VideoListApiUrl;
import cn.ipanel.wendeng.service.service.IVideoListUrlService;
import cn.ipanel.wendeng.service.spider.req.Item;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @program: wendeng
 * @description:
 * @author: hezy
 * @create: 2019-05-16 11:23
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpiderTest {

    private VideoListApiUrl videoListApiUrl;
    private IVideoListUrlService videoListUrlService;

    @Autowired
    public void SpiderTest(IVideoListUrlService videoListUrlService){
        this.videoListUrlService = videoListUrlService;
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
        String formatUrl = itemList.get(0).getUrl();
        log.info("formatUrl:{}", formatUrl);
        String subUrl = formatUrl.substring(0,formatUrl.lastIndexOf("/"));
        String lastUrl = formatUrl.substring(formatUrl.lastIndexOf("/")+1, formatUrl.lastIndexOf("."));
        log.info("subUrl:{},lastUrl:{},jsUrl:{}/data{}.js",subUrl,lastUrl,subUrl,lastUrl);
        String videoUrl = new StringBuffer(subUrl).append("/data").append(lastUrl).append(".js").toString();
        String videoData = HttpClient.doGet(videoUrl);
        String videoJSON = videoData.substring(videoData.indexOf("{"), videoData.lastIndexOf("}")+1);
        log.info("videoJson:{}", videoJSON);
        String json = JSON.toJSONString(JSONPath.read(videoJSON,"$..list"));
        log.info("json:{}", json);
    }
}
