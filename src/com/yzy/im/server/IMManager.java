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

import com.yzy.im.util.LogUtil;
import com.yzy.im.util.MD5Util;

import android.text.TextUtils;

public class IMManager implements IConstants
{
  private static final String TAG = "IMManager";
  public static final String SEND_MSG_ERROR = "send_msg_error";
  
  

  
  
  public String sendHttpRequest(ParamData data)
  {
    try
    {
      StringBuilder sb=new StringBuilder();
      String channel=data.remove(CHANNEL_ID);
      if(TextUtils.isEmpty(channel))
      {
        channel="channel";
      }
      data.put(TIMESTAMP, String.valueOf(System.currentTimeMillis()/1000));
      sb.append(HTTP_METHOD)
      .append(mUrl)
      .append(channel)
      .append(data.toString())
      .append(secretkey);
      data.put(SIGN, MD5Util.encode(sb.toString()));
      return HttpRequest(mUrl + channel,data.getParamString());
    }catch(Exception e)
    {
      LogUtil.getLogger().e("error-->"+SEND_MSG_ERROR);
      return SEND_MSG_ERROR;
    }
   
  }
  
  private String HttpRequest(String url, String query) {
    LogUtil.getLogger().d("url-->"+url);
    LogUtil.getLogger().d("query-->"+query);
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
      LogUtil.getLogger().e(e.getMessage());
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
  
}
