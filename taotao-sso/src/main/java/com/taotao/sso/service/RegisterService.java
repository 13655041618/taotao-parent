package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

/**
 * Created by haier on 2017/8/5.
 */
public interface RegisterService {
    TaotaoResult checkData(String param, int type);

    TaotaoResult register(TbUser user);
}
