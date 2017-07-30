package com.taotao.service.impl;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.service.ContextCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 内容分类管理实现Service
 * Created by haier on 2017/7/30.
 */
@Service
public class ContextCategoryServiceImpl implements ContextCategoryService {
    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    @Override
    public List<EasyUITreeNode> getContextCatList(Long parentId) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        //转换成EasyUITreeNode列表
        List<EasyUITreeNode> result = new ArrayList<EasyUITreeNode>();
        for (TbContentCategory c: list) {
            EasyUITreeNode  node = new EasyUITreeNode();
            node.setId(c.getId());
            node.setText(c.getName());
            node.setState(c.getIsParent()?"closed":"open");
            result.add(node);
        }
        return result;
    }

    @Override
    public TaotaoResult insertCategory(Long parentId, String name) {
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setName(name);
        contentCategory.setParentId(parentId);
        contentCategory.setStatus(1);
        contentCategory.setIsParent(false);
        //排列序号，数据库中全为1
        contentCategory.setSortOrder(1);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        contentCategoryMapper.insert(contentCategory);
        //取返回的主键
        Long id = contentCategory.getId();
        //判断父节点的IsParent属性
        TbContentCategory parentNode = contentCategoryMapper.selectByPrimaryKey(parentId);
        if (!parentNode.getIsParent()) {
            parentNode.setIsParent(true);
            contentCategoryMapper.updateByPrimaryKey(parentNode);
        }
        return TaotaoResult.ok(id);
    }
}
