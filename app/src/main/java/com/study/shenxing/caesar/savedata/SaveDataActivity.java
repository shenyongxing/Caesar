package com.study.shenxing.caesar.savedata;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.study.shenxing.caesar.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_data);

        File fileDir = getFilesDir();
        File cacheDir = getCacheDir();

        Log.i("shenxing", "fileDir : " + fileDir.getAbsolutePath());
        Log.i("shenxing", "cacheDir : " + cacheDir.getAbsolutePath());
        // 上面log打印的日志如下
//        fileDir : /data/data/com.study.shenxing.caesar/files
//        cacheDir : /data/data/com.study.shenxing.caesar/cache

        writeDataToFile();

    }

    private void createCusCacheFile(Context context, String filename) {
        File file = new File(context.getFilesDir(), filename);
    }

    private void writeDataToFile() {
        String filename = "myfile";
        String string = "Hello world!";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public File getTempFile(Context context, String url) {
        File file = null;
        try {
            String fileName = Uri.parse(url).getLastPathSegment();
            file = File.createTempFile(fileName, null, context.getCacheDir());
        } catch (IOException e){
                // Error while creating file
        }
        return file;
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    // 其中利用Environment.getExternalStoragePublicDirectory来获取公开目录
    // 该目录在应用卸载时不会被删除
    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("shenxing", "Directory not created");
        }
        return file;
    }

    // 利用context.getExternalFilesDir获取应用私有目录
    // 该目录在应用卸载时会被删除
    public File getAlbumStorageDir(Context context, String albumName) {
        // Get the directory for the app's private pictures directory.
        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("shenxing", "Directory not created");
        }
        return file;
    }




}
