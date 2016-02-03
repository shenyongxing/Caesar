package com.study.shenxing.caesar.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.study.shenxing.caesar.R;
import com.study.shenxing.caesar.encrypt.CryptTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseDemoActivity extends AppCompatActivity {
//    public static String TABLE_NAME = "CacheDbBean";
    public static String TABLE_NAME = "package_residue_list";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_demo);

        ForGOPowerHelper dataBaseHelper = new ForGOPowerHelper(this) ;
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();

        readData(queryResidue(database)) ;

        String cryptString = CryptTool.encrypt("/Android/data/com.whatsapp/cache/", "go") ;
        Log.i("shenxing", "加密后： " + cryptString) ;
        String decrypt_error =  CryptTool.decrypt(cryptString, "goshihdi") ;
        String decrypt_right =  CryptTool.decrypt(cryptString, "go") ;
        Log.i("shenxing", "解密后： error :" + decrypt_error + ", " + decrypt_right) ;


    }

    private Cursor queryResidue(SQLiteDatabase db) {
        Cursor cursor = null;


        //		String[] columns = new String[]{"file_name", "package_name", "app_name"};
        //
        //		cursor = db.query(TABLE_NAME, columns, "file_name", arrayArgs, null, null, null);
        cursor = db.query(TABLE_NAME, null, null, new String[]{}, null, null, null) ;

        return cursor;
    }


    public void readData(Cursor cursor) {


        while (cursor != null && cursor.moveToNext()) {
            String packageName = cursor.getString(cursor.getColumnIndex("package_name"));
            String path = cursor.getString(cursor.getColumnIndex("path"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            Log.i("shenxing25", "包名： " + packageName + "路径： " + path + ", 类型： " + title) ;
        }

    }


    public static void testInsert(SQLiteDatabase db, List<HashMap<String, String>> list) {
        int count = 0 ;


        // 开启事务
        db.beginTransaction();
        try{
            for (HashMap<String, String> map : list) {

                String packageName = map.get("a") ;
                String path = map.get("b") ;
                int type = Integer.valueOf(map.get("c")) ;
                String insertSql = "insert into cache_path_list(package_name, path, go_type) values (" + "\"" + packageName + "\", \"" + path + "\", " + type + ")"  ;
                db.execSQL(insertSql);
                count++ ;

            }

            db.setTransactionSuccessful();
        }
        catch(Exception ex){

        } finally {
            db.endTransaction();
        }

        Log.i("shenxing18", "************************************insert " + count + "行") ;
    }



    public static List<HashMap<String, String>> getDbCachePath(SQLiteDatabase db) {
        List<String> mPathList = new ArrayList<String>();
        Cursor cursor = null;
        cursor = db.query("CacheDbBean", null, null, new String[]{}, null, null, null) ;

        List<HashMap<String, String>> list = new ArrayList<>() ;
        while (cursor != null && cursor.moveToNext()) {
            HashMap<String, String> map = new HashMap<>() ;
            String packageName = cursor.getString(cursor.getColumnIndex("package_name"));
            String path = cursor.getString(cursor.getColumnIndex("path"));
            int go_type = cursor.getInt(cursor.getColumnIndex("go_type")) ;

            if ("".equals(path)) {
                continue;
            }

            map.put("a", packageName) ;
            map.put("b", CryptTool.encrypt(path, "gopowermaster")) ;
            map.put("c", String.valueOf(go_type)) ;
            list.add(map) ;


//			mPathList.add(path) ;
        }
        String sql = "create table cache_path_list (package_name text, path text, go_type int)" ;
        db.execSQL(sql);

        return list ;
    }



}

//String sql = "create table cache_path_list (package_name text, path text, go_type int)" ;
//db.execSQL(sql);


//连接数据库
//SQLiteDatabase db = null;
//try {
//        db = mReFileManager.getmDbHelper().getWritableDatabase();
//        List<HashMap<String, String>> list = ResidueFileManager.getDbCachePath(db) ;
//        db.close();
//
//        db = mReFileManager.getmDbHelper().getWritableDatabase();
//        ResidueFileManager.testInsert(db, list);
//        } catch (SQLiteException e) {
//        e.printStackTrace();
//        return true;
//        }
