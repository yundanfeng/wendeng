package cn.ipanel.wendeng.service.dao;

import cn.ipanel.wendeng.service.entity.Channel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @program: wendeng
 * @description:
 * @author: hezy
 * @create: 2019-05-16 10:10
 **/
@Repository
public interface ChannelRepository extends CrudRepository<Channel,Integer>{
}
