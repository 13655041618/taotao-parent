package com.taotao.portal.controller;

import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemCat;
import com.taotao.portal.pojo.PortalItem;
import com.taotao.portal.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by haier on 2017/8/15.
 */
@Controller
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @RequestMapping("/products/{cid}")
    public String search(@PathVariable Long cid, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "60") Integer rows, Model model) {
        List<PortalItem> itemList = productsService.getListByCid(cid,page,rows);
        TbItemCat item = productsService.getItemNameByCid(cid);
        int totalPage = productsService.totalPage(cid)/60;
        model.addAttribute("query", item.getName());
        model.addAttribute("totalPages", totalPage);
        model.addAttribute("itemList", itemList);
        model.addAttribute("page", page);
        return "search";
    }
}
