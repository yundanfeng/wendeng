package cn.ipanel.wendeng.service.controller.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @program: wendeng
 * @description:
 * @author: hezy
 * @create: 2019-05-27 12:03
 **/
@Data
public class ListResp<T> implements Serializable{

    private Integer page;
    private Long number;
    private List<T> list;

    public ListResp() {
        super();
    }

    public ListResp(Integer page,Long number,List<T> list){
        this.page = page;
        this.list = list;
        this.number = number;
    }
}
