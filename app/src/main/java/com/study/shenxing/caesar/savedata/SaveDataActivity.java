package com.study.shenxing.caesar.savedata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 数据的读写最好要在androidManifest.xml文件中申明权限
 */
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

        String filename = "shenxing";
        String msg = "Hello world! 有中文怎么办？";
        writeDataToFile(filename, msg);
        String result = readDataFromFile(filename) ;
        Log.i("Caesar", "result : " + result) ;

        FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(this);
        insert(mDbHelper);
        query(mDbHelper) ;

    }

    private void createCusCacheFile(Context context, String filename) {
        File file = new File(context.getFilesDir(), filename);
    }

    private void writeDataToFile(String fileName, String msg) {
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(msg.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String readDataFromFile(String fileName) {
        String result = null;
        FileInputStream inputStream ;
        byte[] bytes = new byte[1024] ;
        int temp ;
        try {
            inputStream = openFileInput(fileName);
            // example 1

            // read的解释
            // Reads a single byte from this stream and returns it as an integer in the
            // range from 0 to 255.
            // 返回的整型值即 表示 读取到的字节
           /* int i = 0 ;
            while ((i = inputStream.read()) != -1) {
                    char tempChar = (char) i ;
                Log.i("Caesar", "tempChar : " + tempChar) ;
            }*/
//             打印结果：
//            I/Caesar  (15113): tempChar : H
//            I/Caesar  (15113): tempChar : e
//            I/Caesar  (15113): tempChar : l
//            I/Caesar  (15113): tempChar : l
//            I/Caesar  (15113): tempChar : o
//            I/Caesar  (15113): tempChar :
//            I/Caesar  (15113): tempChar : w
//            I/Caesar  (15113): tempChar : o
//            I/Caesar  (15113): tempChar : r
//            I/Caesar  (15113): tempChar : l
//            I/Caesar  (15113): tempChar : d
//            I/Caesar  (15113): tempChar : !
            // 但是如果文件存在中文会有乱码
            // read()方法每次只能读取一个字节，所以也只能读取由ASCII码范围内的一些字符。
            // 这些字符主要用于显示现代英语和其他西欧语言。而对于汉字等unicode中的字符则不能正常读取。只能以乱码的形式显示。
            // 所以对于存在有汉字的文件读写应避开此种方法

            // example 2

            // 指定每次读取的字节 如果以后在看不是很明白， 将byte[] bytes = new byte[n] ; 中的n由1慢慢变大观察
            // 由于指定了bytes为1024个字节， 故在打印时后面，没有被写到的字节都是乱码， 故应采用example 3中的read方法
//            int j = 0;
//            while ((j = inputStream.read(bytes)) != -1) {
//                String str = new String(bytes, "utf-8") ;
//                Log.i("Caesar", "tempChar2 : " + str) ;
//            }

            // example 3 可以正确读取文件内容， 且没有乱码

            int k = 0;
            byte[] tempBytes = new byte[inputStream.available()] ;
            while ((k = inputStream.read(tempBytes, 0, tempBytes.length)) != -1) {
                String str = new String(tempBytes, "utf-8") ;
                Log.i("Caesar", "tempChar3 : " + str) ;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return result ;
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

    /**************************************************************************/
    /**
     * 向数据库插入数据
     */
    private void insert(SQLiteOpenHelper mDbHelper) {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String id = "100" ;
        String title = "Hello world" ;
        String content = "This is test demo that insert information into database" ;
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID, id);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, title);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_CONTENT, content);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME,
                "Shen", values);
        Log.i("shenxing", "newRowId : " + newRowId) ;
    }

    /**
     * 更新数据
     */
    private void update(SQLiteOpenHelper mDbHelper) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, "modified");

        // Which row to update, based on the ID
        String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(100) };

        int count = db.update(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    /**
     * 读取数据
     */
    private void query(SQLiteOpenHelper mDbHelper) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                FeedReaderContract.FeedEntry._ID,
                FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,
                FeedReaderContract.FeedEntry.COLUMN_NAME_CONTENT,
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                FeedReaderContract.FeedEntry.COLUMN_NAME_CONTENT + " DESC";
        String selection = null ;
        String[] selectionArgs = null ;

        Cursor c = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        c.moveToFirst();
        while (c.moveToNext()) {
            String result = c.getString(c.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_CONTENT));
            Log.i("shenxing", "result : " + result) ;
        }

    }

    /**
     * 删除数据
     */
    public void delete(SQLiteOpenHelper mDbHelper) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define 'where' part of query.
        String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { String.valueOf(100) };
         // Issue SQL statement.
        db.delete(FeedReaderContract.FeedEntry.TABLE_NAME, selection, selectionArgs);
    }


}
