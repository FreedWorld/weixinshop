package com.jimisun.weixinshop.controller;

import com.jimisun.weixinshop.converter.OrderForm2OrderDTO;
import com.jimisun.weixinshop.dto.OrderDTO;
import com.jimisun.weixinshop.enums.ResultVoCodeEnum;
import com.jimisun.weixinshop.exception.SellException;
import com.jimisun.weixinshop.form.OrderForm;
import com.jimisun.weixinshop.service.BuyerService;
import com.jimisun.weixinshop.service.OrderService;
import com.jimisun.weixinshop.utils.ResultVoUtil;
import com.jimisun.weixinshop.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 15:02 2018-05-14
 * @Modified By:
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    /**
     * 创建订单
     * @param orderForm
     * @param bindingResult
     * @return
     * @throws Exception
     */
    @PostMapping("/create")
    public ResultVo<Map<String, String>> create(@Valid OrderForm orderForm,
                                                BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确, orderForm={}", orderForm);
            throw new SellException(ResultVoCodeEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderForm2OrderDTO.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultVoCodeEnum.CART_EMPTY);
        }

        OrderDTO createResult = orderService.create(orderDTO);

        Map<String, String> map = new HashMap<>();
        map.put("orderId", createResult.getOrderId());

        return ResultVoUtil.sucess(map);
    }


    /**
     * 获取订单列表
     * @param openid
     * @param page
     * @param size
     * @return
     * @throws Exception
     */
    @GetMapping("/list")
    public ResultVo<List<OrderDTO>> list(@RequestParam(value = "openid") String openid,
                                         @RequestParam(value = "page" ,defaultValue = "0")Integer page,
                                         @RequestParam(value = "size",defaultValue = "10")Integer size) throws  Exception{
        //参数校验
        if(StringUtils.isEmpty(openid)){
            throw new SellException(ResultVoCodeEnum.PARAM_ERROR);
        }
        //封装PageRequest对象
        PageRequest pageRequest = new PageRequest(page,size);
        Page<OrderDTO> orderDTOPage= orderService.findList(openid,pageRequest);

        return ResultVoUtil.sucess(orderDTOPage.getContent());
    }


    /**
     * 查看订单详情
     * @param openid
     * @param orderId
     * @return
     * @throws Exception
     */
    @GetMapping("/detail")
    public ResultVo<OrderDTO>detail(@RequestParam("openid") String openid,
                                    @RequestParam("orderId")String orderId) throws  Exception{
        OrderDTO orderDTO = buyerService.findOrderOne(openid,orderId);
        return ResultVoUtil.sucess(orderDTO);

    }

    /**
     * 取消订单
     * @param openid
     * @param orderId
     * @return
     * @throws Exception
     */
    @PostMapping("/cancel")
    public ResultVo<OrderDTO>cancel(@RequestParam("openid") String openid,
                                    @RequestParam("orderId")String orderId) throws  Exception{
        buyerService.cancelOrder(openid,orderId);
        return ResultVoUtil.sucess();
    }


}
