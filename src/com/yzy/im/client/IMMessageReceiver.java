package com.yzy.im.client;

import com.baidu.android.pushservice.PushConstants;
import com.yzy.im.util.LogUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class IMMessageReceiver extends BroadcastReceiver
{
  private static final String TAG = "IMMessageReceiver";

  @Override
  public void onReceive(Context context, Intent intent)
  {
    if(intent ==null )
    {
      return ;
    }
    if(intent.getAction().equals(PushConstants.ACTION_MESSAGE))
    {
      String msg=intent.getExtras().getString(PushConstants.EXTRA_PUSH_MESSAGE_STRING);
      LogUtil.getLogger().v("[Message->]"+msg);
    }else if(intent.getAction().equals(PushConstants.ACTION_RECEIVE))
    {
      String method=intent.getExtras().getString(PushConstants.EXTRA_METHOD);
      int errorCode=intent.getExtras().getInt(PushConstants.EXTRA_ERROR_CODE,PushConstants.ERROR_SUCCESS);
      String content=new String(intent.getExtras().getByteArray(PushConstants.EXTRA_CONTENT));
    }else if(intent.getAction().equals(PushConstants.ACTION_RECEIVER_NOTIFICATION_CLICK))
    {
      String title=intent.getExtras().getString(PushConstants.EXTRA_NOTIFICATION_TITLE);
      String content=intent.getExtras().getString(PushConstants.EXTRA_NOTIFICATION_CONTENT);
    }else if(intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE"))
    {
      
    }
  }
}
