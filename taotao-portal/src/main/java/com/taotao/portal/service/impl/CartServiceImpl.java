package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;
import com.taotao.portal.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车服务
 * Created by haier on 2017/8/6.
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private ItemService itemService;
    @Value("${COOKIE_EXPIRE}")
    private Integer COOKIE_EXPIRE;

    @Override
    public TaotaoResult addCart(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) {
        //查询商品列表中是否存在此商品
        List<CartItem> list = getCartItemList(request);
        boolean flag = false;
        for (CartItem item: list) {
            if (item.getId().longValue() == itemId) {
                //如果存在则在数量上增加
                item.setNum(item.getNum()+num);
                flag = true;
                break;
            }
        }
        //如果不存在，则调用rest的服务，根据id获取商品数据
        if (!flag) {
            TbItem item = itemService.getItemById(itemId);
            CartItem cartItem = new CartItem();
            cartItem.setId(itemId);
            cartItem.setNum(num);
            cartItem.setPrice(item.getPrice());
            cartItem.setTitle(item.getTitle());
            if (StringUtils.isNotBlank(cartItem.getImage())) {
                String image = item.getImage();
                String[] split = image.split(",");
                cartItem.setImage(split[0]);
            }
            //添加到购物车商品列表
            list.add(cartItem);
        }
        // 把购物车商品列表写入cookie
        CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(list), COOKIE_EXPIRE, true);
        // 返回TaotaoResult
        return TaotaoResult.ok();
    }

    private List<CartItem> getCartItemList(HttpServletRequest request) {
        try {
            String json = CookieUtils.getCookieValue(request, "TT_CART", true);
            List<CartItem> list = JsonUtils.jsonToList(json, CartItem.class);
            return list==null?new ArrayList<CartItem>():list;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<CartItem>();
        }
    }

    public List<CartItem> getCartList(HttpServletRequest request) {
        return getCartItemList(request);
    }

    @Override
    public TaotaoResult updateCartItem(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) {
        //从cookie中取出商品列表
        List<CartItem> cartItemList = getCartItemList(request);
        for (CartItem item:cartItemList) {
            if (item.getId() == itemId) {
                //更新数量
                item.setNum(num);
                break;
            }
        }
        //写入到cookie
        CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(cartItemList), COOKIE_EXPIRE, true);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult deleteCartItem(Long itemId, HttpServletRequest request, HttpServletResponse response) {
        //从cookie中取出商品列表
        List<CartItem> cartItemList = getCartItemList(request);
        for (CartItem item:cartItemList) {
            if (item.getId() == itemId) {
                //更新数量
                cartItemList.remove(item);
                break;
            }
        }
        //写入到cookie
        CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(cartItemList), COOKIE_EXPIRE, true);
        return TaotaoResult.ok();
    }
}
