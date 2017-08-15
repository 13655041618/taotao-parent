package com.taotao.portal.service.impl;

import com.github.pagehelper.PageHelper;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemExample;
import com.taotao.portal.pojo.PortalItem;
import com.taotao.portal.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haier on 2017/8/15.
 */
@Service
public class ProductsServiceImpl implements ProductsService {

    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemCatMapper itemCatMapper;
    @Override
    public List<PortalItem> getListByCid(Long cid,Integer page,Integer rows) {
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andCidEqualTo(cid);
        PageHelper.startPage(page,rows);
        List<TbItem> itemList = itemMapper.selectByExample(example);
        List<PortalItem> list = new ArrayList<>();
        if (itemList != null && itemList.size() > 0) {
            for (TbItem item : itemList) {
                PortalItem p = Item2PortalItem(item);
                list.add(p);
            }
        }else {
            TbItemCat itemCat = itemCatMapper.selectByPrimaryKey(cid);
            if (itemCat != null) {
                TbItemCatExample itemCatExample = new TbItemCatExample();
                TbItemCatExample.Criteria itemCatExampleCriteria = itemCatExample.createCriteria();
                itemCatExampleCriteria.andParentIdEqualTo(cid);
                List<TbItemCat> itemCats = itemCatMapper.selectByExample(itemCatExample);
                if (itemCats != null) {
                    for (TbItemCat cat : itemCats) {
                        List<PortalItem> list1 = getListByCid(cat.getId(),page,rows);
                        list.addAll(list1);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public TbItemCat getItemNameByCid(Long cid) {
        TbItemCat item = itemCatMapper.selectByPrimaryKey(cid);
        return item;
    }

    @Override
    public int totalPage(Long cid) {
        int totalPage = 0;
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andCidEqualTo(cid);
        List<TbItem> itemList = itemMapper.selectByExample(example);
        totalPage += itemList.size();
        if (itemList.size() == 0) {
            TbItemCat itemCat = itemCatMapper.selectByPrimaryKey(cid);
            if (itemCat != null) {
                TbItemCatExample itemCatExample = new TbItemCatExample();
                TbItemCatExample.Criteria itemCatExampleCriteria = itemCatExample.createCriteria();
                itemCatExampleCriteria.andParentIdEqualTo(cid);
                List<TbItemCat> itemCats = itemCatMapper.selectByExample(itemCatExample);
                totalPage += itemCats.size();
                if (itemCats != null) {
                    for (TbItemCat cat : itemCats) {
                        totalPage += totalPage(cat.getId());
                    }
                }
            }
        }
        return totalPage;
    }

    public PortalItem Item2PortalItem(TbItem item) {
        PortalItem p = new PortalItem();
        p.setId(item.getId());
        p.setTitle(item.getTitle());
        p.setImage(item.getImage());
        p.setUpdated(item.getUpdated());
        p.setCreated(item.getCreated());
        p.setBarcode(item.getBarcode());
        p.setCid(item.getCid());
        p.setNum(item.getNum());
        p.setPrice(item.getPrice());
        p.setStatus(item.getStatus());
        p.setSellPoint(item.getSellPoint());
        return p;
    }
}
