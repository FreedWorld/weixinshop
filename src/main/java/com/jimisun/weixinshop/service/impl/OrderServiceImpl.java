package com.jimisun.weixinshop.service.impl;

import com.jimisun.weixinshop.converter.OrderMaster2OrderDTO;
import com.jimisun.weixinshop.dao.OrderDetailDao;
import com.jimisun.weixinshop.dao.OrderMasterDao;
import com.jimisun.weixinshop.dto.CartDTO;
import com.jimisun.weixinshop.dto.OrderDTO;
import com.jimisun.weixinshop.entity.OrderDetail;
import com.jimisun.weixinshop.entity.OrderMaster;
import com.jimisun.weixinshop.entity.ProductInfo;
import com.jimisun.weixinshop.enums.OrderStatusEnum;
import com.jimisun.weixinshop.enums.PayStatusEnum;
import com.jimisun.weixinshop.enums.ResultVoCodeEnum;
import com.jimisun.weixinshop.exception.SellException;
import com.jimisun.weixinshop.service.OrderService;
import com.jimisun.weixinshop.service.ProductInfoService;
import com.jimisun.weixinshop.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:jimisun
 * @Description:
 * @Date:Created in 22:54 2018-05-13
 * @Modified By:
 */
@SuppressWarnings("AlibabaTransactionMustHaveRollback")
@Service
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService  {

    @Autowired
    private OrderMasterDao orderMasterDao;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private ProductInfoService productInfoService;

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable)  {
        Page<OrderMaster> orderMasterPage = orderMasterDao.findByBuyerOpenid(buyerOpenid,pageable);
        List<OrderDTO>orderDTOS =OrderMaster2OrderDTO.convert(orderMasterPage.getContent());
        Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOS,pageable,orderMasterPage.getTotalElements());
        return orderDTOPage;
    }

    @Override
    public OrderDTO create(OrderDTO orderDTO){

        //生成订单id
        String orderId = KeyUtil.genUniqueKey();

        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

        //查询商品的数量，价格
        for(OrderDetail orderDetail : orderDTO.getOrderDetailList()){
            ProductInfo productInfo = productInfoService.findOne(orderDetail.getProductId());
            if (productInfo == null){
                throw new  SellException(ResultVoCodeEnum.PRODUCT_NOT_EXIST);
            }
            //计算总价
            orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            //订单详情入库
            orderDetail.setOrderId(orderId);
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetailDao.save(orderDetail);
        }

        //订单主表入库 (OrderMaster,OrderDetail)
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterDao.save(orderMaster);

        //扣库存
        List<CartDTO> cartDTOList =orderDTO.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());
        productInfoService.decreaseStock(cartDTOList);

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterDao.getOne(orderId);
        if(orderMaster==null){
            throw new SellException(ResultVoCodeEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail>orderDetails = orderDetailDao.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetails)){
            throw new SellException(ResultVoCodeEnum.ORDER_DETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetails);
        return orderDTO;
    }

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        //判断订单的状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode() )){
            log.error("取消订单订单状态不正确orderid={},orderstatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultVoCodeEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster result = orderMasterDao.save(orderMaster);
        if(result==null){
            log.error("更新失败{}",orderMaster);
            throw new SellException(ResultVoCodeEnum.ORDER_UPDATE_FAIL);
        }

        //返还库存
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("更新失败{}",orderMaster);
            throw new SellException(ResultVoCodeEnum.ORDER_DETAIL_EMPIY);
        }
        List<CartDTO>cartDTOS = orderDTO.getOrderDetailList().stream().map(e -> new CartDTO(e.getProductId(),
                e.getProductQuantity())).collect(Collectors.toList());
        productInfoService.increaseStock(cartDTOS);


        //TODO 如果已支付，需要退款
        if(orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS)){
            //TODO 退款逻辑
        }

        return orderDTO;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            throw new SellException(ResultVoCodeEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHEND.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterDao.save(orderMaster);
        if(updateResult==null){
            throw new SellException(ResultVoCodeEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            throw new SellException(ResultVoCodeEnum.ORDER_STATUS_ERROR);
        }

        //判断支付状态
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            throw new SellException(ResultVoCodeEnum.ORDER_PAY_STATUS_ERROR);
        }

        //修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterDao.save(orderMaster);
        if(updateResult==null){
            throw new SellException(ResultVoCodeEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }
}
