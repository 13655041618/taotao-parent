package com.taotao.rest.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

import java.util.List;

/**
 * Created by haier on 2017/7/30.
 */
public interface ContentService {
    List<TbContent> getContentList(Long cid);

    TaotaoResult syncContent(Long cid);
}
