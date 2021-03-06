package cn.ipanel.wendeng.service.service.impl;

import cn.ipanel.wendeng.service.dao.VideoDataRepository;
import cn.ipanel.wendeng.service.entity.VideoData;
import cn.ipanel.wendeng.service.service.IVideoDateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: wendeng
 * @description:
 * @author: hezy
 * @create: 2019-05-15 17:40
 **/
@Slf4j
@Service
public class VideoDataServiceImol implements IVideoDateService{
    private VideoDataRepository videoDataRepository;

    @Autowired
    public void VideoDataServiceImol(VideoDataRepository videoDataRepository){
        this.videoDataRepository = videoDataRepository;
    }

    @Override
    public List<VideoData> findAll(){
        return (List<VideoData>)videoDataRepository.findAll();
    }

    @Override
    public Page<VideoData> findAll(Pageable pageable){
        return videoDataRepository.findAll(pageable);
    }

    @Override
    public void addVideoData(VideoData videoData){
        videoDataRepository.save(videoData);
    }

}
