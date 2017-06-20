package com.image.compress;

import android.graphics.Bitmap;

/**
 * @author Foin
 * @version 1.0
 * @time 2017/6/6 17:09
 * @desc 这是这个类的描述内容...
 */

public class ImageCompress {

    static {
        System.loadLibrary("jpeg-turbo");
    }

    /**
     * NDK方法加载图片
     *
     * @param bitmap   图片bitmap
     * @param quality  压缩的质量
     * @param fileName 压缩后的路径
     * @return
     */
    public native static int nativeCompressBitmap(Bitmap bitmap, int quality, String fileName, boolean var4);

}
