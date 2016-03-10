package com.study.shenxing.caesar.algorithm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.study.shenxing.caesar.R;

public class AlgorithmDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algorithm_demo);

        int[] array = new int[] {28, 10, 49, 58, 20, 69, 20,40, 80, 100, 109, 210, 430, 320} ;
        compareSort(array);
    }

    /**
     * 比较排序法
     * @param a
     */
    private void compareSort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[i] > a[j]) {
                    swap(a, i, j);
                }
            }
        }
        print(a);
    }


    public void swap(int[] data, int a, int b) {
        int t = data[a];
        data[a] = data[b];
        data[b] = t;
    }

    private void print(int[] a) {
        if (a == null) {
            return ;
        }

        StringBuilder builder = new StringBuilder() ;
        builder.append("[ ") ;
        for (int i = 0; i < a.length; i++) {
            builder.append(a[i]) ;
            if (i == a.length - 1) {
                builder.append(" ]") ;
            } else {
                builder.append(", ");
            }
        }
        Log.i("shenxing", "排序结果是: " + builder.toString()) ;
    }
}
