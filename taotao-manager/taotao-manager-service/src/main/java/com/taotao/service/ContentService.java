package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

/**
 * Created by haier on 2017/7/30.
 */
public interface ContentService {
    TaotaoResult insertContent(TbContent content);

    EasyUIDataGridResult getContentList(Integer page, Integer rows);
}
