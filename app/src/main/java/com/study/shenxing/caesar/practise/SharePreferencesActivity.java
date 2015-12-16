package com.study.shenxing.caesar.practise;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.study.shenxing.caesar.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SharePreferencesActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mSaveBtn ;
    private Button mRestoreBtn ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_preferences);

        mSaveBtn = (Button) findViewById(R.id.save) ;
        mSaveBtn.setOnClickListener(this);
        mRestoreBtn = (Button) findViewById(R.id.restore) ;
        mRestoreBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.save) {
            try {
                CusTestObject object = new CusTestObject() ;
                object.setName("shenxing");
                object.setAge(24);

                SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(object);
                String base64Product = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
                editor.putString("object", base64Product);
                editor.commit() ;

                baos.close();
                oos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (v.getId() == R.id.restore) {
            try {
                SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
                String objectStr = sharedPreferences.getString("object", "");
                byte[] objectByte = Base64.decode(objectStr, Base64.DEFAULT) ;
                ByteArrayInputStream bais = new ByteArrayInputStream(objectByte);
                ObjectInputStream ois = new ObjectInputStream(bais);
                CusTestObject object = (CusTestObject) ois.readObject() ;
                Log.i("shenxing", "name : " + object.getName() + ", age : " + object.getAge()) ;

                bais.close();
                ois.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 内部类只有设置为static才能被序列化, fuck,搜了好久才知道.
     * 或者将内部类单独作为一个类.
     */
    public static class CusTestObject implements Serializable {
        private String name ;
        private int age ;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getAge() {
            return age;
        }


    }
}
