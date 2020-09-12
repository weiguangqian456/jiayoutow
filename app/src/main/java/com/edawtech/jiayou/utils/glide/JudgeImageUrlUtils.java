package com.edawtech.jiayou.utils.glide;


import static com.edawtech.jiayou.config.base.Const.BASE_IMAGE_URL;

/**
 * @author Created by EDZ on 2018/7/27.
 *         判断图片是否合法
 */

public class JudgeImageUrlUtils {
    public static String isAvailable(String url) {
        if (url != null && !url.contains("http")) {
            url = BASE_IMAGE_URL + url;
        }
        return url;
    }
}

