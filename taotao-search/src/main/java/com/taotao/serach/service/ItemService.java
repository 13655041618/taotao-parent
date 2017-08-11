package com.taotao.serach.service;

import com.taotao.common.pojo.TaotaoResult;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;

/**
 * Created by haier on 2017/7/31.
 */
public interface ItemService {
    TaotaoResult importItems() throws IOException, SolrServerException;
}
