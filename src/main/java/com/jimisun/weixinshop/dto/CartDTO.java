package com.jimisun.weixinshop.dto;

import lombok.Data;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 23:28 2018-05-13
 * @Modified By:
 */
@Data
public class CartDTO {
    /** 商品 **/
    private String productId;

    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
