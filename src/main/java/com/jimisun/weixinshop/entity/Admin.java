package com.jimisun.weixinshop.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 14:51 2018-06-04
 * @Modified By:
 */
@Entity
@Data
public class Admin {

    @Id
    private Integer id;
    private String username;
    private String password;

}
