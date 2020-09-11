package com.edawtech.jiayou.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.widget.TextView
import com.flyco.roundview.RoundViewDelegate


@SuppressLint("AppCompatCustomView")
class RoundTextView() : TextView(), Parcelable {
    private var delegate: RoundViewDelegate? = null

    constructor(parcel: Parcel) : this() {

    }

    fun RoundTextView(context: Context?) {
        this(context, null)
    }

    fun RoundTextView(context: Context?, attrs: AttributeSet?) {
        this(context, attrs, 0)
    }

    fun RoundTextView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) {
        super(context, attrs, defStyleAttr)
        delegate = RoundViewDelegate(this, context, attrs)
    }

    /** use delegate to set attr  */
    fun getDelegate(): RoundViewDelegate? {
        return delegate
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (delegate.isWidthHeightEqual() && width > 0 && height > 0) {
            val max = Math.max(width, height)
            val measureSpec = MeasureSpec.makeMeasureSpec(max, MeasureSpec.EXACTLY)
            super.onMeasure(measureSpec, measureSpec)
            return
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (delegate.isRadiusHalfHeight()) {
            delegate.setCornerRadius(height / 2)
        } else {
            delegate.setBgSelector()
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RoundTextView> {
        override fun createFromParcel(parcel: Parcel): RoundTextView {
            return RoundTextView(parcel)
        }

        override fun newArray(size: Int): Array<RoundTextView?> {
            return arrayOfNulls(size)
        }
    }

}