package com.jimisun.weixinshop.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 11:22 2018-05-24
 * @Modified By:
 */
@Entity
@Data
public class Notice {

    @Id
    private Integer id;

    private String name;

    private String content;


}
