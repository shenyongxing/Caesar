package com.study.shenxing.caesar.xmlparse;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;

import com.study.shenxing.caesar.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

public class XmlParserActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml_parser);

        // 如何读取assets文件夹文件??? 一直没成功
        // ===========2016年8月13日======== 下面代码在lg上是可以成功运行的,之前出错可能是机型问题
        InputStream is = getAssetsStream("xml_parse_test.xml");
        // 网络资料：上面代码适用于Eclipse不适用于Android studio 下面的代码
//        InputStream is = this.getClass().getClassLoader().getResourceAsStream("/src/main/assets/xml_parse_test.xml");
//        testParse(is);
//        InputStream is = getResources().openRawResource(R.raw.xml_parse_test) ;
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
//                if (eventType != XmlPullParser.START_TAG) {
//                    continue;
//                }


                if (eventType == XmlPullParser.START_DOCUMENT) {

                } else if (eventType == XmlPullParser.START_TAG) {
                    String name = parser.getName() ;
                    if ("name".equals(name) || "sex".equals(name) || "age".equals(name)) {
                        Log.i("shenxing", "tag : " + name + ", value : " + parser.nextText()) ;
                    }
                } else if (eventType == XmlPullParser.END_TAG) {

                } else if (eventType == XmlPullParser.TEXT) {
                    // XmlPullParse.TEXT 就是xml中定义的值
                    // 可以通过对比在start_tag获取的name,来判断该text具体是那个节点的值
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

    /**
     * 另一种解析方式
     */
    private void parseXml(XmlPullParser parser) {
        int event;
        String text = null;
        try {
            event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String name = parser.getName(); // start-tag节点名称
                switch (event) {
                    case XmlPullParser.START_TAG :
                        break;
                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (name.equals("name")) {
                            String parseName = text;
                        }
                        break;
                }

                event = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
