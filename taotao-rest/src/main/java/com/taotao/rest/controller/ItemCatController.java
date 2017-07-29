package com.taotao.rest.controller;

import com.taotao.common.utils.JsonUtils;
import com.taotao.rest.pojo.ItemCatResult;
import com.taotao.rest.service.ItemCatService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by haier on 2017/7/29.
 */
@Controller
@RequestMapping("/item/cat")
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;

    /*@RequestMapping(value = "/list",produces = MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8")
    @ResponseBody
    public String getItemCatList(String callback) {
        ItemCatResult result = itemCatService.getItemCatList();
        if (StringUtils.isBlank(callback)) {
            String json = JsonUtils.objectToJson(result);
            return json;
        }
        String json = JsonUtils.objectToJson(result);
        return callback + "(" + json + ")";
    }*/
    @RequestMapping("/list")
    @ResponseBody
    public Object getItemCatList(String callback) {
        ItemCatResult result = itemCatService.getItemCatList();
        if (StringUtils.isBlank(callback)) {
            return result;
        }
        //如果字符串不为空，需要支持jsonp调用
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
        mappingJacksonValue.setJsonpFunction(callback);
        return mappingJacksonValue;
    }
}
