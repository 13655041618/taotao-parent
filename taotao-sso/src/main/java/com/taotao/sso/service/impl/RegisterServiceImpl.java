package com.taotao.sso.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.sso.service.RegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * 注册服务Service
 * Created by haier on 2017/8/5.
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private TbUserMapper userMapper;

    @Override
    public TaotaoResult checkData(String param, int type) {
        //根据数据类型检查数据
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        if (1 == type) {//type表示类型  1 = username  ,2 = phone , 3 = email
            criteria.andUsernameEqualTo(param);
        } else if (2 == type) {
            criteria.andPhoneEqualTo(param);
        } else if (3 == type) {
            criteria.andEmailEqualTo(param);
        }
        List<TbUser> list = userMapper.selectByExample(example);
        if (list == null || list.isEmpty()) {
            return TaotaoResult.ok(true);
        }
        return TaotaoResult.ok(false);
    }

    @Override
    public TaotaoResult register(TbUser user) {
        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
            return TaotaoResult.build(400, "用户名或密码不能为空！");
        }
        //校验账号
        TaotaoResult result = checkData(user.getUsername(), 1);
        if (!(Boolean) result.getData()) {
            return TaotaoResult.build(400,"用户名已存在");
        }
        //校验手机号
        if (user.getPhone() != null) {
            TaotaoResult resultPhone = checkData(user.getPhone(), 2);
            if (!(Boolean) resultPhone.getData()) {
                return TaotaoResult.build(400,"手机号码已存在");
            }
        }
        //校验邮箱
        if (user.getEmail() != null) {
            TaotaoResult resultEmail = checkData(user.getEmail(), 3);
            if (!(Boolean) resultEmail.getData()) {
                return TaotaoResult.build(400,"邮箱已存在");
            }
        }
        user.setCreated(new Date());
        user.setUpdated(new Date());
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        userMapper.insert(user);
        return TaotaoResult.ok();
    }
}
