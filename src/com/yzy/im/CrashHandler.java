package com.yzy.im;

import java.lang.Thread.UncaughtExceptionHandler;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Looper;

import com.yzy.im.bug.sender.EmailSender;
import com.yzy.im.util.LogUtil;
import com.yzy.im.util.ToastUtils;

public class CrashHandler implements UncaughtExceptionHandler
{
  private static final String TAG = "CrashHandler";
  private Thread.UncaughtExceptionHandler mDefaultHandler;
  private static CrashHandler instance;
  private Context mContext;
  
  private CrashHandler()
  {
    
  }
  
  public static CrashHandler getInstance()
  {
    if(instance==null)
    {
      return instance=new CrashHandler();
    }
    return instance;
  }
  
  public void init(Context mContext)
  {
    this.mContext=mContext;
    
    mDefaultHandler=Thread.getDefaultUncaughtExceptionHandler();
    Thread.setDefaultUncaughtExceptionHandler(this);
  }

  @Override
  public void uncaughtException(Thread thread, Throwable ex)
  {
    if (!handleException(ex) && mDefaultHandler != null) 
    {
      mDefaultHandler.uncaughtException(thread, ex);
    }else
    {
      try {  
        Thread.sleep(3000);  
    } catch (InterruptedException e) {  
        e.printStackTrace();
    }  
    //退出程序  
    IMApplication.getInstance().finishActivity();
    android.os.Process.killProcess(android.os.Process.myPid());  
    System.exit(1);  
    }

  }
  
  public boolean handleException(Throwable ex)
  {
    if(ex==null ||mContext==null)
      return false;
    
    final String report=getCrashReport(mContext, ex);
    LogUtil.getLogger().e(report);
    new Thread()
    {
      public void run() 
      {
        try {
          EmailSender sender = new EmailSender();
          //设置服务器地址和端口，网上搜的到
          sender.setProperties("smtp.163.com", "25");
          //分别设置发件人，邮件标题和文本内容
          sender.setMessage("hellogavin2008@163.com", "IM Bug",report);
          //设置收件人
          sender.setReceiver(new String[]{"717402738@qq.com"});
          //发送邮件
          sender.sendEmail("smtp.163.com", "hellogavin2008@163.com", "hellogavin");
        } catch (AddressException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (MessagingException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        
        Looper.prepare();
        ToastUtils.AlertMessageInCenter("程序即将崩溃,我已经将崩溃信息发至我的邮箱,便于尽快解决该崩溃！");
        Looper.loop();
      };
    }.start();
    return true;
  }
  
  /**
  * 获取APP崩溃异常报告
  * 
  * @param ex
  * @return
  */
  private String getCrashReport(Context context, Throwable ex) 
  {
      PackageInfo pinfo = getPackageInfo(context);
      StringBuffer exceptionStr = new StringBuffer();
      exceptionStr.append("Version: " + pinfo.versionName + "("
      + pinfo.versionCode + ")\n");
      exceptionStr.append("Android: " + android.os.Build.VERSION.RELEASE
      + "(" + android.os.Build.MODEL + ")\n");
      exceptionStr.append("Exception: " + ex.getMessage() + "\n");
      StackTraceElement[] elements = ex.getStackTrace();
      for (int i = 0; i < elements.length; i++) {
      exceptionStr.append(elements[i].toString() + "\n");
      }
      return exceptionStr.toString();
  }
  
  /**
  * 获取App安装包信息
  * 
  * @return
  */
  private PackageInfo getPackageInfo(Context context) 
  {
    PackageInfo info = null;
    try 
    {
    info = context.getPackageManager().getPackageInfo(
    context.getPackageName(), 0);
    } catch (NameNotFoundException e) {
    e.printStackTrace(System.err);
    }
    if (info == null)
    info = new PackageInfo();
    return info;
  }
   
}
