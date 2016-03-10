package com.study.shenxing.caesar.xmlparse;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;

import com.study.shenxing.caesar.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class XmlParserActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml_parser);

//        InputStream is = getAssetsStream("xml_parse_test.xml");
        // 网络资料：上面代码适用于Eclipse不适用于Android studio 下面的代码
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("/src/main/assets/xml_parse_test.xml");
        testParse(is);
    }

    /**
     * 得到Assets里面相应的文件流
     *
     * @param fileName
     * @return
     */
    private InputStream getAssetsStream(String fileName) {
        InputStream is = null;
        try {
            is = getAssets().open(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }

    /**
     * xml解析
     * @param in
     */
    private void testParse(InputStream in) {
        XmlPullParser parser = Xml.newPullParser() ;
        try {
            parser.setInput(in, "utf-8");
            int eventType = parser.getEventType() ;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT) {

                } else if (eventType == XmlPullParser.START_TAG) {
                    String name = parser.getName() ;
                    Log.i("shenxing", "name : " + name) ;
                } else if (eventType == XmlPullParser.END_TAG) {

                } else if (eventType == XmlPullParser.END_DOCUMENT) {

                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
