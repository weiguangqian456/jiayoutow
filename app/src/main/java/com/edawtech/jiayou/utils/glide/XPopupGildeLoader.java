package com.edawtech.jiayou.utils.glide;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.lxj.xpopup.interfaces.XPopupImageLoader;

import java.io.File;

/**
 * @author: LuoYangMian
 * @date: 2019/12/5
 * @describe: // 图片加载器，XPopup不负责加载图片，需要你实现一个图片加载器传给我，这里以Glide为例
 */
public class XPopupGildeLoader implements XPopupImageLoader {
    
    @Override
    public void loadImage(int position, @NonNull Object uri, @NonNull ImageView imageView) {

        Glide.with(imageView).load(uri).into(imageView);
    }

    @Override
    public File getImageFile(@NonNull Context context, @NonNull Object uri) {
        try {
            return Glide.with(context).downloadOnly().load(uri).submit().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
