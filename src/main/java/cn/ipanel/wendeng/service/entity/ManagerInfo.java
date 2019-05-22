package cn.ipanel.wendeng.service.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @program: wendeng
 * @description:
 * @author: hezy
 * @create: 2019-05-22 15:41
 **/
@Data
@Entity
@Table
@DynamicUpdate
@DynamicInsert
public class ManagerInfo implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 16)
    private String username;

    @Column(length = 32)
    private String name;

    @Column(length = 32)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(foreignKeyDefinition = "none", value = ConstraintMode.NO_CONSTRAINT))
    @NotFound(action = NotFoundAction.IGNORE)
    private List<RoleInfo> roles;

    public ManagerInfo(String username, String name, String password, List<RoleInfo> roles) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.roles = roles;
    }

    public ManagerInfo() {
    }
}
