package com.taotao.order.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.order.pojo.OrderInfo;

/**
 * Created by haier on 2017/8/8.
 */
public interface OrderService {
    TaotaoResult createOrder(OrderInfo orderInfo);
}
