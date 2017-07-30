package com.taotao.service.impl;

import com.taotao.common.pojo.PictureResult;
import com.taotao.service.PictureService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 图片上传Service
 * Created by haier on 2017/7/27.
 */
@Service
public class PictureServiceImpl implements PictureService {
    @Override
    public PictureResult uploadPic(MultipartFile multipartFile,String realPath) {
        PictureResult result = new PictureResult();
        if (multipartFile.isEmpty()) {
            result.setError(1);
            result.setMessage("图片为空");
            return result;
        }
        File saveDir = new File(realPath);
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }
        CommonsMultipartFile file = (CommonsMultipartFile) multipartFile;
        //获取文件上传流
        byte[] bytes = file.getBytes();
        //文件名称在服务器可能存在重复
        String newFileName = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        newFileName = sdf.format(new Date());
        Random r = new Random();
        for (int i = 0; i < 3; i++) {
            newFileName += r.nextInt(10);
        }
        //获取文件扩展名
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newPath = realPath + newFileName + suffix;
        File newFile = new File(newPath);
        OutputStream out = null;
        try {
            out = new FileOutputStream(newFile);
            out.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            result.setError(1);
            result.setMessage("图片上传失败！");
            return result;
        }finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        result.setError(0);
        result.setUrl("http://localhost:8080/upload/" + newFileName + suffix);
        return result;
    }
}
