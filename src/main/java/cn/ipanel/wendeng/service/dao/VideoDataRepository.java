package cn.ipanel.wendeng.service.dao;

import cn.ipanel.wendeng.service.entity.VideoData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @program: wendeng
 * @description:
 * @author: hezy
 * @create: 2019-05-15 17:33
 **/
@Repository
public interface VideoDataRepository extends CrudRepository<VideoData,Integer>{

    Page<VideoData> findAll(Pageable pageable);

}
