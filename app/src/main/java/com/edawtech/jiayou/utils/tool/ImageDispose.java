package com.edawtech.jiayou.utils.tool;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

// 图像处理
public class ImageDispose {

    private static ImageDispose mSingleIns = null;

    private ImageDispose() {
    }

    public static ImageDispose getInstance() {
        if (mSingleIns == null) {
            synchronized (ImageDispose.class) {
                if (mSingleIns == null) {
                    mSingleIns = new ImageDispose();
                }
            }
        }
        return mSingleIns;
    }

    //获取图片的旋转角度
    public int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }



    /**
     * 旋转图片
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public Bitmap rotaingImageView(int angle , Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();;
        matrix.postRotate(angle);
        System.out.println("angle2=" + angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    /**
     *
     * @param path 图片路径
     * @param digree 旋转角度
     * @return
     */
    public Bitmap creatBitmap(String path, int digree){
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        Bitmap bitmapRe;
        // 旋转图片
        Matrix m = new Matrix();
        m.postRotate(digree);
        bitmapRe = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), m, true);
        if (bitmap!=bitmapRe){
            bitmap.recycle();
        }
        return  bitmapRe;
    }


    FileOutputStream out;
    String bitmapName = System.currentTimeMillis()+".jpg"; //  "fenxiang.jpg"
    File file;
    String QQFilePath;
    public String saveBitmap(Bitmap bmp) {

        try { // 获取SDCard指定目录下
            String sdCardDir = Environment.getExternalStorageDirectory() + "/kbb/"; // zupubao
            File dirFile = new File(sdCardDir);  //目录转化成文件夹
            if (!dirFile.exists()) {              //如果不存在，那就建立这个文件夹
                dirFile.mkdirs();
            }                          //文件夹有啦，就可以保存图片啦
            File file = new File(sdCardDir, bitmapName);// 在SDcard的目录下创建图片文,以当前时间为其命名
            out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
//            System.out.println("_________保存到____sd______指定目录文件夹下____________________");



            Log.e("saveBitMap", "saveBitmap: 图片保存到" + Environment.getExternalStorageDirectory() + "/kbb/" + bitmapName);
            QQFilePath = Environment.getExternalStorageDirectory() + "/kbb/" + bitmapName;
//            showShare(QQFilePath);
//            showShare2();
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return QQFilePath;
//        Toast.makeText(HahItemActivity.this,"保存已经至"+Environment.getExternalStorageDirectory()+"/CoolImage/"+"目录文件夹下", Toast.LENGTH_SHORT).show();
    }


/*  Glide.get(Utils.getTopActivityOrApp()).clearMemory();
    Log.e("分享图片地址链接分享", "分享图片地址：" + shopInfoEntity.getImgs().get(0).toString());
                Glide.with(ShopDetailsActivity.this).load(shopInfoEntity.getImgs().get(0)).asBitmap().into(new SimpleTarget<Bitmap>() {
        @Override
        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                        //由于微信分享的图片要求在32k一下,所以要转换成缩略图
            bitmap = Tool.createBitmapThumbnail(resource, false);
            wxBitMap = bitmap;
//                            saveBitmap(bitmap);
//                            String sss = saveMyBitmap("fenxiang", bitmap);
            saveBitmap(bitmap);
        }
    });*/

}
