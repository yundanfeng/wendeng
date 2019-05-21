package cn.ipanel.wendeng.service.spider;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @program: wendeng
 * @description:
 * @author: hezy
 * @create: 2019-05-21 15:17
 **/
@Component
@Data
@PropertySource("classpath:application.yml")
public class SpiderConfig {

    /**
     * 文件保存地址
     */
    @Value("${spider.file-path}")
    private String filePath;

    @Value("${spider.update_cron}")
    private String updateCron;

    /**
     * 每个分类爬取的视频数量
     */
    @Value("${spider.video-num}")
    private String videoNum;
}
