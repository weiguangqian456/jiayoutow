package com.edawtech.jiayou.ui.custom;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edawtech.jiayou.R;


/**
 * 自定义标题TitleView
 * 选择RelativeLayout作为父类容器，目的在于RelativeLayout便于控制相对位置。
 * Created by Jarek on 2016/9/5.
 */
public class TitleView extends RelativeLayout {

    private int textColor = 0xffffff;
    private int smallTextColor = 0xffffff;

    private int marginStart = 8;
    private int marginEnd = 8;

    /**default TextView drawable-padding with dp unit*/
    private int drawablePadding = 3;
    /**min view width with dp unit*/
    private int minViewWidth = 25;
    /**min view High with dp unit*/
    private int viewHigh = 25;
    private int maxViewHigh = 45;
    /**the size of smaller text with sp unit*/
    private int defaultSmallTextSize = 14;  // 两侧文字大小
    /**the size of title text with sp unit*/
    private int defaultTitleSize = 16;  // 标题文字大小

    /*left button, if this means back,two options:1.image button;2:a textView which has drawable and text*/
    /**back to last page--TextView*/
    private TextView mLeftBackTextTv;
    /**back to last page--ImageView*/
    private ImageView mLeftBackImageTv;

    /**title*/
    private TextView mTitleTv;

    /*right button, ImageView or TextView ,only show one of them*/
    /**right ImageView*/
    private ImageView mRightImageIv;
    /**right TextView*/
    private TextView mRightTextTv;

    /*right another button, ImageView or TextView ,only show one of them*/
    /**right another ImageView*/
    private ImageView mRightImageTwoIv;
    /**right another TextView*/
    private TextView mRightTextTwoTv;


    public TitleView(Context context) {
        this(context, null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    /**
     * initview
     * @param context Context
     * @param attrs AttributeSet
     */
    private void initView (Context context, AttributeSet attrs) {
        // 首先我们要获得TypedArray对象，所有自定义属性的值通过它来获取：
        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.TitleAttr);

        // 开始添加按钮到容器中
        /*init text and image widget*/
        initLeftTextView(context, typeArray);
        initLeftImageView(context, typeArray);
        initTitleView(context, typeArray);
        initRightTextView(context, typeArray);
        initRightImageView(context, typeArray);
        initRightAnotherTextView(context, typeArray);
        initRightAnotherImageView(context, typeArray);

        // 回收
        typeArray.recycle();
    }

    /**
     * init the left TextView
     * @param context Context
     * @param typeArray TypedArray
     */
    private void initLeftTextView (Context context, TypedArray typeArray) {
        // 这里left_text就是自定义属性，表明是左侧TextView显示的文字。
        int leftText = typeArray.getResourceId(R.styleable.TitleAttr_left_text, 0);
        // 取到对应的styleable后，首先判断是否大于0，大于0表示是定义在string中的，通过typeArray.getResources().getText()获取值，等于0就直接取值，表示可能是通过直接赋值的方式给值的。
        CharSequence charSequence =  leftText > 0 ?
                typeArray.getResources().getText(leftText) : typeArray.getString(R.styleable.TitleAttr_left_text);

        // 设置宽高
        LayoutParams params = initLayoutParams();
        /*init TextView*/
        mLeftBackTextTv = createTextView(context, R.id.tv_left_text, charSequence, params);
        // 给指定的TextView设置drawable
        setTextViewDrawable(typeArray, R.styleable.TitleAttr_left_text_drawable_left, R.styleable.TitleAttr_left_text_drawable_right, mLeftBackTextTv);

        // 指定位置规则：
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        // 设置字体大小和文字颜色：
        float textSize = getDimensionPixelSize(typeArray, R.styleable.TitleAttr_small_text_size, defaultSmallTextSize);
        mLeftBackTextTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        mLeftBackTextTv.setTextColor(getSmallTextColorFromAttr(typeArray));
        // 添加创建的View.
        addView(mLeftBackTextTv);
    }

    /**
     * init the left ImageView
     * @param context Context
     * @param typeArray TypedArray
     */
    private void initLeftImageView (Context context, TypedArray typeArray) {
        int leftImgAttr = typeArray.getResourceId(R.styleable.TitleAttr_left_image, 0);

        if (leftImgAttr != 0) {
            LayoutParams params = initLayoutParams();
            int marginsRight = (int) typeArray.getDimension(R.styleable.TitleAttr_right_image_margins_right, 0);

            mLeftBackImageTv = createImageView(context, R.id.iv_left_image, leftImgAttr, marginsRight, params);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            addView(mLeftBackImageTv);
        }
    }


    /**
     * init the title TextView
     * @param context Context
     * @param typeArray TypedArray
     */
    private void initTitleView (Context context, TypedArray typeArray) {
        int leftText = typeArray.getResourceId(R.styleable.TitleAttr_title_name, 0);
        CharSequence charSequence =  leftText > 0 ?
                typeArray.getResources().getText(leftText) : typeArray.getString(R.styleable.TitleAttr_title_name);

        LayoutParams params = initLayoutParams();
        /*init TextView*/
        mTitleTv = createTextView(context, R.id.tv_title_name, charSequence, params);
        setTextViewDrawable(typeArray, R.styleable.TitleAttr_title_drawable_left, R.styleable.TitleAttr_title_drawable_right, mTitleTv);

        float textSize = getDimensionPixelSize(typeArray, R.styleable.TitleAttr_title_text_size, defaultTitleSize);
        mTitleTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        mTitleTv.setTextColor(getTextColorFromAttr(typeArray));

        int textStyle = typeArray.getInt(R.styleable.TitleAttr_title_textStyle, 0);
        if (textStyle == 1) {
            mTitleTv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
            //mTitleTv.getPaint().setFakeBoldText(true);//加粗
        } else {
            mTitleTv.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));//常规
            //mTitleTv.getPaint().setFakeBoldText(false);//常规
        }

        int gravity = typeArray.getInt(R.styleable.TitleAttr_title_gravity, 0);
        if (gravity > 0) {
            if (gravity == Gravity.LEFT) {
                params.addRule(RelativeLayout.RIGHT_OF, mLeftBackImageTv == null ? mLeftBackTextTv.getId() : mLeftBackImageTv.getId());
                params.setMarginStart((int)getPixelSizeByDp(marginStart));
            } else if (gravity == Gravity.RIGHT) {
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params.setMarginEnd((int)getPixelSizeByDp(marginEnd));
            } else {
                params.addRule(RelativeLayout.CENTER_IN_PARENT);
                // params.setMargins((int)getPixelSizeByDp(20), 0, (int)getPixelSizeByDp(20), 0);
            }
        } else { //default:center
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            // params.setMargins((int)getPixelSizeByDp(20), 0, (int)getPixelSizeByDp(20), 0);
        }

        addView(mTitleTv);
    }

    /**
     * init the right TextView
     * @param context Context
     * @param typeArray TypedArray
     */
    private void initRightTextView (Context context, TypedArray typeArray) {
        int leftText = typeArray.getResourceId(R.styleable.TitleAttr_right_text, 0);
        CharSequence charSequence =  leftText > 0 ?
                typeArray.getResources().getText(leftText) : typeArray.getString(R.styleable.TitleAttr_right_text);

        LayoutParams params = initLayoutParams();
        /*init TextView*/
        mRightTextTv = createTextView(context, R.id.tv_right_text, charSequence, params);
        setTextViewDrawable(typeArray, R.styleable.TitleAttr_right_text_drawable_left, R.styleable.TitleAttr_right_text_drawable_right, mRightTextTv);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        float textSize = getDimensionPixelSize(typeArray, R.styleable.TitleAttr_small_text_size, defaultSmallTextSize);
        mRightTextTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        mRightTextTv.setTextColor(getSmallTextColorFromAttr(typeArray));

        addView(mRightTextTv);
    }

    /**
     * init the right another TextView
     * @param context Context
     * @param typeArray TypedArray
     */
    private void initRightAnotherTextView (Context context, TypedArray typeArray) {
        int leftText = typeArray.getResourceId(R.styleable.TitleAttr_right_text_two, 0);
        CharSequence charSequence =  leftText > 0 ?
                typeArray.getResources().getText(leftText) : typeArray.getString(R.styleable.TitleAttr_right_text_two);

        LayoutParams params = initLayoutParams();
        /*init TextView*/
        mRightTextTwoTv = createTextView(context, R.id.tv_right_text_two, charSequence, params);
        setTextViewDrawable(typeArray, R.styleable.TitleAttr_right_text_two_drawable_left,  R.styleable.TitleAttr_right_text_two_drawable_right, mRightTextTwoTv);
        if (mRightImageIv != null) {
            params.addRule(RelativeLayout.LEFT_OF, mRightImageIv.getId());
        } else if (mRightTextTv != null) {
            params.addRule(RelativeLayout.LEFT_OF, mRightTextTv.getId());
        }

        float textSize = getDimensionPixelSize(typeArray, R.styleable.TitleAttr_small_text_size, defaultSmallTextSize);
        mRightTextTwoTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        mRightTextTwoTv.setTextColor(getSmallTextColorFromAttr(typeArray));

        addView(mRightTextTwoTv);
    }

    /**
     * init the right ImageView
     * @param context Context
     * @param typeArray TypedArray
     */
    private void initRightImageView (Context context, TypedArray typeArray) {
        int rightImgAttr = typeArray.getResourceId(R.styleable.TitleAttr_right_image, 0);

        if (rightImgAttr == 0) {
            return;
        }
        LayoutParams params = initLayoutParams();
        int marginsRight = (int) typeArray.getDimension(R.styleable.TitleAttr_right_image_margins_right, 0);

        mRightImageIv = createImageView(context, R.id.iv_right_image, rightImgAttr, marginsRight, params);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        addView(mRightImageIv);
    }

    /**
     * init the right ImageView
     * @param context Context
     * @param typeArray TypedArray
     */
    private void initRightAnotherImageView (Context context, TypedArray typeArray) {
        int rightImgAttr = typeArray.getResourceId(R.styleable.TitleAttr_right_image_two, 0);

        if (rightImgAttr == 0) {
            return;
        }
        LayoutParams params = initLayoutParams();
        int marginsRight = (int) typeArray.getDimension(R.styleable.TitleAttr_right_image_margins_right, 0);

        mRightImageTwoIv = createImageView(context, R.id.iv_right_image_two, rightImgAttr, marginsRight, params);

        if (mRightImageIv != null) {
            params.addRule(RelativeLayout.LEFT_OF, mRightImageIv.getId());
        } else if (mRightTextTv != null) {
            params.addRule(RelativeLayout.LEFT_OF, mRightTextTv.getId());
        }
        addView(mRightImageTwoIv);
    }


    /**
     * 设置宽高
     * layout params of RelativeLayout
     * @return LayoutParams
     */
    private LayoutParams initLayoutParams () {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
    }


    /**
     * drawable of TextView
     * @param typeArray TypedArray
     * @param leftDrawableStyleable leftDrawableStyleable
     * @param rightDrawableStyleable rightDrawableStyleable
     * @param textView which TextView to set
     */
    private void setTextViewDrawable(TypedArray typeArray, int leftDrawableStyleable, int rightDrawableStyleable, TextView textView) {
        int leftDrawable = typeArray.getResourceId(leftDrawableStyleable, 0);
        int rightDrawable = typeArray.getResourceId(rightDrawableStyleable, 0);
        textView.setCompoundDrawablePadding((int)getPixelSizeByDp(drawablePadding));
        textView.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, 0, rightDrawable, 0);

    }

    /**
     * create TextView
     * @param context Context
     * @param id the id of TextView
     * @param charSequence text to show
     * @param params relative params
     * @return the TextView which is inited
     */
    private TextView createTextView(Context context, int id, CharSequence charSequence, LayoutParams params) {
        TextView textView = new TextView(context);
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER);
        textView.setId(id);
        textView.setMinWidth((int)getPixelSizeByDp(minViewWidth));
        textView.setMinHeight((int)getPixelSizeByDp(viewHigh));
        textView.setText(charSequence);
        return textView;
    }

    /**
     * create ImageView
     * @param context Context
     * @param id the id of ImageView
     * @param drawable the resource of imageView
     * @param params relative params
     * @return ImageView
     */
    private ImageView createImageView (Context context, int id, int drawable, int marginsRight, LayoutParams params) {
        int padding = (int)getPixelSizeByDp((maxViewHigh - viewHigh) / 2);
        params.height = (int)getPixelSizeByDp(maxViewHigh);
        params.setMargins(0, 0, (int)getPixelSizeByDp(marginsRight), 0);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setId(id);
        imageView.setImageResource(drawable);
        if (id == R.id.iv_left_image || id == R.id.iv_right_image) {
            imageView.setMinimumWidth((int)getPixelSizeByDp(maxViewHigh));
            imageView.setPadding(padding, padding, padding, padding);
        } else {
            imageView.setMinimumWidth((int)getPixelSizeByDp(viewHigh));
            imageView.setPadding(0, padding, 0, padding);
        }
        return imageView;
        //imageView.setBackgroundColor(R.color.colorLinesLight);
    }

    /**
     * get Pixel size by dp
     * @param dp dp
     * @return the value of px
     */
    private float getPixelSizeByDp(int dp) {
        Resources res = this.getResources();
        final float scale = res.getDisplayMetrics().density;
        return  dp * scale + 0.5f;
    }

    /**
     * get Pixel size by sp
     * @param sp sp
     * @return the value of px
     */
    private float getPiselSizeBySp (int sp) {
        Resources res = this.getResources();
        final float scale = res.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    /**
     * get the dimension pixel size from typeArray which is defined in attr
     * @param typeArray TypedArray
     * @param stylable stylable
     * @param defaultSize defaultSize
     * @return the dimension pixel size
     */
    private float getDimensionPixelSize(TypedArray typeArray, int stylable, int defaultSize) {
        int sizeStyleable = typeArray.getResourceId(stylable, 0);
        return sizeStyleable > 0 ? typeArray.getResources().getDimensionPixelSize(sizeStyleable) : typeArray.getDimensionPixelSize(stylable, (int)getPiselSizeBySp(defaultSize));
    }


    /**
     * get textColor
     * @param typeArray TypedArray
     * @return textColor
     */
    private int getTextColorFromAttr (TypedArray typeArray) {
        int textColorStyleable = typeArray.getResourceId(R.styleable.TitleAttr_title_text_color, 0);
        if (textColorStyleable > 0) {
            return typeArray.getResources().getColor(textColorStyleable);
        } else {
            return typeArray.getColor(R.styleable.TitleAttr_title_text_color, textColor);
        }
    }

    /**
     * get smallTextColor
     * @param typeArray TypedArray
     * @return smallTextColor
     */
    private int getSmallTextColorFromAttr (TypedArray typeArray) {
        int textColorStyleable = typeArray.getResourceId(R.styleable.TitleAttr_small_text_color, 0);
        if (textColorStyleable > 0) {
            return typeArray.getResources().getColor(textColorStyleable);
        } else {
            return typeArray.getColor(R.styleable.TitleAttr_small_text_color, smallTextColor);
        }
    }

    /**
     * set TextView's drawable padding
     * @param drawablePadding drawablePadding
     */
    public void setTextViewDrawablePadding(int drawablePadding) {
        this.drawablePadding = drawablePadding;
    }

    public TextView getLeftBackTextTv() {
        return mLeftBackTextTv;
    }

    public ImageView getLeftBackImageTv() {
        return mLeftBackImageTv;
    }

    public TextView getTitleTv() {
        return mTitleTv;
    }

    public ImageView getRightImageIv() {
        return mRightImageIv;
    }

    public TextView getRightTextTv() {
        return mRightTextTv;
    }

    public ImageView getRightImageTwoIv() {
        return mRightImageTwoIv;
    }

    public TextView getRightTextTwoTv() {
        return mRightTextTwoTv;
    }
}
