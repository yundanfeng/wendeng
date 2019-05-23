package cn.ipanel.wendeng.service.service.impl;

import cn.ipanel.wendeng.service.controller.resp.ManagerVO;
import cn.ipanel.wendeng.service.dao.ManagerRepository;
import cn.ipanel.wendeng.service.entity.ManagerInfo;
import cn.ipanel.wendeng.service.global.Globals;
import cn.ipanel.wendeng.service.global.RequestParamErrorException;
import cn.ipanel.wendeng.service.service.IManagerService;
import cn.ipanel.wendeng.service.util.MD5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
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

    @Override
    public ManagerVO login(String username, String password) throws Exception{
        if (Globals.isEmpty(username)) {
            throw new RequestParamErrorException("用户名不能为空");
        }
        if (Globals.isEmpty(password)) {
            throw new RequestParamErrorException("密码不能为空");
        }
        UsernamePasswordToken token = new UsernamePasswordToken(username, MD5Util.EncryptionStr32(password));
        ManagerInfo managerInfo = managerRepository.findByUsername(token.getUsername());
        if (null == managerInfo) {
            throw new RequestParamErrorException("用户名或密码有误");
        }
        if (!new String(token.getPassword()).equals(managerInfo.getPassword())) {
            throw new RequestParamErrorException("用户名或密码有误");
        }
        SecurityUtils.getSubject().login(token);
        return new ManagerVO(managerInfo);
    }
}
