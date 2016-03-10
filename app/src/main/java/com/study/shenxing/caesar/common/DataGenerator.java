package com.study.shenxing.caesar.common;

import com.study.shenxing.caesar.stickylistview.Countries;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenxing on 16-3-4.
 */
public class DataGenerator {
    private static DataGenerator sInstance ;

    private DataGenerator() {
    }

    public static DataGenerator getInstance() {
        if (sInstance == null) {
            sInstance = new DataGenerator() ;
        }
        return sInstance ;
    }

    public List<String> getListDatas() {
        List<String> data = new ArrayList<String>();
        for (int i = 0; i < Countries.COUNTRIES.length; i++) {
            data.add(Countries.COUNTRIES[i]) ;
        }
        return data ;
    }
}
