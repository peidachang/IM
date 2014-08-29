package com.yzy.im.server;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.yzy.im.util.MD5Util;

import android.text.TextUtils;

public class IMManager implements IConstants
{
  private static final String TAG = "IMManager";
  
  
  /**
   * 对URL进行UTF-8编码
   * @param url 
   *        需要进行编码的url
   * @return
   *        编码后的url
   * @throws UnsupportedEncodingException
   */
  public String urlEncode(String url) throws UnsupportedEncodingException
  {
    String tmp=URLEncoder.encode(url, "utf-8");
    return tmp.replace("*", "%2A");
  }
  
  public String jsonEncode(String url)
  {
    String tmp=url.replace("\\", "\\\\");
    tmp=tmp.replace("\"", "\\\"");
    tmp=tmp.replace("\'", "\\\'");
    return tmp;
  }
  
  
  public String sendHttpRequest(ParamData data)
  {
    StringBuilder sb=new StringBuilder();
    String channel=data.get(CHANNEL_ID);
    if(TextUtils.isEmpty(channel))
    {
      channel="channel";
    }
    data.put(TIMESTAMP, String.valueOf(System.currentTimeMillis()/1000));
    sb.append(HTTP_METHOD)
    .append(mUrl)
    .append(channel)
    .append(data.toString());
    data.put(SIGN, MD5Util.encode(sb.toString()));
    return sb.toString();
  }
  
  
  
}
