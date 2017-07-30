package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.AdNode;
import com.taotao.portal.service.ContentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 内容管理service
 * Created by haier on 2017/7/30.
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${REST_CONTENT_URL}")
    private String REST_CONTENT_URL;
    @Value("${REST_CONTENT_AD1_CID}")
    private String REST_CONTENT_AD1_CID;
    @Value("%{PIC_HOST_URL}")
    private String PIC_HOST_URL;

    /**
     * 获得大广告位的内容
     * @return
     */
    @Override
    public String getAd1List() {
        //调用服务层的服务
        String result = HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_URL + REST_CONTENT_AD1_CID);
        //把字符串转换成TaotaoResult
        try {
            TaotaoResult taotaoResult = TaotaoResult.formatToList(result, TbContent.class);
            //取内容列表
            List<TbContent> list = (List<TbContent>) taotaoResult.getData();
            List<Map> resultList = new ArrayList<>();
            //创建一个jsp页码要求的pojo列表
            for (TbContent tbContent : list) {
                Map map = new HashMap<>();
                map.put("src", PIC_HOST_URL + tbContent.getPic());
                map.put("height", 240);
                map.put("width", 670);
                map.put("srcB", PIC_HOST_URL + tbContent.getPic2());
                map.put("widthB", 550);
                map.put("heightB", 240);
                map.put("href", tbContent.getUrl());
                map.put("alt", tbContent.getSubTitle());
                resultList.add(map);
            }
            return JsonUtils.objectToJson(resultList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
