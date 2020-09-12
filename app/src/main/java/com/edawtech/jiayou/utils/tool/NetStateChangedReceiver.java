/**
 * Copyright 2014 eTao Tech. Inc. LTD. All rights reserved.
 * - Powered by Team GOF. -
 */

package com.edawtech.jiayou.utils.tool;

import android.content.BroadcastReceiver;

/**
 * @ClassName: NetStateChangedReceiver
 * @Description: 用一句话描述该文件做什么
 * @author jinsongliu 
 * @date 2015-03-15 下午5:35:36
 * 
*/

public abstract class NetStateChangedReceiver extends BroadcastReceiver {

//	@Override
//	public void onReceive(Context context, Intent intent) {
//	
//	}

	public abstract void handleOnNetStateChanged();
}
