package com.taotao.portal.service;

import com.taotao.pojo.TbItemCat;
import com.taotao.portal.pojo.PortalItem;

import java.util.List;

/**
 * Created by haier on 2017/8/15.
 */
public interface ProductsService {
    List<PortalItem> getListByCid(Long cid,Integer page,Integer rows);

    TbItemCat getItemNameByCid(Long cid);

    int totalPage(Long cid);
}
