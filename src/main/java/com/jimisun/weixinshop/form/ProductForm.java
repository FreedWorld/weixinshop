package com.jimisun.weixinshop.form;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 13:17 2018-05-20
 * @Modified By:
 */
@Data
public class ProductForm {



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


    //类目编号
    private Integer categoryType;









}
