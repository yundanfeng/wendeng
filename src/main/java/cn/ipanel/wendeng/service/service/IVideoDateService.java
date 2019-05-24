package cn.ipanel.wendeng.service.service;

import cn.ipanel.wendeng.service.entity.VideoData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @program: wendeng
 * @description:
 * @author: hezy
 * @create: 2019-05-15 17:38
 **/
public interface IVideoDateService {

    List<VideoData> findAll();

    Page<VideoData> findAll(Pageable pageable);

    void addVideoData(VideoData videoData);
}
