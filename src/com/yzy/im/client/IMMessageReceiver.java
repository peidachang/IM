package com.yzy.im.client;

import java.lang.annotation.Annotation;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import android.widget.RemoteViews.RemoteView;
import com.baidu.android.pushservice.PushConstants;
import com.google.gson.Gson;
import com.yzy.im.IMApplication;
import com.yzy.im.R;
import com.yzy.im.bean.IMMessage;
import com.yzy.im.bean.User;
import com.yzy.im.callback.IEventCallback;
import com.yzy.im.server.IConstants;
import com.yzy.im.ui.ChatActivity;
import com.yzy.im.util.LogUtil;
import com.yzy.im.util.SharePreferenceHelper;

@SuppressLint("NewApi")
public class IMMessageReceiver extends BroadcastReceiver
{
  private static final String TAG = "IMMessageReceiver";
  private NotificationCompat.Builder mBuilder;
  private NotificationManager mNotificationManager;

  @Override
  public void onReceive(Context context, Intent intent)
  {
    if(intent ==null )
    {
      return ;
    }
    //用户接收到消息
    if(intent.getAction().equals(PushConstants.ACTION_MESSAGE))
    {
      String msg=intent.getExtras().getString(PushConstants.EXTRA_PUSH_MESSAGE_STRING);
      LogUtil.getLogger().i("[Message->]"+msg);
      Gson json=new Gson();
      IMMessage iMsg=json.fromJson(msg,  IMMessage.class);
      //如果是新用户消息或者回应消息，则调用所有callback的onMessage方法
      if(iMsg.getMessage().equals(IConstants.MSG_NEW_USER)||iMsg.getMessage().equals(IConstants.MSG_NEW_USER_REPLY))
      {
        for(IEventCallback callback:IMApplication.getInstance().getCallback())
        {
          callback.onMessage(iMsg);
        }
      }else
      {
        LogUtil.getLogger().d("talk user id-->"+SharePreferenceHelper.getInstance(context).getTalkUserId());
        //正常信息就判断是否正在和它聊天，如果正在聊则直接显示，否则使用Notification显示
        if(SharePreferenceHelper.getInstance(context).getTalkUserId()==null||!SharePreferenceHelper.getInstance(context).getTalkUserId().equals(iMsg.getUserid()))
        {
            LogUtil.getLogger().d("start showNotificationMesage");
            showNotificationMessage(context, iMsg);
        }else
        {
          for(IEventCallback callback:IMApplication.getInstance().getCallback())
          {
            callback.onMessage(iMsg);
          }
        }
      }
      
      
    }else if(intent.getAction().equals(PushConstants.ACTION_RECEIVE))
    {
      String method=intent.getExtras().getString(PushConstants.EXTRA_METHOD);
      int errorCode=intent.getExtras().getInt(PushConstants.EXTRA_ERROR_CODE,PushConstants.ERROR_SUCCESS);
      String content=new String(intent.getExtras().getByteArray(PushConstants.EXTRA_CONTENT));
      LogUtil.getLogger().v("method="+method+",errorCode="+errorCode+",content="+content);
      if(method.equals(PushConstants.METHOD_BIND))
      {
        for(IEventCallback callback:IMApplication.getInstance().getCallback())
        {
          callback.onBind(context, errorCode, content);
        }
      }
    }else if(intent.getAction().equals(PushConstants.ACTION_RECEIVER_NOTIFICATION_CLICK))
    {
      String title=intent.getExtras().getString(PushConstants.EXTRA_NOTIFICATION_TITLE);
      String content=intent.getExtras().getString(PushConstants.EXTRA_NOTIFICATION_CONTENT);
    }else if(intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE"))
    {
      
    }
  }
  
  private void showNotificationMessage(Context context,IMMessage message)
  {
    mBuilder=new NotificationCompat.Builder(context);
    mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    mBuilder.setContentTitle("快聊");
    mBuilder.setContentText(message.getMessage());
    mBuilder.setContentIntent(getDefaultIntent(context,message));
    mBuilder.setTicker("新消息");
    mBuilder.setWhen(System.currentTimeMillis());
    mBuilder.setDefaults(Notification.DEFAULT_SOUND);
    mBuilder.setAutoCancel(true);
    mBuilder.setOnlyAlertOnce(true);
    mBuilder.setSmallIcon(R.drawable.icon);
    Notification notification=mBuilder.build();
    notification.flags|=Notification.FLAG_NO_CLEAR;
    mNotificationManager.notify(R.drawable.icon,notification);
  }
  
  private PendingIntent getDefaultIntent(Context context,IMMessage msg)
  {
    Intent intent=new Intent(context.getApplicationContext(),ChatActivity.class);
    intent.putExtra("user", new User(msg.getUserid(),msg.getChannelid(),msg.getNick()));
    intent.putExtra("msg", msg);
    PendingIntent pintent=PendingIntent.getActivity(context, 1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
    return pintent;
    
  }
}
