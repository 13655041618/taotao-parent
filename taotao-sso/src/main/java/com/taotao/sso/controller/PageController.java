package com.taotao.sso.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.sso.service.LoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by haier on 2017/8/5.
 */
@Controller
public class PageController {

    /**
     * 展示登陆页面
     * @return
     */
    @RequestMapping("/page/login")
    public String showLogin(String redirectURL, Model model) {
        model.addAttribute("redirect", redirectURL);
        return "login";
    }

    @RequestMapping("/page/register")
    public String showRegister() {
        return "register";
    }
}
