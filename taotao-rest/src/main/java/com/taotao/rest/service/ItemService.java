package com.taotao.rest.service;

import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamItem;

/**
 * Created by haier on 2017/8/1.
 */
public interface ItemService {
    TbItem getItemById(Long itemId);

    TbItemDesc getItemDescById(Long itemId);

    TbItemParamItem getItemParamById(Long itemId);
}
