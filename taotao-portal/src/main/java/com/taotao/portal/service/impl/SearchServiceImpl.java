package com.taotao.portal.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import com.taotao.portal.pojo.PortalItem;
import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by haier on 2017/8/1.
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Value("${SEARCH_URL}")
    private String SEARCH_URL;

    @Autowired
    private TbItemMapper itemMapper;

    @Override
    public SearchResult search(String keyword, Integer page, Integer rows) {
        Map<String, String> param = new HashMap<>();
        param.put("keyword", keyword);
        param.put("page", page+"");
        param.put("rows", rows+"");
        String json = HttpClientUtil.doGet(SEARCH_URL, param);
        //转换成java对象
        TaotaoResult result = TaotaoResult.formatToPojo(json, SearchResult.class);
        //取出返回的结果
        SearchResult searchResult = (SearchResult) result.getData();
        return searchResult;
    }

    @Override
    public List<PortalItem> searchItem(String keyword,Integer page,Integer rows) {
        PageHelper.startPage(page,rows);
        List<TbItem> itemList = itemMapper.selectByTitleLike(keyword);
        PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(itemList);
        List<PortalItem> portalItemList = new ArrayList<PortalItem>();
        for (TbItem item:itemList) {
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
            portalItemList.add(p);
        }
        return portalItemList;
    }

    @Override
    public int totalPage(String keyword) {
        List<TbItem> itemList = itemMapper.selectByTitleLike(keyword);
        return itemList.size();
    }
}
