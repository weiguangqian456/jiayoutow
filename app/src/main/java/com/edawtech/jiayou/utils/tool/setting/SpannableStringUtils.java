package com.edawtech.jiayou.utils.tool.setting;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.View;

/**
 * @ProjectName: LeXun
 * @Package: com.mto.lexun.utils.control
 * @ClassName: SpannableStringUtils
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2019/12/31 11:06
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/12/31 11:06
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 *
 * Android TextView中文字通过SpannableString来设置超链接、颜色、字体等属性。
 */
public class SpannableStringUtils {
    /**
     * setSpan(Object what, int start, int end, int flags)方法需要用户输入四个参数，
     * what表示设置的格式是什么，可以是前景色、背景色也可以是可点击的文本等等，
     * start表示需要设置格式的子字符串的起始下标，
     * 同理end表示终了下标，
     * flags属性就有意思了，共有四种属性：
     *     Spanned.SPAN_INCLUSIVE_EXCLUSIVE 从起始下标到终了下标，包括起始下标
     *     Spanned.SPAN_INCLUSIVE_INCLUSIVE 从起始下标到终了下标，同时包括起始下标和终了下标
     *     Spanned.SPAN_EXCLUSIVE_EXCLUSIVE 从起始下标到终了下标，但都不包括起始下标和终了下标
     *     Spanned.SPAN_EXCLUSIVE_INCLUSIVE 从起始下标到终了下标，包括终了下标
     */

    private static SpannableStringUtils instance;// 入口操作管理

    /**
     * 单例方式获取 SpannableString 对象
     */
    public static SpannableStringUtils getInstance() {
        if (instance == null) {
            synchronized (SpannableStringUtils.class) {
                if (instance == null) {
                    instance = new SpannableStringUtils();
                }
            }
        }
        return instance;
    }

    private SpannableStringUtils() {
    }

    /**
     * 获取 SpannableString 对象，可进行相关配置的修改。
     */
    public SpannableString config(CharSequence source) {
        // 创建一个 SpannableString 对象
        return new SpannableString(source);
    }

    /**
     * ForegroundColorSpan，为文本设置前景色，效果和TextView的setTextColor()类似
     */
    // 0xff0099EE
    public SpannableString setColor(SpannableString spannableString, int color, int startIndex, int endIndex) {
        spannableString.setSpan(new ForegroundColorSpan(color), startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
    // "#ff0099EE"
    public SpannableString setColor(SpannableString spannableString, String colorString, int startIndex, int endIndex) {
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(colorString)), startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * BackgroundColorSpan，为文本设置背景色，效果和TextView的setBackground()类似
     */
    // 0xff0099EE
    public SpannableString setBackgroundColor(SpannableString spannableString, int color, int startIndex, int endIndex) {
        spannableString.setSpan(new BackgroundColorSpan(color), startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
    // "#ff0099EE"
    public SpannableString setBackgroundColor(SpannableString spannableString, String colorString, int startIndex, int endIndex) {
        spannableString.setSpan(new BackgroundColorSpan(Color.parseColor(colorString)), startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * AbsoluteSizeSpan，设置字体大小（绝对值,单位：像素）
     */
    // 20
    public SpannableString setSizePx(SpannableString spannableString, int textSize, int startIndex, int endIndex) {
        spannableString.setSpan(new AbsoluteSizeSpan(textSize), startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
    // 20 // 第二个参数boolean dip，如果为true，表示前面的字体大小单位为dip，否则为像素，同上。
    public SpannableString setSizeDip(SpannableString spannableString, int textSize, int startIndex, int endIndex) {
        spannableString.setSpan(new AbsoluteSizeSpan(textSize, true), startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * RelativeSizeSpan，设置字体大小（相对值,单位：像素） 参数表示为默认字体大小的多少倍。
     */
    // 1.2f
    public SpannableString setSizePx(SpannableString spannableString, float proportion, int startIndex, int endIndex) {
        spannableString.setSpan(new RelativeSizeSpan(proportion), startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * ScaleXSpan，设置字体大小（相对值,单位：像素） 参数表示为默认字体宽度的多少倍。
     */
    // 2.0f表示默认字体宽度的两倍，即X轴方向放大为默认字体的两倍，而高度不变
    public SpannableString setSizeScaleXPx(SpannableString spannableString, float proportion, int startIndex, int endIndex) {
        spannableString.setSpan(new ScaleXSpan(proportion), startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * StrikethroughSpan，为文本设置中划线，也就是常说的删除线
     */
    public SpannableString setLink(SpannableString spannableString, int startIndex, int endIndex) {
        spannableString.setSpan(new StrikethroughSpan(), startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * UnderlineSpan，为文本设置下划线
     */
    public SpannableString setUnderLine(SpannableString spannableString, int startIndex, int endIndex) {
        spannableString.setSpan(new UnderlineSpan(), startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * StyleSpan，为文字设置风格（粗体、斜体），和TextView属性textStyle类似
     */
    // android.graphics.Typeface.NORMAL 正常 // Typeface.BOLD 粗体 // Typeface.ITALIC 斜体 // Typeface.BOLD_ITALIC 粗斜体。
    public SpannableString setStyle(SpannableString spannableString, int style, int startIndex, int endIndex) {
        spannableString.setSpan(new StyleSpan(style), startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * ImageSpan，设置文本图片
     */
    // Drawable drawable = getResources().getDrawable(R.mipmap.a9c);  drawable.setBounds(0, 0, 42, 42);
    public SpannableString setImage(SpannableString spannableString, Drawable drawable, int startIndex, int endIndex) {
        spannableString.setSpan(new ImageSpan(drawable), startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * ClickableSpan，设置可点击的文本，设置这个属性的文本可以相应用户点击事件，至于点击事件用户可以自定义，用户可以实现点击跳转页面的效果。
     *
     * 注意：使用ClickableSpan的文本如果想真正实现点击作用，必须为TextView设置setMovementMethod方法，否则没有点击相应，
     * 至于setHighlightColor方法则是控件点击时的背景色。
     *     textView.setMovementMethod(LinkMovementMethod.getInstance());// 开始响应点击事件
     *     textView.setHighlightColor(Color.parseColor("#36969696"));// 控件点击时的背景色
     */
    public SpannableString setClickable(SpannableString spannableString, ClickableSpan clickableSpan, int startIndex, int endIndex) {
        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
    // 自定义点击事件监听。
    class MyClickableSpan extends ClickableSpan {

        private String content;

        public MyClickableSpan(String content) {
            this.content = content;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Color.WHITE);       //设置文件颜色
            ds.setUnderlineText(true);      //设置下划线
            // ds.setUnderlineText(false);
        }

        @Override
        public void onClick(View widget) {
            //            Intent intent = new Intent(MainActivity.this, OtherActivity.class);
            //            Bundle bundle = new Bundle();
            //            bundle.putString("content", content);
            //            intent.putExtra("bundle", bundle);
            //            startActivity(intent);
        }
    }

    /**
     * URLSpan，设置超链接文本。
     *
     * 注意：使用URLSpan的文本如果想真正实现点击作用，必须为TextView设置setMovementMethod方法，否则没有点击相应，
     * 至于setHighlightColor方法则是控件点击时的背景色。
     *     textView.setMovementMethod(LinkMovementMethod.getInstance());// 开始响应点击事件
     *     textView.setHighlightColor(Color.parseColor("#36969696"));// 控件点击时的背景色
     */
    public SpannableString setURL(SpannableString spannableString, String url, int startIndex, int endIndex) {
        spannableString.setSpan(new URLSpan(url), startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    // 示例：
    public void dsf() {
        //        SpannableString spannableString = new SpannableString("为文字设置超链接");
        //        URLSpan urlSpan = new URLSpan("http://www.jianshu.com/users/dbae9ac95c78");
        //        spannableString.setSpan(urlSpan, 5, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        //        textView.setMovementMethod(LinkMovementMethod.getInstance());
        //        textView.setHighlightColor(Color.parseColor("#36969696"));
        //        textView.setText(spannableString);

        //        SpannableString spannableString = new SpannableString("为文字设置点击事件");
        //        MyClickableSpan clickableSpan = new MyClickableSpan("http://www.jianshu.com/users/dbae9ac95c78");
        //        spannableString.setSpan(clickableSpan, 5, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        //        textView.setMovementMethod(LinkMovementMethod.getInstance());
        //        textView.setHighlightColor(Color.parseColor("#36969696"));
        //        textView.setText(spannableString);


        //        SpannableString spannableString = new SpannableString("在文本中添加表情（表情）");
        //        Drawable drawable = getResources().getDrawable(R.mipmap.a9c);
        //        drawable.setBounds(0, 0, 42, 42);
        //        ImageSpan imageSpan = new ImageSpan(drawable);
        //        spannableString.setSpan(imageSpan, 6, 8, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        //        textView.setText(spannableString);


        //        SpannableString spannableString = new SpannableString("为文字设置粗体、斜体风格");
        //        StyleSpan styleSpan_B  = new StyleSpan(Typeface.BOLD);
        //        StyleSpan styleSpan_I  = new StyleSpan(Typeface.ITALIC);
        //        spannableString.setSpan(styleSpan_B, 5, 7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        //        spannableString.setSpan(styleSpan_I, 8, 10, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        //        textView.setHighlightColor(Color.parseColor("#36969696"));
        //        textView.setText(spannableString);


        //        SpannableString spannableString = new SpannableString("为文字设置下划线");
        //        UnderlineSpan underlineSpan = new UnderlineSpan();
        //        spannableString.setSpan(underlineSpan, 5, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        //        textView.setText(spannableString);


        //        SpannableString spannableString = new SpannableString("为文字设置删除线");
        //        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        //        spannableString.setSpan(strikethroughSpan, 5, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        //        textView.setText(spannableString);


        //        SpannableString spannableString = new SpannableString("设置文字的前景色为淡蓝色");
        //        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#0099EE"));
        //        spannableString.setSpan(colorSpan, 9, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        //        textView.setText(spannableString);

        //        SpannableString spannableString = new SpannableString("设置文字的背景色为淡绿色");
        //        BackgroundColorSpan colorSpan = new BackgroundColorSpan(Color.parseColor("#AC00FF30"));
        //        spannableString.setSpan(colorSpan, 9, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        //        textView.setText(spannableString);

        //        SpannableString spannableString = new SpannableString("万丈高楼平地起");
        //        RelativeSizeSpan sizeSpan07 = new RelativeSizeSpan(1.2f);
        //        spannableString.setSpan(sizeSpan07, 6, 7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        //        textView.setText(spannableString);
    }

}
