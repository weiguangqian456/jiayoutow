package com.edawtech.jiayou.utils.sound;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.SoundPool;
import android.util.Log;

import com.edawtech.jiayou.R;


// 简单的设置点击音效
public class SoundPoolUtil {

    private static SoundPoolUtil soundPoolUtil;
    private SoundPool soundPool;
    private int soundID;

    //单例模式
    public static SoundPoolUtil getInstance(Context context) {
        if (soundPoolUtil == null) {
            soundPoolUtil = new SoundPoolUtil(context);
        }
        return soundPoolUtil;
    }

    //这里初始化SoundPool的方法是安卓5.0以后提供的新方式
    @SuppressLint("NewApi")
    private SoundPoolUtil(Context context) {
        //soundPool = new SoundPool(3, AudioManager.STREAM_SYSTEM, 0); 
        soundPool = new SoundPool.Builder().build();
        //加载音频文件
        soundID = soundPool.load(context, R.raw.scan, 1);
    }

    public void playSound() {
        Log.d("tag", "soundID " + soundID);
        //播放音频
        soundPool.play(soundID, 1, 1, 0, 0, 1);
    }

    public void playSound(int soundID) {
        Log.d("tag", "soundID " + soundID);
        //播放音频
        //soundPool.play(soundID, 1, 1, 0, 0, 1);
        soundPool.play(
                soundID,
                1,      //左耳道音量【0~1】
                1,     //右耳道音量【0~1】
                0,         //播放优先级【0表示最低优先级】
                0,           //循环模式【0表示循环一次，-1表示一直循环，其他表示数字+1表示当前数字对应的循环次数】
                1            //播放速度【1是正常，范围从0~2】
        );
    }

}
