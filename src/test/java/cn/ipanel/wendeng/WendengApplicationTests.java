package cn.ipanel.wendeng;

import cn.ipanel.wendeng.service.dao.VideoDataRepository;
import cn.ipanel.wendeng.service.entity.Channel;
import cn.ipanel.wendeng.service.service.IChannelService;
import cn.ipanel.wendeng.service.spider.SpiderConfig;
import cn.ipanel.wendeng.service.spider.processor.TelevisionProcessor;
import cn.ipanel.wendeng.service.spider.req.Item;
import cn.ipanel.wendeng.service.spider.req.VideoModel;
import cn.ipanel.wendeng.service.task.SpiderTask;
import cn.ipanel.wendeng.spider.HttpClient;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringRunner;
import us.codecraft.webmagic.Spider;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAsync
@Slf4j
public class WendengApplicationTests {

    private Channel channel;
    private IChannelService channelService;
    private TelevisionProcessor televisionProcessor;
    private SpiderTask spiderTask;
    private SpiderConfig spiderConfig;
    private VideoDataRepository videoDataRepository;
    @Autowired
    public void SpiderTest(IChannelService channelService,TelevisionProcessor televisionProcessor,
                           SpiderTask spiderTask, SpiderConfig spiderConfig,VideoDataRepository videoDataRepository){
        this.channelService = channelService;
        this.televisionProcessor = televisionProcessor;
        this.spiderTask = spiderTask;
        this.spiderConfig = spiderConfig;
        this.videoDataRepository = videoDataRepository;
    }

	@Test
	public void contextLoads() {
	}

	@Test
	public void spider() throws Exception{
		channel = channelService.findAll().get(0);
		log.info("test {}", JSON.toJSON(channel));
		String url = channel.getApiUrl();
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
		channel = channelService.findAll().get(0);
		log.info("test {}", JSON.toJSON(channel));
		String url = channel.getApiUrl();
		String data = HttpClient.doGet(url);
		List<Item> itemList= JSON.parseArray(data,Item.class);
		log.info("data:{}", itemList.size());
		long start = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
		for(int i=0;i<itemList.size();i++){
			//htmlTask.getItem(itemList.get(i));
		}
		long end = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
	}

	@Test
	public void webMagic(){

		final List<String> pageUrl = new ArrayList<>();
		//获取各个栏目的数据链接
		channelService.findAll().forEach(info->pageUrl.add(info.getApiUrl()));
		Spider.create(televisionProcessor).addUrl(pageUrl.toArray(new String[pageUrl.size()])).thread(5).run();
	}

	@Test
	public void fileTest(){
		//查出所有已下载文件
		String path = spiderConfig.getFilePath();
		File file = new File(path);
		File[] files = file.listFiles();
		log.info("fileSize:{}",files.length);
		Map<String,String> downloadedFiles = spiderTask.traversalFiles(file);
		log.info("files:{}",downloadedFiles.size());
		downloadedFiles.keySet().forEach(System.out::println);
	}

	@Test
	public void testVideo(){
    	Page page = videoDataRepository.findAll(PageRequest.of(0,10));
    	log.info("pages:{},number:{}",page.getTotalPages(),page.getTotalElements());
/*    	List<VideoData> list = page.getContent();
    	list.forEach(info->System.out.println(info.getId()+" "+info.getTitle()));*/
	}

}
