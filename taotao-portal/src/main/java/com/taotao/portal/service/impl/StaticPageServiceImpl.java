package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.portal.service.ItemService;
import com.taotao.portal.service.StaticPageService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * 生成商品详情页面静态网页
 * Created by haier on 2017/8/4.
 */
@Service
public class StaticPageServiceImpl implements StaticPageService {

    @Value("${STATIC_PAGE_PATH}")
    private String STATIC_PAGE_PATH;

    @Autowired
    private ItemService itemService;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Override
    public TaotaoResult getItemHtml(Long itemId) throws Exception {
        //商品基本信息
        TbItem item = itemService.getItemById(itemId);
        //商品描述信息
        String itemDesc = itemService.getItemDescById(itemId);
        //商品规格参数信息
        String itemParam = itemService.getItemParamById(itemId);
        //生成静态页面
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Template template = configuration.getTemplate("item.ftl");
        //创建数据集
        Map root = new HashMap<>();
        root.put("item", item);
        root.put("itemParam", itemParam);
        root.put("itemDesc", itemDesc);
        //创建一个writer对象
        Writer out = new FileWriter(new File(STATIC_PAGE_PATH + itemId + ".html"));
        template.process(root, out);
        out.flush();
        out.close();
        return TaotaoResult.ok();
    }
}
