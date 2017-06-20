# 整合[libjpeg-turbo](https://github.com/libjpeg-turbo/libjpeg-turbo)适用于Android的图片压缩
**我相信很多朋友都想有一个优秀的图片压缩方法，也相信很多人知道哈夫曼算法，也知道有大神写过jpeg的压缩算法，但是苦于没办法整理这些资料
而只能放弃，而刚好我也是这么过来的，所以将这个算法整理出来，方便能够再下次用到的地方拿来用。**<br/>
## 使用方法
***将项目源码下载下来，引入[<font color='red'>imageCompressLibrary</font>]这个Module，这是你的项目已经拥有了压缩图片的能力，具体代码使用如下：*** <br/>
```
new AsyncTask<Void, Void, Void>() {
        @Override
        protected Void doInBackground(Void... params) {
            //压缩图片
            File file = new File(imagePath);
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            //注意这句话，就是这一句代码调用了压缩图片的算法
            /**
            * 第一个参数传入一个将要压缩的图片
            * 第二个参数是压缩的程度，根据自己的需要来取值1-100
            * 第三个参数是图片压缩完成后输出的路径
            * 第三个参数是否采用哈弗曼算法,一般传true
            */
            ImageCompress.nativeCompressBitmap(bitmap, 50, compressPath, true);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Glide.with(this)
                    .load(compressPath)
                    .into(imageView);
        }
    }.execute();
```
### 我的个人QQ：764093434
