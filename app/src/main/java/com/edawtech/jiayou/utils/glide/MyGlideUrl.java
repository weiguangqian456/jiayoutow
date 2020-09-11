package com.edawtech.jiayou.utils.glide;

import com.bumptech.glide.load.model.GlideUrl;

/**
 * Created by Administrator on 2017/12/21 0021.
 *
 * 解决类似如：图片资源都是存放在七牛云上面的，而七牛云为了对图片资源进行保护，
 * 会在图片url地址的基础之上再加上一个token参数。oken作为一个验证身份的参数并不是一成不变的，
 * 很有可能时时刻刻都在变化。结果就造成了，明明是同一张图片，就因为token不断在改变，导致Glide的缓存功能完全失效了。
 * 如：http://url.com/image.jpg?token=d9caa6e02c990b0a
 *
 * 解决方法：因为getCacheKey()方法中的逻辑太直白了，直接就是将图片的url地址进行返回来作为缓存Key的。
 * 那么其实我们只需要重写这个  getCacheKey() 方法，加入一些自己的逻辑判断，就能轻松解决掉刚才的问题了。
 *
 * 创建一个MyGlideUrl继承自GlideUrl：
 *
 * 使用：在load()方法中传入这个自定义的MyGlideUrl对象，而不能再像之前那样直接传入url字符串了。
 *  .load(new MyGlideUrl(url))
 */

public class MyGlideUrl extends GlideUrl {

    private String mUrl;

    public MyGlideUrl(String url) {
        super(url);
        mUrl = url;
    }

    /**
     * 重写了getCacheKey()方法.
     * @return
     */
    @Override
    public String getCacheKey() {
        // return super.getCacheKey();
        return mUrl.replace(findTokenParam(), "");
    }

    /**
     * 将图片url地址中token参数的这一部分移除掉。
     * @return
     */
    private String findTokenParam() {
        String tokenParam = "";
        int tokenKeyIndex = mUrl.indexOf("?token=") >= 0 ? mUrl.indexOf("?token=") : mUrl.indexOf("&token=");
        if (tokenKeyIndex != -1) {
            // 找到token=后面的下一个&amp;符号，这样就能把token=xxxxx这段内容给截取出来.
            int nextAndIndex = mUrl.indexOf("&", tokenKeyIndex + 1);
            if (nextAndIndex != -1) {
                tokenParam = mUrl.substring(tokenKeyIndex + 1, nextAndIndex + 1);
            } else {
                tokenParam = mUrl.substring(tokenKeyIndex);
            }
        }
        return tokenParam;
    }

}
