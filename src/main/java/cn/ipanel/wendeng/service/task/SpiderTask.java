package cn.ipanel.wendeng.service.task;

import cn.ipanel.wendeng.service.spider.SpiderConfig;
import cn.ipanel.wendeng.service.spider.TelevisionSpider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @program: wendeng
 * @description:
 * @author: hezy
 * @create: 2019-05-20 11:13
 **/
@Slf4j
@Component
@PropertySource("classpath:application.yml")
public class SpiderTask {


    private BlockingQueue<String> fileQueue;
    private SpiderConfig spiderConfig;
    private TelevisionSpider televisionSpider;

    private static AtomicBoolean isDoingUpdate = new AtomicBoolean(false);


    @Autowired
    public void SpiderTask(BlockingQueue<String> fileQueue,SpiderConfig spiderConfig,
                           TelevisionSpider televisionSpider){
        this.fileQueue = fileQueue;
        this.spiderConfig = spiderConfig;
        this.televisionSpider = televisionSpider;
    }

    @Scheduled(cron = "${spider.update_cron}")
    public void spider(){
        log.info("进入更新方法之中---------------------》");
        //程序首次启动会执行，防止定时任务同时执行
        if(!isDoingUpdate.get()){
            log.info("开始执行更新操作-------------》");
            isDoingUpdate.set(true);
            long start = System.currentTimeMillis();
            televisionSpider.doUpdate();
            long end = System.currentTimeMillis();
            long use = (end - start)/1000;
            log.info("完成更新，用时："+use + "秒");
            isDoingUpdate.set(false);
        }
    }

    /**
     * 监听文件下载队列，下载文件
     */
    @Async("myAsync")
    public void listemFileQueue() {
        while (true) {
            String url = "";
            try {
                url = fileQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                String filePath = spiderConfig.getFilePath();
                if(!url.isEmpty()){
                    if (url.contains("mp4")){
                        filePath += "/video/";
                        log.info("get fileUrl:{} from fileQueue",url);
                        String name = url.substring(url.lastIndexOf("/")+1);
                        loadFile(url,filePath,name);
                    }else {
                        filePath += "/img/";
                    }
                }
            } catch (Exception e) {
                log.info("listen alarmQueue error: {}", e.getMessage());
            }
        }
    }

    /**
     * 下载保存文件
     * @param url
     * @param path
     * @param name
     * @throws Exception
     */
    public void loadFile(String url,String path, String name) throws Exception{
        String filePath = path;

        File file=new File(filePath);
        //判断文件夹是否存在
        if (!file.exists())
        {
            //如果文件夹不存在，则创建新的的文件夹
            file.mkdirs();
        }
        URL httpUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.setDoOutput(true);

        conn.connect();
        InputStream inputStream = conn.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        FileOutputStream fileOut = new FileOutputStream(filePath+name);
        BufferedOutputStream bos = new BufferedOutputStream(fileOut);
        byte[] buf = new byte[4096];
        int length = bis.read(buf);
        while (length != -1){
            bos.write(buf,0,length);
            length = bis.read(buf);
        }
        bos.close();
        bis.close();
        conn.disconnect();
        return;
    }
}
