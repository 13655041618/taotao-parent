package com.taotao.rest.service.impl;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.pojo.ItemCatResult;
import com.taotao.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品 分类列表查询
 * Created by haier on 2017/7/29.
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Override
    public ItemCatResult getItemCatList() {
        //调用递归方法来查询商品列表
        List list = getItemCatList(0L);
        //返回结果
        ItemCatResult result = new ItemCatResult();
        result.setData(list);
        return result;
    }

    private List getItemCatList(Long parentId) {
        //根据parentId查询列表
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> list = itemCatMapper.selectByExample(example);
        List resultList = new ArrayList<>();
        int index = 0;
        for (TbItemCat item: list) {
            //如果是父节点
            if (index >= 14) {
                break;
            }
            if (item.getIsParent()) {
                CatNode node = new CatNode();
                node.setUrl("/products/" + item.getId() + ".html");
                //如果当前节点为第一级节点
                if (item.getParentId() == 0) {
                    node.setName("<a href = '/products/'"+ item.getId() + ".html>'" + item.getName() + "</a>");
                    index++;
                }else {
                    node.setName(item.getName());
                }
                node.setItems(getItemCatList(item.getId()));
                resultList.add(node);

            }else {
                //如果不是父节点
                String ChildrenNode = "/products/" + item.getId() + ".html|" + item.getName();
                resultList.add(ChildrenNode);
            }
        }
        return resultList;
    }
}
