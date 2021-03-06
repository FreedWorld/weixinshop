package com.jimisun.weixinshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jimisun.weixinshop.enums.CustomerStatusEnum;
import com.jimisun.weixinshop.utils.EnumUtil;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 11:26 2018-05-29
 * @Modified By:
 */
@Entity
@Data
public class Customer {

    @Id
    private String id;
    private String username;
    private String password;
    private String openid;
    private Date createTime;
    private Date updateTime;
    private Integer status= CustomerStatusEnum.FINISHEND.getCode();

    @JsonIgnore
    public CustomerStatusEnum getCustomerStatusEnum(){
        return EnumUtil.getByCode(status,CustomerStatusEnum.class);
    }


}
