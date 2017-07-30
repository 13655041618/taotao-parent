package com.taotao.service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;

import java.util.List;

/**
 * Created by haier on 2017/7/30.
 */
public interface ContextCategoryService {
    List<EasyUITreeNode> getContextCatList(Long parentId);

    TaotaoResult insertCategory(Long parentId, String name);
}
