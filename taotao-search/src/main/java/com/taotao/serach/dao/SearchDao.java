package com.taotao.serach.dao;

import com.taotao.serach.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;

/**
 * Created by haier on 2017/7/31.
 */
public interface SearchDao {
    SearchResult search(SolrQuery query) throws SolrServerException;
}
