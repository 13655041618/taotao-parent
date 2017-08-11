package com.taotao.serach.service.impl;

import com.taotao.serach.dao.SearchDao;
import com.taotao.serach.pojo.SearchResult;
import com.taotao.serach.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by haier on 2017/7/31.
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchDao searchDao;

    @Override
    public SearchResult search(String query, int page, int rows) throws Exception{
        //创建查询条件
        SolrQuery solrQuery = new SolrQuery();
        //设置查询条件
        solrQuery.setQuery(query);
        //设置分页条件
        solrQuery.setStart((page-1)*rows);
        solrQuery.setRows(rows);
        //设置默认搜索域
        solrQuery.set("df", "item_title");
        //设置高亮
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("item_title");
        solrQuery.setHighlightSimplePre("<font class=\"skcolor_ljg\">");
        solrQuery.setHighlightSimplePost("</font>");
        //执行查询
        SearchResult searchResult = searchDao.search(solrQuery);
        //计算总页数
        Long recordCount = searchResult.getRecordCount();
        int pageCount = (int) (recordCount / rows);
        if (recordCount % rows > 0) {
            pageCount++;
        }
        searchResult.setPageCount(pageCount);
        searchResult.setCurPage(page);
        return searchResult;
    }
}
