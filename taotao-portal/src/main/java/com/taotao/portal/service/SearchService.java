package com.taotao.portal.service;

import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.PortalItem;
import com.taotao.portal.pojo.SearchResult;

import java.util.List;

/**
 * Created by haier on 2017/8/1.
 */
public interface SearchService {
    SearchResult search(String keyword, Integer page, Integer rows);

    List<PortalItem> searchItem(String keyword, Integer page, Integer rows);

    int totalPage(String keyword);
}
