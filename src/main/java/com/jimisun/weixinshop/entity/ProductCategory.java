package com.jimisun.weixinshop.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * product_category
 */
@Table(name = "product_category")
@Entity
@DynamicUpdate
@Data
public class ProductCategory {


    //类目id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    //类目名字
    private String categoryName;

    //类目编号
    private Integer categoryType;

    public ProductCategory() {
    }
}
