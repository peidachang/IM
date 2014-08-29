package com.yzy.im.server;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class IMManager
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
    String result=null;
    
    return result;
  }
  
  
  
}
