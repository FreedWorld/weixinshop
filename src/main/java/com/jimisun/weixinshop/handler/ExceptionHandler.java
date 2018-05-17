package com.jimisun.weixinshop.handler;

import com.jimisun.weixinshop.enums.ResultVoCodeEnum;
import com.jimisun.weixinshop.exception.SellException;
import com.jimisun.weixinshop.utils.ResultVoUtil;
import com.jimisun.weixinshop.vo.ResultVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 22:09 2018-05-12
 * @Modified By:
 */
@ControllerAdvice
public class ExceptionHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionHandler.class);

    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultVo handler(Exception e){
        //判断是否是自己定义的异常类
        if(e instanceof SellException){
            SellException productException = (SellException)e;
            //注意：这里返回的是统一返回类型
            return ResultVoUtil.error(productException.getCode(),productException.getMessage());
        }else{
            LOGGER.error("------系统异常-----{}",e);
            //注意：这里返回的是统一返回类型
            return ResultVoUtil.error(ResultVoCodeEnum.UNKONW_ERROR.getCode(),ResultVoCodeEnum.UNKONW_ERROR.getMsg());
        }

    }
}