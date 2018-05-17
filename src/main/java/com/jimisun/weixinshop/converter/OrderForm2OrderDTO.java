package com.jimisun.weixinshop.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jimisun.weixinshop.dto.OrderDTO;
import com.jimisun.weixinshop.entity.OrderDetail;
import com.jimisun.weixinshop.enums.ResultVoCodeEnum;
import com.jimisun.weixinshop.exception.SellException;
import com.jimisun.weixinshop.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 15:33 2018-05-14
 * @Modified By:
 */
@Slf4j
public class OrderForm2OrderDTO {

    public static OrderDTO convert(OrderForm orderForm) {
        Gson gson = new Gson();
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>() {
                    }.getType());
        } catch (Exception e) {
            log.error("【对象转换】错误, string={}", orderForm.getItems());
            throw new SellException(ResultVoCodeEnum.PARAM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }

}
