package com.foin.jpeg;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.image.compress.ImageCompress;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    /**
     * @desc 选择图片的RequestCode
     */
    public static final int IMAGE_PICKER_REQUEST_CODE = 100;
    /**
     * @desc 显示图片
     */
    @BindView(R.id.image_view)
    ImageView imageView;
    /**
     * @desc 图片压缩的输出路径
     */
    private String compressPath;
    /**
     * @desc 当前选择的图片路径
     */
    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        //选中数量限制
        imagePicker.setSelectLimit(1);
        //显示拍照按钮
        imagePicker.setShowCamera(true);
        //压缩图片的路径
        compressPath = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + System.currentTimeMillis() + ".jpg";
    }

    @OnClick({R.id.choose, R.id.compress})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.choose:
                Intent intent = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent, IMAGE_PICKER_REQUEST_CODE);
                break;
            case R.id.compress:
                if (imagePath == null || new File(imagePath).length() < 1) {
                    Toast.makeText(this, "请选择一张图片", Toast.LENGTH_SHORT).show();
                    return;
                }
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        //压缩图片
                        File file = new File(imagePath);
                        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        ImageCompress.nativeCompressBitmap(bitmap, 50, compressPath, true);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        final File file = new File(compressPath);
                        if (file.length() <= 0) {
                            Toast.makeText(MainActivity.this, "图片压缩失败", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        //重新显示出来，感受一个下变化
                        Glide.with(MainActivity.this)
                                .load(compressPath)
                                .into(imageView);
                        Toast.makeText(MainActivity.this, "成功", Toast.LENGTH_SHORT).show();
                    }
                }.execute();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICKER_REQUEST_CODE && resultCode == ImagePicker.RESULT_CODE_ITEMS && data != null) {//得到选择图片的返回结果
            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            imagePath = images.get(0).path;
            //第一显示图片
            Glide.with(this)
                    .load(imagePath)
                    .into(imageView);
        }
    }

}
