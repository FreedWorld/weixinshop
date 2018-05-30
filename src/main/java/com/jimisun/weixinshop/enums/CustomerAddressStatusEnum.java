package com.jimisun.weixinshop.enums;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 11:22 2018-05-30
 * @Modified By:
 */
public enum  CustomerAddressStatusEnum {

    NEW(1,"未选定"),
    FINISHEND(0,"选定"),
            ;

    private Integer code;

    private String message;

    CustomerAddressStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
