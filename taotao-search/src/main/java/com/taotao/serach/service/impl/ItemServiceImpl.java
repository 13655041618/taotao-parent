package com.taotao.serach.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.serach.mapper.ItemMapper;
import com.taotao.serach.pojo.SearchItem;
import com.taotao.serach.service.ItemService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Created by haier on 2017/7/31.
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private SolrServer solrServer;

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public TaotaoResult importItems() throws IOException, SolrServerException {
        List<SearchItem> itemList = itemMapper.getItemList();
        for (SearchItem item: itemList) {
            SolrInputDocument document = new SolrInputDocument();
            //添加域
            document.addField("id", item.getId());
            document.addField("item_title", item.getTitle());
            document.addField("item_sell_point", item.getSell_point());
            document.addField("item_price", item.getPrice());
            document.addField("item_image", item.getImage());
            document.addField("item_category_name", item.getCategory_name());
            document.addField("item_desc", item.getItem_desc());
            solrServer.add(document);
        }
        solrServer.commit();
        return TaotaoResult.ok();
    }
}
