package com.taotao.portal.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.PortalItem;
import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * Created by haier on 2017/8/1.
 */
@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;

    //solr搜索配置
    /*@RequestMapping("/search")
    public String search(@RequestParam("q") String keyword, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "60") Integer rows, Model model) {
        //get乱码处理
        try {
            keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            keyword = "";
            e.printStackTrace();
        }
        SearchResult searchResult = searchService.search(keyword, page, rows);
        //参数传递 给页面
        model.addAttribute("query", keyword);
        model.addAttribute("totalPages", searchResult.getPageCount());
        model.addAttribute("itemList", searchResult.getItemList());
        model.addAttribute("page", searchResult.getCurPage());

        //返回逻辑视图
        return "search";
    }*/

    //直接访问mybatis搜索
    @RequestMapping(value = "/search")
    public String search(@RequestParam("q") String keyword, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "60") Integer rows, Model model) {
        //get乱码处理
        try {
            keyword = URLDecoder.decode(keyword, "utf-8");
        } catch (UnsupportedEncodingException e) {
            keyword = "";
            e.printStackTrace();
        }
        List<PortalItem> itemList = searchService.searchItem(keyword,page,rows);
        //参数传递 给页面
        int totalPage = searchService.totalPage(keyword)/60 + 1;
        model.addAttribute("query", keyword);
        model.addAttribute("totalPages", totalPage);
        model.addAttribute("itemList", itemList);
        model.addAttribute("page", page);
        //返回逻辑视图
        return "search";
    }
}
