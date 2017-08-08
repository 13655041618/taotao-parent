package com.taotao.portal.service;

import com.taotao.common.pojo.TaotaoResult;

import java.io.IOException;

/**
 * Created by haier on 2017/8/4.
 */
public interface StaticPageService {
    TaotaoResult getItemHtml(Long itemId) throws Exception;
}
