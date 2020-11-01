package com.swufe.owner;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.swufe.owner.GetMP3;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TestVo extends AppCompatActivity implements Runnable {

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


        Thread t = new Thread(this);
        t.start();
        String httpUrl = "http://fy.iciba.com/ajax.php?a=fy";
        String httpArg="";
        String re=GetMP3.request(httpUrl, httpArg);


    }






    private TextView.OnEditorActionListener EnterListenter = new TextView.OnEditorActionListener() {
        /**
         * 参数说明
         * @param v 被监听的对象
         * @param actionId  动作标识符,如果值等于EditorInfo.IME_NULL，则回车键被按下。
         * @param event    如果由输入键触发，这是事件；否则，这是空的(比如非输入键触发是空的)。
         * @return 返回你的动作
         */
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                Log.i("---","输入");
                String enString=EnString.toString();
                Log.i(TAG,"input===="+enString);
                if(enString == null || enString.equals("") || enString.equals(R.string.En)){//no input
                    ShowString.setText( "请输入英文单词");
                }else if(enString.equals(english)){
                    ShowString.setText("回答对啦");
                }else{
                    ShowString.setText("需要继续记忆"+english);
                    Log.i(TAG,english+enString);
                }
            }
            return false;
        }
    };

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


    @Override
    public void run(){
        Log.i(TAG,"run:run()......");
        Message msg = handler.obtainMessage(5);
        /**
        try{

                String form = "zh";
                String to = "en";
                String q = "我要妹子！";
                String url = "http://fy.iciba.com/ajax.php?a=fy";
                String url1="http://dict-co.iciba.com/api/dictionary.php?w=good&type=json&key=XXX";

                Map<String, String> params = new HashMap<>();
                params.put("f", form);
                params.put("t", to);
                params.put("w", q);

                CloseableHttpClient httpClient = HttpClients.createDefault();
                HttpPost request = new HttpPost(util.getUrlWithQueryString(url, params));
                CloseableHttpResponse response = httpClient.execute(request);

                HttpEntity entity = response.getEntity();

                String result = EntityUtils.toString(entity, "utf-8");

                System.out.println(result);
                EntityUtils.consume(entity);

                response.getEntity().getContent().close();
                response.close();
                msg.obj = list1;
                handler.sendMessage(msg);
        }catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
         **/

    }
}