package com.edawtech.jiayou.softphone;

import android.content.Context;
import android.media.AudioManager;

import com.edawtech.jiayou.config.base.MyApplication;
import com.edawtech.jiayou.config.constant.VsUserConfig;


/**
 * �������ڱ���ϵͳMedia�����뻹ԭ
 */
public class SystemMediaConfig {

	/**
	 * ��ʼϵͳMedia����
	 */
	public static void initMediaConfig(AudioManager am) {
		if (am == null) {
			am = (AudioManager) MyApplication.getContext().getSystemService(Context.AUDIO_SERVICE);
		}
		VsUserConfig.setData(MyApplication.getContext(), VsUserConfig.JKEY_MEDIA_MODE, am.getMode());
		VsUserConfig.setData(MyApplication.getContext(), VsUserConfig.JKEY_MEDIA_SPEAKERON, am.isSpeakerphoneOn());
		VsUserConfig.setData(MyApplication.getContext(), VsUserConfig.JKEY_MEDIA_RINGERMODE, am.getRingerMode());
	}

	/**
	 * ��ԭMedia����
	 */
	public static void restoreMediaConfig(AudioManager am) {
		if (am == null) {
			am = (AudioManager) MyApplication.getInstance().getApplicationContext()
					.getSystemService(Context.AUDIO_SERVICE);
		}
		int mode = VsUserConfig.getDataInt(MyApplication.getContext(), VsUserConfig.JKEY_MEDIA_MODE);
		int ringerMode = VsUserConfig.getDataInt(MyApplication.getContext(), VsUserConfig.JKEY_MEDIA_RINGERMODE);
		boolean isPpeakerphoneOn = VsUserConfig.getDataBoolean(MyApplication.getContext(),
				VsUserConfig.JKEY_MEDIA_SPEAKERON, true);

		am.setSpeakerphoneOn(isPpeakerphoneOn);
		am.setMode(mode);
		// am.setRingerMode(ringerMode);
	}
}
