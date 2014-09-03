package com.yzy.im.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import com.yzy.im.util.CommonUtil;
import com.yzy.im.util.LogUtil;
import com.yzy.im.util.MD5Util;

import android.text.TextUtils;
import android.util.Log;

public class IMManager implements IConstants
{
  private static final String TAG = "IMManager";
 
  public static IMManager _instance=null;
  
  public static IMManager getInstance()
  {
    if(_instance==null)
      _instance=new IMManager();
    
    return _instance;
  }
  
  
  public String sendHttpRequest(ParamData data)
  {
    try
    {
      data.put(TIMESTAMP, String.valueOf(System.currentTimeMillis()/1000));
      String channel=data.remove(CHANNEL_ID);
      if(channel==null)
        channel="channel";

      data.put(SIGN,CommonUtil.getSignature(data,channel));
      return HttpRequest(mUrl + channel,data.getParamString());
    }catch(Exception e)
    {
      return SEND_MSG_ERROR;
    }
   
  }
  
  private String HttpRequest(String url, String query) {
    LogUtil.getLogger().v(url);
    LogUtil.getLogger().v(query);
    StringBuilder out=new StringBuilder();
    URL urlobj;
    HttpURLConnection connection = null;

    try {
      urlobj = new URL(url);
      connection = (HttpURLConnection) urlobj.openConnection();
      connection.setRequestMethod("POST");

      connection.setRequestProperty("Content-Type",
          "application/x-www-form-urlencoded");
      connection
          .setRequestProperty("Content-Length", "" + query.length());
      connection.setRequestProperty("charset", "utf-8");

      connection.setUseCaches(false);
      connection.setDoInput(true);
      connection.setDoOutput(true);

      connection.setConnectTimeout(10*1000);
      connection.setReadTimeout(10*1000);

      // Send request
      DataOutputStream wr = new DataOutputStream(
          connection.getOutputStream());
      wr.writeBytes(query.toString());
      wr.flush();
      wr.close();

      // Get Response
      InputStream is = connection.getInputStream();
      BufferedReader rd = new BufferedReader(new InputStreamReader(is));
      String line;

      while ((line = rd.readLine()) != null) {
        out.append(line);
        out.append('\r');
      }
      rd.close();

    } catch (Exception e) {
      e.printStackTrace();
      out.append(SEND_MSG_ERROR);//消息发送失败，返回错误，执行重发
    }

    if (connection != null)
      connection.disconnect();

    return out.toString();
  }
  
  
  /**
   * 推送给所有用户
   * @param msg
   * @return
   */
  public String pushMessage(String msg)
  {
    ParamData data=new ParamData(PUSH_MSG);
    data.put(MESSAGE_TYPE, TEXT_MESSAGE);
    data.put(MESSAGES, msg);
    data.put(MSG_KEYS,MSG_KEY);
    data.put(PUSH_TYPE, PUSH_TYPE_ALL);
    return sendHttpRequest(data);
  }
  
  public String pushMessage(String msg,String userId)
  {
    ParamData data=new ParamData(PUSH_MSG);
    data.put(MESSAGE_TYPE, TEXT_MESSAGE);
    data.put(MESSAGES, msg);
    data.put(USER_ID, userId);
    data.put(MSG_KEYS,MSG_KEY);
    data.put(PUSH_TYPE, PUSH_TYPE_USER);
    return sendHttpRequest(data);
  }
  
}
