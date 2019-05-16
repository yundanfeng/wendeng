package cn.ipanel.wendeng.service.service.impl;

import cn.ipanel.wendeng.service.dao.VideoListApiUrlRepository;
import cn.ipanel.wendeng.service.entity.VideoListApiUrl;
import cn.ipanel.wendeng.service.service.IVideoListUrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: wendeng
 * @description:
 * @author: hezy
 * @create: 2019-05-16 10:46
 **/
@Slf4j
@Service
public class VideoListServiceImpl implements IVideoListUrlService{

    private VideoListApiUrlRepository videoListApiUrlRepository;

    @Autowired
    public void VideoListServiceImpl(VideoListApiUrlRepository videoListApiUrlRepository){
        this.videoListApiUrlRepository = videoListApiUrlRepository;
    }

    @Override
    public List<VideoListApiUrl> findAll() {
        return (List<VideoListApiUrl>) videoListApiUrlRepository.findAll();
    }
}
