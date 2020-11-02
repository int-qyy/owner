package com.swufe.owner;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.swufe.owner.JsonBean;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;

import static android.content.Context.MODE_PRIVATE;


public class JsonEx {
    private final static String TAG = "JsonEx";

    /**
     * 英译汉时使用。查词
     * 使用pull方式解析金山词霸返回的XML数据。
     * */
    public static void Envoice(String result) {

        try {

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(result));
            int eventType = xmlPullParser.getEventType();

            String voiceUrlText = "";   //发音url


            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = xmlPullParser.getName();
                switch (eventType) {
                    //开始解析
                    case XmlPullParser.START_TAG: {
                        switch (nodeName) {
                            case "pron":
                                voiceUrlText += xmlPullParser.nextText() + "|";
                                Log.i(TAG,voiceUrlText);
                                break;
                            default:
                                break;
                        }
                    }
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
            String[] voiceUrlArray = voiceUrlText.split("\\|");

            //创建SharedPreferences.Editor对象，指定文件名为
            SharedPreferences.Editor editor = MyApplication.getContext().getSharedPreferences("Json",MODE_PRIVATE).edit();

            editor.clear();
            editor.putString("voiceEnUrlText",voiceUrlArray[0]);
            editor.apply();

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "解析过程中出错！！！");
        }

    }



}
