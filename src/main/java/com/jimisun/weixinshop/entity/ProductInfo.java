package com.jimisun.weixinshop.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

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


}
