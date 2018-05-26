package com.jimisun.weixinshop.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 12:38 2018-05-25
 * @Modified By:
 */
@Entity
@Data
public class Ad1 {

    @Id
    private Integer id;

    private String name;

    private String pic;

    private String address;


}
