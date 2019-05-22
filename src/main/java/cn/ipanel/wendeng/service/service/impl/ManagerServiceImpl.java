package cn.ipanel.wendeng.service.service.impl;

import cn.ipanel.wendeng.service.controller.resp.ManagerVO;
import cn.ipanel.wendeng.service.dao.ManagerRepository;
import cn.ipanel.wendeng.service.entity.ManagerInfo;
import cn.ipanel.wendeng.service.service.IManagerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: wendeng
 * @description:
 * @author: hezy
 * @create: 2019-05-22 17:37
 **/
@Service
public class ManagerServiceImpl implements IManagerService{

    @Resource
    private ManagerRepository managerRepository;

    @Override
    public ManagerVO queryManager(String username) {
        ManagerInfo managerInfo = managerRepository.findByUsername(username);
        if (null == managerInfo) {
            return null;
        } else {
            return new ManagerVO(managerInfo);
        }
    }
}
