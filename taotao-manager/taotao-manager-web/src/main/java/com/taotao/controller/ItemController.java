package com.taotao.controller;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by haier on 2017/7/25.
 */
@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    @ResponseBody
    public TbItem getItem(@PathVariable Long itemId) {
        TbItem item = itemService.findItemByID(itemId);
        return item;
    }

    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
        EasyUIDataGridResult result = itemService.getItemList(page, rows);
        return result;
    }

    @RequestMapping(value = "/item/save",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult createItem(TbItem item, String desc,String itemParams) {
        TaotaoResult result = itemService.createItem(item, desc,itemParams);
        return result;
    }

    @RequestMapping("/item/param/list")
    @ResponseBody
    public EasyUIDataGridResult getItemCatList(Integer page, Integer rows) {
        EasyUIDataGridResult result = itemService.getItemParamList(page, rows);
        return result;
    }

    @RequestMapping("/item/page/{itemId}")
    public String showItemParam(@PathVariable Long itemId, Model model) {
        String html = itemService.getItemParamHtml(itemId);
        model.addAttribute("html", html);
        return "itemparam";
    }
}
