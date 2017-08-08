package com.taotao.portal.service;

import com.taotao.pojo.TbItem;

/**
 * Created by haier on 2017/8/2.
 */
public interface ItemService {
    TbItem getItemById(Long itemId);

    String getItemDescById(Long itemId);

    String getItemParamById(Long itemId);
}
