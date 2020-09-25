package com.edawtech.jiayou.utils;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;

import androidx.annotation.RequiresApi;

/**
 * ClassName:      AppIconHelper
 * <p>
 * Author:
 * <p>
 * CreateDate:      2020/9/23 13:53
 * <p>
 * Description:     获取程序应用图标
 */
public class AppIconHelper {

    public static Bitmap getAppIcon(PackageManager packageManager, String packageName) {
        if (Build.VERSION.SDK_INT >= 26) {
            return getIcon(packageManager, packageName);
        }

        try {
            Drawable applicationIcon = packageManager.getApplicationIcon(packageName);
            return ((BitmapDrawable) applicationIcon).getBitmap();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private static Bitmap getIcon(PackageManager packageManager, String packageName) {
        try {
            Drawable icon = packageManager.getApplicationIcon(packageName);
            if (icon instanceof BitmapDrawable) {
                return ((BitmapDrawable) icon).getBitmap();
            } else if (icon instanceof AdaptiveIconDrawable) {
                Drawable backgroundDr = ((AdaptiveIconDrawable) icon).getBackground();
                Drawable foregroundDr = ((AdaptiveIconDrawable) icon).getForeground();

                Drawable[] drr = new Drawable[2];
                drr[0] = backgroundDr;
                drr[1] = foregroundDr;

                LayerDrawable layerDrawable = new LayerDrawable(drr);

                int width = layerDrawable.getIntrinsicWidth();
                int height = layerDrawable.getIntrinsicHeight();

                Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

                Canvas canvas = new Canvas(bitmap);

                layerDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                layerDrawable.draw(canvas);

                return bitmap;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
