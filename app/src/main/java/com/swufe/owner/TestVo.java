package com.swufe.owner;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.swufe.owner.DB.DBManager;
import com.swufe.owner.DB.VoItem;
import com.swufe.owner.Utils.HttpUtils;
import com.swufe.owner.Utils.PlayEn;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.os.Looper.*;

public class TestVo extends AppCompatActivity {

    private static final String TAG="TestVo";
    VoItem voItem = null;
    String english,chinese;
    EditText EnString;
    TextView ChString,ShowString;
    ImageView enImg;

    Handler handler;
    public PlayEn mp3Box=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_vo);
        EnString=(EditText)findViewById(R.id.ipt);
        ChString=(TextView)findViewById(R.id.ch1);
        ShowString=(TextView) findViewById(R.id.showVo);
        enImg= (ImageView) findViewById(R.id.im_en_voice);

        mp3Box=new PlayEn(TestVo.this, "my_vo");

        DBManager dbManager = new DBManager(TestVo.this);
        Random r = new Random();
        int ran1 = r.nextInt(100000);
        int len=dbManager.count();
        int ran=ran1%len;
        Log.i(TAG,"len:====="+len);
        Log.i(TAG,"now id===="+ran);
        voItem= dbManager.findById(ran);
        while(voItem==null){
            r = new Random();
             ran1 = r.nextInt(100000);
             ran=ran1%len;
            Log.i(TAG,"len:====="+len);
            Log.i(TAG,"now id===="+ran);
            voItem= dbManager.findById(ran);
        }
        english=voItem.getEnString();
        Log.i(TAG,"english=="+english);
        chinese=voItem.getChString();
        Log.i(TAG,"chinese==="+chinese);
        ChString.setText(chinese);

        final String urlxml = "https://dict-co.iciba.com/api/dictionary.php?w=" + english + "&key=9AA9FA4923AC16CED1583C26CF284C3F";

        HttpUtils.sendOkHttpRequest(urlxml, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Toast.makeText(TestVo.this, "获取翻译数据失败！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {

                final String result = response.body().string();
                Log.i(TAG, result);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String voiceEnUrlText= Envoice(result);
                        Log.i(TAG, "enurlText===============" + voiceEnUrlText);
                        //mp3Box.playMusicByWord(voiceEnUrlText);
                        enImg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                try {
                                    MediaPlayer mediaPlayer;
                                    mediaPlayer = MediaPlayer.create(TestVo.this,Uri.parse(voiceEnUrlText));
                                    mediaPlayer.start();
                                    Log.i(TAG,"已发声");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    }
                });
            }
        });

}

    public void submit(View view){
        EnString=(EditText)findViewById(R.id.ipt);
        String enString=EnString.getText().toString();
        Log.i(TAG,"input===="+enString);
        if(enString == null || enString.equals("") || enString.equals(R.string.hint)){//no input
            Toast.makeText(this, "请输入英文单词", Toast.LENGTH_SHORT).show();
        }else if(enString.equals(english)){
            Toast.makeText(this, "回答对啦", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "需要继续记忆", Toast.LENGTH_SHORT).show();
            ShowString.setText(english);
            Log.i(TAG,english+enString);
        }
    }


    public void reM(View view){
        Intent intent=new Intent(TestVo.this, MainActivity.class);
        startActivity(intent);
    }
    public void ans(View view){
        ShowString.setText(english);
    }
    public void nex(View view){
        Intent intent=new Intent(TestVo.this, TestVo.class);
        startActivity(intent);
    }

    public void voice(View view){
        Intent intent=new Intent(TestVo.this, MainActivity.class);
        startActivity(intent);
    }


    /**
     * 获取英文发音URL
     * @param result
     */
    public String Envoice(String result) {
        String voiceUrlText = "";   //发音url
        try {

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(result));
            int eventType = xmlPullParser.getEventType();


            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = xmlPullParser.getName();
                switch (eventType) {
                    //开始解析
                    case XmlPullParser.START_TAG: {
                        switch (nodeName) {
                            case "pron":
                                voiceUrlText = xmlPullParser.nextText() ;
                                Log.i(TAG,"vo========="+voiceUrlText);
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

        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "解析过程中出错！！！");
        }
        return voiceUrlText;
    }


    private long firstTime = 0;
    /**
     * 监听keyUP 实现双击退出
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            long nowTime = System.currentTimeMillis();
            if (nowTime - firstTime > 2000) {
                Toast.makeText(TestVo.this, "你给我回来", Toast.LENGTH_SHORT).show();
                firstTime = nowTime;
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyUp(keyCode, event);
    }



}