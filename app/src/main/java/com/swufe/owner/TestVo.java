package com.swufe.owner;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TestVo extends AppCompatActivity {

    private static final String TAG="TestVo";
    VoItem voItem = null;
    String english,chinese;
    EditText EnString;
    TextView ChString,ShowString;
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_vo);
        EnString=(EditText)findViewById(R.id.ipt);
        ChString=(TextView)findViewById(R.id.ch1);
        ShowString=(TextView) findViewById(R.id.showVo);

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
                Log.i(TAG,"getting=========================================");
                Log.i(TAG, result);
                runOnUiThread(new Runnable() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void run() {
                        Envoice(result);
                        SharedPreferences pref = getSharedPreferences("JsonEx", MODE_PRIVATE);
                        final String voiceEnUrlText = pref.getString("voiceEnUrlText", "空");
                        Log.i(TAG, "enurlText===============" + voiceEnUrlText);
                        /**
                         *

                         final MediaPlayer[] mediaPlayer = {new MediaPlayer()};
                         try {
                         mediaPlayer[0].setDataSource(TestVo.this, Uri.parse(voiceEnUrlText));
                         mediaPlayer[0].prepare();
                         } catch (IOException e) {
                         e.printStackTrace();
                         }
                         ImageView enVoiceImg = (ImageView) findViewById(R.id.im_en_voice);
                         enVoiceImg.setOnClickListener(new View.OnClickListener() {
                        @Override public void onClick(View v) {
                        try {
                        mediaPlayer[0].start();
                        Log.i(TAG,"已经播放");
                        mediaPlayer[0].reset();
                        mediaPlayer[0].release();
                        mediaPlayer[0] = null;
                        } catch (Exception e) {
                        e.printStackTrace();
                        }
                        }

                        });
                         */

                        final MediaPlayer[] mediaPlayer = {new MediaPlayer()};
                        try {
                            mediaPlayer[0].setDataSource(voiceEnUrlText);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mediaPlayer[0].setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mediaPlayer[0].setOnErrorListener(new MediaPlayer.OnErrorListener() {

                            @Override
                            public boolean onError(MediaPlayer mp, int what, int extra) {
                                mp.stop();
                                mp.release();
                                Log.i(TAG,"Error on Listener,what:"+what+"extra:"+extra);
                                return false;
                            }
                        });

                        mediaPlayer[0].setOnCompletionListener(new MediaPlayer.OnCompletionListener(){

                            @Override
                            public void onCompletion(MediaPlayer arg0) {
                                arg0.stop();
                                arg0.release();
                                Log.i(TAG,"mediaPlayer Listener completed");
                            }
                        });


                        try {
                            mediaPlayer[0].prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mediaPlayer[0].setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                mediaPlayer[0].start();
                                mediaPlayer[0].release();
                                mediaPlayer[0] = null;
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
        //Intent intent=new Intent(TestVo.this, TestVo.class);
        //startActivity(intent);
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



    public void Envoice(String result) {

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

            //创建SharedPreferences.Editor对象，指定文件名为
            SharedPreferences.Editor editor = getSharedPreferences("JsonEx",MODE_PRIVATE).edit();
            editor.clear();
            Log.i(TAG,"create editor");
            editor.putString("voiceEnUrlText",voiceUrlText);
            Log.i(TAG,"voiceURL==============="+voiceUrlText);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "解析过程中出错！！！");
        }

    }



}