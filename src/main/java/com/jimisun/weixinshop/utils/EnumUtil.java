package com.jimisun.weixinshop.utils;

import com.jimisun.weixinshop.enums.CodeEnum;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 16:30 2018-05-18
 * @Modified By:
 */
public class EnumUtil {

    public static <T extends CodeEnum> T getByCode(Integer code,Class<T> enumClass){
        for(T each : enumClass.getEnumConstants()){
            if(code.equals(each.getCode())){
                return  each;
            }
        }
        return  null;
    }
}
