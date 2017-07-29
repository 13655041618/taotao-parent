package com.taotao.service;

import com.taotao.common.pojo.PictureResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by haier on 2017/7/27.
 */
public interface PictureService {
    PictureResult uploadPic(MultipartFile multipartFile,String realPath);
}
