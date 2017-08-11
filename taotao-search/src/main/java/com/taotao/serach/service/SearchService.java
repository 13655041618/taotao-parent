package com.taotao.serach.service;

import com.taotao.serach.pojo.SearchResult;

/**
 * Created by haier on 2017/7/31.
 */
public interface SearchService {
    SearchResult search(String query, int page, int rows) throws Exception;
}
