package com.example.yanci.api.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Http 基础请求类
 * Created by yanci on 16/1/25.
 */
public class BaseHttpHandler {

    private final  static  String TAG = "BaseHttpHandler";

    // HTTP 请求方式
    private final  static  String REQUEST_METHOD_POST = "POST";
    private final  static  String REQUEST_METHOD_GET  = "GET";

    // 编码类型
    private final static String ENCODE_TYPE = "UTF-8";

    // 请求超时时间
    private final static int TIME_OUT    =  100000;

    // 单例句柄
    private static BaseHttpHandler instance = null;

    // 编解码器
    private  BaseHttpHandler() {

    }

    /**
     * BaseHttpHandler
     * @return 实例
     */
    public   static  BaseHttpHandler getInstance() {
        if (instance == null) {
            instance = new BaseHttpHandler();
        }
        return instance;
    }



    /**
     * 请求URL
     * @param Request
     * @return
     */
    private String RequestUrl(String request)  {
        return request;
    }

    /**
     * @brief Http GET 请求
     * @param paramsMap  请求参数
     * @param typeOfT    模板类型
     * @param <T>
     * @return
     * @throws IOException
     */
    public  <T> T HttpGetRequest(String request,Map<String, String> paramsMap, Type typeOfT) throws IOException {
        String params = joinParams(paramsMap);
        HttpURLConnection connection = getConnection(RequestUrl(request) + '?' +  params, REQUEST_METHOD_GET, TIME_OUT);
        connection.connect();

        // Request Fail
        if (connection.getResponseCode() != 200) {
            connection.disconnect();
            return  null;
        }

        // Request success
        if (connection.getResponseCode() == 200) {
            InputStream is = connection.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len = 0;
            byte buffer[] = new byte[1024];
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer,0,len);
            }
            is.close();
            baos.close();
            connection.disconnect();
            final  String result = new String(baos.toByteArray());
            Gson gson = new Gson();
            return gson.fromJson(result, typeOfT);
        }

        return null;
    }

    /**
     * Http POST
     * @param paramsMap
     * @param typeOfT
     * @param <T>
     * @return
     * @throws IOException
     */
    public  <T> T HttpPostRequest(String request,Map<String, String> paramsMap, Type typeOfT) throws IOException{
        String data = joinParams(paramsMap);
        HttpURLConnection connection = getConnection(RequestUrl(request), REQUEST_METHOD_POST,TIME_OUT);
        connection.setRequestProperty("Content-Length",String.valueOf(data.getBytes().length));
        connection.setRequestProperty("Accept","application/json");
        connection.connect();

        // Write Params
        OutputStream os = connection.getOutputStream();
        os.write(data.getBytes());
        os.flush();

        // Request Fail
        if (connection.getResponseCode() != 200) {
            connection.disconnect();
            return  null;
        }

        // Request success
        if (connection.getResponseCode() == 200) {
            InputStream is = connection.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len = 0;
            byte buffer[] = new byte[1024];
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer,0,len);
            }
            is.close();
            baos.close();
            connection.disconnect();
            final  String result = new String(baos.toByteArray());
            Gson gson = new Gson();
            return gson.fromJson(result, typeOfT);
        }

        return null;
    }

    /**
     * 获取URL CONN
     * @param serverUrl
     * @param requestMethod
     * @param timeout
     * @return
     */
    private HttpURLConnection getConnection(String serverUrl,String requestMethod,int timeout) {
        HttpURLConnection connection = null;
        if (connection == null) {
            try {
                URL url = new URL(serverUrl);
                connection = (HttpURLConnection) url.openConnection();
                // 设置请求方式
                connection.setRequestMethod(requestMethod);
                // 设置输入流
                connection.setDoInput(true);

                if (requestMethod == REQUEST_METHOD_GET) {
                    connection.setRequestMethod("GET");
                }
                // 设置输出流
                else if (requestMethod == REQUEST_METHOD_POST) {
                    connection.setDoOutput(true);
                    connection.setRequestMethod("POST");
                }

                // 不使用缓存
                connection.setUseCaches(false);
                // 读取数据超时时间
                connection.setReadTimeout(timeout);
                // 连接超时时间
                connection.setConnectTimeout(timeout);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    /**
     * 拼接参数列表
     * @param paramsMap
     * @return
     */
    private  String joinParams(Map<String,String> paramsMap) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : paramsMap.keySet()) {
            stringBuilder.append(key);
            stringBuilder.append("=");
            try {
                stringBuilder.append(URLEncoder.encode(paramsMap.get(key), ENCODE_TYPE));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            stringBuilder.append("&");
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }
}
