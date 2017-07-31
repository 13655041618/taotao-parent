package com.taotao.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by haier on 2017/7/30.
 */
@Controller
@RequestMapping("/content")
public class ContentController {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${RESET_CONTENT_SYNC_URL}")
    private String RESET_CONTENT_SYNC_URL;

    @Autowired
    private ContentService contentService;

    @RequestMapping("/save")
    @ResponseBody
    public TaotaoResult insertContent(TbContent content) {
        TaotaoResult result = contentService.insertContent(content);
        //调用taotao-rest发布的服务，同步缓存
        HttpClientUtil.doGet(REST_BASE_URL + RESET_CONTENT_SYNC_URL + content.getCategoryId());
        return result;
    }
}
