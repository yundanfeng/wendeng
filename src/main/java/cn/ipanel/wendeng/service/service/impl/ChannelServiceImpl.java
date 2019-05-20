package cn.ipanel.wendeng.service.service.impl;

import cn.ipanel.wendeng.service.dao.ChannelRepository;
import cn.ipanel.wendeng.service.entity.Channel;
import cn.ipanel.wendeng.service.service.IChannelService;
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
public class ChannelServiceImpl implements IChannelService {

    private ChannelRepository channelRepository;

    @Autowired
    public void VideoListServiceImpl(ChannelRepository channelRepository){
        this.channelRepository = channelRepository;
    }

    @Override
    public List<Channel> findAll() {
        return (List<Channel>) channelRepository.findAll();
    }
}
