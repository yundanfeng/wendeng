package cn.ipanel.wendeng.service.controller.resp;

import cn.ipanel.wendeng.service.entity.ManagerInfo;
import cn.ipanel.wendeng.service.entity.RoleInfo;
import lombok.Data;

import static java.util.stream.Collectors.joining;

/**
 *
 */
@Data
public class ManagerVO {
    private Integer id;
    private String username;
    private String name;
    private String roles;

    public ManagerVO(ManagerInfo managerInfo) {
        this.id = managerInfo.getId();
        this.username = managerInfo.getUsername();
        this.name = managerInfo.getName();
        this.roles = managerInfo.getRoles().stream().map(RoleInfo::getName).collect(joining("、"));

    }
}
