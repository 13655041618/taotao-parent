package com.taotao.controller;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ContextCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by haier on 2017/7/30.
 */
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {

    @Autowired
    private ContextCategoryService contextCategoryService;

    @RequestMapping("/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCateList(@RequestParam(value = "id",defaultValue = "0") Long parentId) {
        return contextCategoryService.getContextCatList(parentId);
    }

    @RequestMapping("/create")
    @ResponseBody
    public TaotaoResult createNode(Long parentId,String name) {
        return contextCategoryService.insertCategory(parentId, name);
    }
}
