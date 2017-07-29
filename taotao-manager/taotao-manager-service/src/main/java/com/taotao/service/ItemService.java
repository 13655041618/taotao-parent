package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

/**
 * Created by haier on 2017/7/25.
 */
public interface ItemService {
    /**
     * 通过商品ID查询商品
     * @param Itemid
     * @return
     */
    TbItem findItemByID(Long Itemid);

    EasyUIDataGridResult getItemList(int page, int rows);

    TaotaoResult createItem(TbItem item, String desc,String itemParam);

    EasyUIDataGridResult getItemParamList(int page, int rows);

    String getItemParamHtml(Long itemId);
}
