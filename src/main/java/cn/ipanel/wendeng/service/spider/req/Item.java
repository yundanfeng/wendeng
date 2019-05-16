package cn.ipanel.wendeng.service.spider.req;

import lombok.Data;

import java.util.List;

/**
 * @program: wendeng
 * @description:
 * @author: hezy
 * @create: 2019-05-16 14:56
 **/
@Data
public class Item {

    private String itemId;

    private String publishTime;

    private String thumbImage;

    private String auditTime;

    private List<String> channelNames;

    private  String producer;

    private String source;

    private String title;

    private String type;

    private List<String> channelIds;

    private String url;
}
