package cn.ipanel.wendeng.service.service;

import cn.ipanel.wendeng.service.entity.VideoListApiUrl;

import java.util.List;

/**
 * @program: wendeng
 * @description:
 * @author: hezy
 * @create: 2019-05-16 10:44
 **/
public interface IVideoListUrlService {

    public List<VideoListApiUrl> findAll();
}
