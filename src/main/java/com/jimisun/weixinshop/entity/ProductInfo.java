package com.jimisun.weixinshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jimisun.weixinshop.enums.ProductInfoStatusEnum;
import com.jimisun.weixinshop.utils.EnumUtil;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "product_info")
@DynamicUpdate
public class ProductInfo {

    @Id
    private String  productId;

    //名字
    private String productName;

    //价格
    private BigDecimal productPrice;

    //库存
    private Integer productStock;

    //描述
    private String productDescription;

    //图片链接
    private String productIcon;

    //状态  0正常  1下架
    private Integer productStatus;

    //类目编号
    private Integer categoryType;


    /** 创建时间. */
    private Date createTime;

    /** 更新时间. */
    private Date updateTime;

    @JsonIgnore
    public ProductInfoStatusEnum getProductInfoStatusEnum(){
        return EnumUtil.getByCode(productStatus,ProductInfoStatusEnum.class);
    }



}
