package com.jimisun.weixinshop.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 10:48 2018-05-30
 * @Modified By:
 */
@Entity
@Data
public class CustomerAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String openid;
    private String getName;
    private String getPhone;
    private String getArea;
    private String getAddress;
    private Integer status;


}
