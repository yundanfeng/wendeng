package cn.ipanel.wendeng.service.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @program: wendeng
 * @description:
 * @author: hezy
 * @create: 2019-05-22 15:39
 **/
@Entity
@Data
@Table
@DynamicInsert
@DynamicUpdate
public class RoleInfo implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String url;

    public RoleInfo(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public RoleInfo() {

    }
}
