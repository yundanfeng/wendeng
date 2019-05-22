package cn.ipanel.wendeng.service.dao;

import cn.ipanel.wendeng.service.entity.RoleInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @program: wendeng
 * @description:
 * @author: hezy
 * @create: 2019-05-16 10:10
 **/
@Repository
public interface RoleRepository extends CrudRepository<RoleInfo, Integer> {

    List<RoleInfo> findByIdIn(List<Integer> ids);

    List<RoleInfo> findByNameIn(List<String> names);

    Optional<RoleInfo> findByName(String roleName);
}
