package com.taotao.controller;

import com.taotao.common.pojo.PictureResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by haier on 2017/7/27.
 */
@Controller
@RequestMapping("/pic")
public class PictureController {
    @Autowired
    private PictureService pictureService;

    @RequestMapping("/upload")
    @ResponseBody
    public String showPicture(HttpServletRequest request ,MultipartFile uploadFile) {
        String realPath = request.getSession().getServletContext().getRealPath("/upload/");
        PictureResult result = pictureService.uploadPic(uploadFile,realPath);
        String json = JsonUtils.objectToJson(result);
        return json;
    }
}
