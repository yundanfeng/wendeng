package cn.ipanel.wendeng.service.service;

import cn.ipanel.wendeng.service.controller.resp.ManagerVO;

/**
 * @program: wendeng
 * @description:
 * @author: hezy
 * @create: 2019-05-22 17:37
 **/
public interface IManagerService {

    ManagerVO queryManager(String username);

    ManagerVO login(String username, String password) throws Exception;
}
