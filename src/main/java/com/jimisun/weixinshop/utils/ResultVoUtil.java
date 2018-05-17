package com.jimisun.weixinshop.utils;

import com.jimisun.weixinshop.enums.ResultVoCodeEnum;
import com.jimisun.weixinshop.vo.ResultVo;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 21:59 2018-05-12
 * @Modified By:
 */
public class ResultVoUtil {

    public static ResultVo sucess(Object object){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(ResultVoCodeEnum.SUCCESS.getCode());
        resultVo.setMsg(ResultVoCodeEnum.SUCCESS.getMsg());
        resultVo.setData(object);
        return resultVo;
    }

    public static ResultVo sucess(){
        return sucess(null);
    }


    public static ResultVo error(Integer code,String msg){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(code);
        resultVo.setMsg(msg);
        return resultVo;
    }
}
