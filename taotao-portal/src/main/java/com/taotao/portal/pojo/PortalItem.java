package com.taotao.portal.pojo;

import com.taotao.pojo.TbItem;

/**
 * Created by haier on 2017/8/2.
 */
public class PortalItem extends TbItem {
    public String[] getImages() {
        String images = this.getImage();
        if (images != null && !images.equals("")) {
            String[] imgs = images.split(",");
            return imgs;
        }
        return null;
    }
}
