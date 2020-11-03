package com.swufe.owner;
import okhttp3.OkHttpClient;
import okhttp3.Request;
public class HttpUtils {

        /**
         * 使用OkHttp网络工具发送网络请求
         * */
        public static void sendOkHttpRequest(String address,okhttp3.Callback callback){

            //创建OkHttpClient对象
            OkHttpClient client = new OkHttpClient();

            //创建Request对象
            Request request = new Request.Builder().url(address).build();

            //发送请求
            client.newCall(request).enqueue(callback);

        }

}
