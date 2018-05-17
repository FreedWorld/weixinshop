package com.jimisun.weixinshop.vo;

import lombok.Data;

/**
 * HTTP请求返回的最外层对象
 */
@Data
public class ResultVo<T> {

    //错误码
    private Integer code;

    //错误信息
    private String msg;

    //返回数据
    private T Data;

}
