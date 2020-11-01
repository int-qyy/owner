package com.swufe.owner;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetMP3 {
    public static String request(String httpUrl, String httpArg) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            /**setRequestProperty（key,value）
             * Sets the general request property. If a property with the key already
             * exists, overwrite its value with the new value.
             *  设置通用的请求属性。如果该key对应的的属性值已经存在，那么新值将覆盖以前的值
             * <p> NOTE: HTTP requires all request properties which can
             * legally have multiple instances with the same key
             * to use a comma-seperated list syntax which enables multiple
             * properties to be appended into a single property.
             *提示：HTTP要求拥有相同key值的多个实例的所有请求属性，可以使用逗号分隔的列表语法，这样就可以将多个属性附加到单个属性中
             * @param   key     the keyword by which the request is known
             *                  (e.g., "<code>Accept</code>").
             * @param   value   the value associated with it.
             * @throws IllegalStateException if already connected
             * @throws NullPointerException if key is <CODE>null</CODE>
             * @see #getRequestProperty(java.lang.String)
             */
            connection.setRequestProperty("apikey",  "71e4b699*********cf44ebb02cd2");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
