package com.taotao.order.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbOrderItemMapper;
import com.taotao.mapper.TbOrderMapper;
import com.taotao.mapper.TbOrderShippingMapper;
import com.taotao.order.component.JedisClient;
import com.taotao.order.pojo.OrderInfo;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by haier on 2017/8/8.
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private TbOrderMapper orderMapper;

    @Autowired
    private TbOrderItemMapper orderItemMapper;

    @Autowired
    private TbOrderShippingMapper orderShippingMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_ORDER_GEN_KEY}")
    private String REDIS_ORDER_GEN_KEY;
    @Value("${ORDER_ID_BEGIN}")
    private String ORDER_ID_BEGIN;
    @Value("${REDIS_ORDER_DETAIL_GEN_KEY}")
    private String REDIS_ORDER_DETAIL_GEN_KEY;
    @Override
    public TaotaoResult createOrder(OrderInfo orderInfo) {
        //取出订单号
        String id = jedisClient.get(REDIS_ORDER_GEN_KEY);
        if (StringUtils.isBlank(id)) {
            //如果订单号生成的key不存在，需要设置一个初始值
            jedisClient.set(REDIS_ORDER_GEN_KEY, ORDER_ID_BEGIN);
        }
        //生成订单号
        Long orderId = jedisClient.incr(REDIS_ORDER_GEN_KEY);
        //补全字段
        orderInfo.setOrderId(orderId.toString());
        //状态：1、未付款 2、已付款 3、未发货 4、已发货 5、交易成功 6、交易关闭
        orderInfo.setStatus(1);
        Date date = new Date();
        orderInfo.setCreateTime(date);
        orderInfo.setUpdateTime(date);
        //插入订单
        orderMapper.insert(orderInfo);

        //取出订单明细
        List<TbOrderItem> orderItems = orderInfo.getOrderItems();
        for (TbOrderItem item : orderItems) {
            //生成订单明细id
            Long orderDetailId = jedisClient.incr(REDIS_ORDER_DETAIL_GEN_KEY);
            item.setId(orderDetailId.toString());
            item.setOrderId(orderId.toString());
            //插入订单明细
            orderItemMapper.insert(item);
        }
        TbOrderShipping orderShipping = orderInfo.getOrderShipping();
        orderShipping.setOrderId(orderId.toString());
        orderShipping.setCreated(date);
        orderShipping.setUpdated(date);
        //插入到物流单
        orderShippingMapper.insert(orderShipping);
        //返回taotaoResult包装订单号
        return TaotaoResult.ok(orderId);
    }
}
