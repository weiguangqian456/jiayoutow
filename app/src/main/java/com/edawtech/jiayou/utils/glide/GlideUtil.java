package com.edawtech.jiayou.utils.glide;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.edawtech.jiayou.R;
import com.edawtech.jiayou.config.base.MyApplication;
import com.edawtech.jiayou.utils.tool.unit.DensityUtil;


import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Administrator on 2019/04/22 0022.
 * 图片加载框架 Glide4.x
 * Glide加载工具类：
 */
public class GlideUtil {

    private final static String http = "http://";
    private final static String https = "https://";

    public static boolean isUrlHttp(String image_Url) {
        if (!TextUtils.isEmpty(image_Url)) {
            boolean bool = image_Url.startsWith(http);// 判断字符串是否以"http://"开头
            boolean bool_s = image_Url.startsWith(https);// 判断字符串是否以"https://"开头
            if (bool || bool_s) {
                return true;
            }
            return false;
        }
        return false;
    }

    public static String UrlHttp(String image_Url) {
        if (!TextUtils.isEmpty(image_Url)) {
            if (isUrlHttp(image_Url)) {
                return image_Url;
            } else {
                return http + image_Url;
            }
        }
        return "";
    }

    // 正常图片加载
    public static void load(Context context, String image_Url, ImageView imageView ) {
        // http判断。
        String url = UrlHttp(image_Url);
        if (!TextUtils.isEmpty(url)) {
            // android listview使用glide异步加载图片错位，闪烁问题。
            imageView.setTag(R.id.imageloader_uri, url);
            // 调用Glide.with()方法用于创建一个加载图片的实例。
            Glide.with(context)
                    // 只允许加载静态图片(在Glide 4中是先asBitmap()再load()的)。
                    // .asBitmap()
                    // 只允许加载动态图片（只能二选一或者都不选，默认Glide都会自动进行判断）
                    // .asGif()
                    // 指定待加载的图片资源，使用自定义的MyGlideUrl类.
                    .load(new MyGlideUrl(url))
                    // apply()方法，引用RequestOptions对象。
                    .apply(requestOptions())
                    // 让图片显示在哪个ImageView上
                    // .into(imageView);
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            // 获取图像控件的Tag
                            String tag = (String) imageView.getTag(R.id.imageloader_uri);
                            // 如果一样，显示图片
                            if (TextUtils.equals(url, tag)) {
                                imageView.setImageDrawable(resource);
                            }
                        }
                    });
        } else {
            imageView.setImageResource(R.drawable.wait_bg);
        }
    }
    public static void load(Context context, String image_Url, ImageView imageView , int Rimagview ) {
        // http判断。
        String url = UrlHttp(image_Url);
        if (!TextUtils.isEmpty(url)) {
            // android listview使用glide异步加载图片错位，闪烁问题。
            imageView.setTag(R.id.imageloader_uri, url);
            // 调用Glide.with()方法用于创建一个加载图片的实例。
            Glide.with(context)
                    // 只允许加载静态图片(在Glide 4中是先asBitmap()再load()的)。
                    // .asBitmap()
                    // 只允许加载动态图片（只能二选一或者都不选，默认Glide都会自动进行判断）
                    // .asGif()
                    // 指定待加载的图片资源，使用自定义的MyGlideUrl类.
                    .load(new MyGlideUrl(url))
                    // apply()方法，引用RequestOptions对象。
                    .apply(requestOptions())
                    // 让图片显示在哪个ImageView上
                    // .into(imageView);
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            // 获取图像控件的Tag
                            String tag = (String) imageView.getTag(R.id.imageloader_uri);
                            // 如果一样，显示图片
                            if (TextUtils.equals(url, tag)) {
                                imageView.setImageDrawable(resource);
                            }
                        }
                    });
        } else {
            imageView.setImageResource(Rimagview);
        }
    }

    // 自定义图片加载
    public static void load(Activity context, String image_Url, ImageView imageView, RequestOptions options) {
        // http判断。
        String url = UrlHttp(image_Url);
        if (!TextUtils.isEmpty(url)&& !context.isDestroyed()) {
            // android listview使用glide异步加载图片错位，闪烁问题。
            imageView.setTag(R.id.imageloader_uri, url);
            Glide.with(context)
                    // 指定待加载的图片资源，使用自定义的MyGlideUrl类.
                    .load(new MyGlideUrl(url))
                    .apply(options)
                    // .into(imageView);
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            // 获取   图像控件的Tag
                            String tag = (String) imageView.getTag(R.id.imageloader_uri);
                            // 如果一样，显示图片
                            if (TextUtils.equals(url, tag)) {
                                imageView.setImageDrawable(resource);
                            }
                        }
                    });
        } else {
            imageView.setImageResource(R.drawable.wait_bg);
        }
    }

    // 自定义本地图片加载
    public static void load(Context context, int url, ImageView imageView, RequestOptions options) {
        if (url >= 0) {
            Glide.with(context)
                    // 指定待加载的本地图片资源.
                    .load(url)
                    .apply(options)
                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.wait_bg);
        }
    }

    /**
     * 正常图片请求。
     * @return
     */
    // Glide 4中引入了一个RequestOptions对象，将这一系列的API都移动到了RequestOptions当中。
    private static RequestOptions requestOptions() {
        return new RequestOptions()
                // 占位图显示
                .placeholder(R.drawable.wait_bg)
                // url为空的时候,显示的图片
                .fallback(R.drawable.wait_bg)
                // 异常占位图
                .error(R.drawable.wait_bg)
                // 使用placeholder(占位符)第一次加载不显示图片问题
                .dontAnimate()
                // 禁用掉Glide的内存缓存功能。
                // .skipMemoryCache(true)
                // 禁用掉Glide的硬盘缓存功能
                // .diskCacheStrategy(DiskCacheStrategy.NONE)
                // 只缓存原始图片。
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                // 使用override()方法指定了一个图片的尺寸，将图片尺寸指定成原始大小Target.SIZE_ORIGINAL。
                // .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                ;
    }

    /**
     * 圆形图片。(自定义)
     * @return
     */
    public static RequestOptions cropOptions() {
//        return new RequestOptions()
//                // 占位图显示
//                .placeholder(R.drawable.wait_bg)
//                // 异常占位图
//                .error(R.drawable.wait_bg)
//                // 只缓存原始图片。
//                .diskCacheStrategy(DiskCacheStrategy.DATA)
//                // 圆形图片。
//                .transform(new CircleCrop());
        return RequestOptions.bitmapTransform(new CircleCrop())
                .placeholder(R.drawable.wait_bg)
                .error(R.drawable.wait_bg);
    }

    /**
     * 原图 -> 圆图
     * @return
     */
    public static RequestOptions cropCircleOptions() {
//        return new RequestOptions()
//                // 占位图显示
//                .placeholder(R.drawable.wait_bg)
//                // 异常占位图
//                .error(R.drawable.wait_bg)
//                // 只缓存原始图片。
//                .diskCacheStrategy(DiskCacheStrategy.DATA)
//                // 圆形图片。
//                .transform(new CropCircleTransformation());
        return RequestOptions.bitmapTransform(new CropCircleTransformation())
                .placeholder(R.drawable.wait_bg)
                .error(R.drawable.wait_bg);
    }

    /**
     * 原图处理成圆角，
     * 如果是四周都是圆角则是RoundedCornersTransformation.CornerType.ALL
     * @return
     */
    public static RoundedCornersTransformation.CornerType ALL = RoundedCornersTransformation.CornerType.ALL;
    public static RoundedCornersTransformation.CornerType BOTTOM = RoundedCornersTransformation.CornerType.BOTTOM;
    public static RoundedCornersTransformation.CornerType TOP = RoundedCornersTransformation.CornerType.TOP;
    public static RoundedCornersTransformation.CornerType LEFT = RoundedCornersTransformation.CornerType.LEFT;
    public static RoundedCornersTransformation.CornerType RIGHT = RoundedCornersTransformation.CornerType.RIGHT;
    // 将图片裁剪成圆角矩形
    public static RequestOptions roundedCornersOptions(int radius, int margin, RoundedCornersTransformation.CornerType cornerType) {
        return RequestOptions
                // 对图片进行圆角化处理。
                /**
                 * 对图片进行圆角化处理，第一个参数越大圆角越大。
                 * 圆角图片 new RoundedCornersTransformation 参数为 ：半径 , 外边距 , 圆角方向(ALL,BOTTOM,TOP,RIGHT,LEFT,BOTTOM_LEFT等等)
                 */
                .bitmapTransform(new RoundedCornersTransformation(
                        DensityUtil.dip2px(MyApplication.getContext(), radius),
                        DensityUtil.dip2px(MyApplication.getContext(), margin), cornerType))
                .placeholder(R.drawable.wait_bg)
                .error(R.drawable.wait_bg);
    }

}
