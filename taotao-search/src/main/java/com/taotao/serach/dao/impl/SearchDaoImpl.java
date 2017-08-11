package com.taotao.serach.dao.impl;

import com.taotao.serach.dao.SearchDao;
import com.taotao.serach.pojo.SearchItem;
import com.taotao.serach.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by haier on 2017/7/31.
 */
public class SearchDaoImpl implements SearchDao {

    @Autowired
    private SolrServer solrServer;

    @Override
    public SearchResult search(SolrQuery query) throws SolrServerException {
        QueryResponse response = solrServer.query(query);
        SolrDocumentList results = response.getResults();
        List<SearchItem> resultList = new ArrayList<>();
        for (SolrDocument document: results) {
            SearchItem item = new SearchItem();
            item.setCategory_name((String) document.get("item_category_name"));
            item.setId((String) document.get("id"));
            item.setImage((String) document.get("item_image"));
            item.setPrice((Long) document.get("item_price"));
            item.setSell_point((String) document.get("item_sell_point"));
            //取高亮显示
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            List<String> list = highlighting.get(document.get("id")).get("item_title");
            String itemTitle = "";
            if (list != null && list.size() > 0) {
                //取高亮后的结果
                itemTitle = list.get(0);
            } else {
                itemTitle = (String) document.get("item_title");
            }
            item.setTitle(itemTitle);
            //添加到列表
            resultList.add(item);
        }
        SearchResult result = new SearchResult();
        result.setItemList(resultList);
        //查询结果总数量
        result.setRecordCount(results.getNumFound());
        return result;
    }
}
