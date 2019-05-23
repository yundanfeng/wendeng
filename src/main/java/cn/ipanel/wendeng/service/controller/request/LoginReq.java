package cn.ipanel.wendeng.service.controller.request;

import cn.ipanel.wendeng.service.annotation.MyInterfaceRequestParams;
import lombok.Data;

/**
 * @program: wendeng
 * @description:
 * @author: hezy
 * @create: 2019-05-23 15:18
 **/
@MyInterfaceRequestParams(operationName = "登录")
@Data
public class LoginReq {
    private String username;
    private String password;
}
