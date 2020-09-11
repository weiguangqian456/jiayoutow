package com.edawtech.jiayou.utils.edit;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 点击EditText文本框之外任何地方隐藏键盘的解决办法
 * @author y
 */
public class EditTextHideAndShowKeyboardUtils {

	/**
	 * 一般的隐藏软键盘的方法为：
	 * @param context
	 * @param v
	 */
	public static void hideKeyboard(Context context, View v) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm != null) {
			imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
		}
	}
	
	/**
	 * 一般的显示软键盘的方法为：
	 * @param context
	 * @param v
	 */
	public static void showKeyboard(Context context, View v) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm != null) {  
			imm.showSoftInput(v, 0);
		} 
	}
	
	/**
	 * 方法一：监听EditText本身区域之外的点击事件，判断虚拟键盘隐藏和显示以及处理。
	 * 在activity中添加如下方法来处理点击事件。
	 */
//	/**
//	 * 获取点击事件:
//	 * 通过dispatchTouchEvent每次ACTION_DOWN事件中动态判断非EditText本身区域的点击事件，然后在事件中进行屏蔽。
//	 */
//	@Override
//	public boolean dispatchTouchEvent(MotionEvent ev) {
//		// TODO Auto-generated method stub
//		if (ev.getAction() == MotionEvent.ACTION_DOWN) {  
//			// 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
//			View v = getCurrentFocus();  
//			if (EditTextHideAndShowKeyboardUtils.isShouldHideInput(v, ev)) {  
//				// 隐藏软键盘
//				EditTextHideAndShowKeyboardUtils.hideKeyboard(this, v);
//			}  
//			return super.dispatchTouchEvent(ev);  
//		}  
//		// 必不可少，否则所有的组件都不会有TouchEvent了  
//		if (getWindow().superDispatchTouchEvent(ev)) {  
//			return true;  
//		}  
//		return onTouchEvent(ev);
//	}
	
	// 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
	public static boolean isShouldHideInput(View v, MotionEvent event) {
		if (v != null && (v instanceof EditText)) {
			int[] leftTop = { 0, 0 };  
			//获取输入框当前的location位置  
			v.getLocationInWindow(leftTop);  
			int left = leftTop[0];  
			int top = leftTop[1];  
			int bottom = top + v.getHeight();  
			int right = left + v.getWidth();  
			if (event.getX() > left && event.getX() < right  
					&& event.getY() > top && event.getY() < bottom) {  
				// 点击的是输入框区域，保留点击EditText的事件 (点击EditText的事件，忽略它)。
				return false;  
			} else {  
				return true;  
			}  
		}  
		// 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
		return false;  
	} 
	
	/**
	 * 方法一：监听虚拟键盘本身的点击事件，判断虚拟键盘隐藏和显示以及处理。
	 * 在activity中添加如下方法来处理:监听虚拟键盘隐藏和显示事件.
	 */
//	// 监听虚拟键盘隐藏和显示事件:该Activity的最外层Layout,给该layout设置监听，监听其布局发生变化事件.
//	activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener(){
//		
//		@Override
//		public void onGlobalLayout(){
//			
//			if (isKeyboardShown(activityRootView.getRootView())) {
//				// 虚拟键盘显示
//			} else {
//				// 虚拟键盘隐藏
//			}
//		}
//	});
			
	/**
	 * 计算根布局的底部空隙:
	 * 其实所有的方法都是为了发现软键盘对布局的影响，从而判断软键盘的显示和隐藏。
	 * 还有一种方法就判断根布局的可视区域与屏幕底部的差值，如果这个差大于某个值，可以认定键盘弹起了。
	 * @param rootView
	 * @return
	 */
	public static boolean isKeyboardShown(View rootView) {
		// 得到的Rect就是根布局的可视区域，而rootView.bottom是其本应的底部坐标值，如果差值大于我们预设的值，就可以认定键盘弹起了。
		// 这个预设值是键盘的高度的最小值。这个rootView实际上就是DectorView，通过任意一个View再getRootView就能获得。
		final int softKeyboardHeight = 100;
		Rect r = new Rect();
		rootView.getWindowVisibleDisplayFrame(r);
		DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
		int heightDiff = rootView.getBottom() - r.bottom;
		return heightDiff > softKeyboardHeight * dm.density;
	}


	/**
	 * EditText获取焦点并显示软键盘
	 */
	public static void showSoftInputFromWindow(Activity activity, EditText editText) {
		editText.setFocusable(true);
		editText.setFocusableInTouchMode(true);
		editText.requestFocus();
		activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
	}


	/**
	 * 强制隐藏软键盘
	 * @param activity
	 */
	public static void hideSoftKeyboard(Activity activity) {
		try {
			InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0); //强制隐藏键盘
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
