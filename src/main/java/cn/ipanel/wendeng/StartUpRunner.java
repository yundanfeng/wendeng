package cn.ipanel.wendeng;

import cn.ipanel.wendeng.service.spider.SpiderConfig;
import cn.ipanel.wendeng.service.task.SpiderTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @program: wendeng
 * @description:
 * @author: hezy
 * @create: 2019-05-21 12:00
 **/
@Component
public class StartUpRunner implements CommandLineRunner{

    private SpiderTask spiderTask;
    private SpiderConfig spiderConfig;

    @Autowired
    public void StartUpRunner(SpiderTask spiderTask,SpiderConfig spiderConfig){
        this.spiderTask = spiderTask;
        this.spiderConfig = spiderConfig;
    }

    @Override
    public void run(String... args) throws Exception{
        if(!SpiderConfig.FILE_DOWNLOAD_FALSE.equals(spiderConfig.getFileDownload())){
            spiderTask.listemFileQueue();
        }
        //spiderTask.spider();
    }
}
