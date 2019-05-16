package cn.ipanel.wendeng.service.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @program: wendeng
 * @description:
 * @author: hezy
 * @create: 2019-05-16 10:06
 **/
@Entity
@Data
public class VideoListApiUrl implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String apiUrl;

    @Column
    private String channelId;

    @Column
    private String channelName;
}
