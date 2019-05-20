package cn.ipanel.wendeng.service.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @program: wendeng
 * @description:
 * @author: hezy
 * @create: 2019-05-15 17:25
 **/
@Entity
@Data
public class VideoData implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String dataId;

    @Column
    private String itemId;

    @Column
    private String thumbImage;

    @Column
    private String title;

    @Column
    private String channelId;

    @Column
    private String htmlUrl;

    @Column
    private String apiUrl;

    @Column
    private String ossUrl;

    @Column
    private String content;

    @Column
    private String source;

    @Column
    private String editor;

    @Column
    private Integer isSync;

    @Column
    private String publishTime;

    @Column String updateTime;
}
