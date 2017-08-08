package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;

/**
 * Created by haier on 2017/7/28.
 */
public interface ItemParamService {
    TaotaoResult getItemParamByCid(Long cid);

    TaotaoResult insertItemParam(Long cid,String paramData);

}
