package com.jimisun.weixinshop.exception;

import com.jimisun.weixinshop.enums.ResultVoCodeEnum;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 22:10 2018-05-12
 * @Modified By:
 */
public class SellException extends RuntimeException {
    private Integer code;

    public SellException(ResultVoCodeEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public SellException(Integer code,String message) {
        super(message);
        this.code = code;
    }
}
