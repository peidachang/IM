package com.yzy.im.ui;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.google.gson.Gson;
import com.yzy.im.IMApplication;
import com.yzy.im.R;
import com.yzy.im.bean.IMMessage;
import com.yzy.im.callback.IEventCallback;
import com.yzy.im.callback.IPushMessageCallback;
import com.yzy.im.customview.ClearEditText;
import com.yzy.im.model.PushAsyncTask;
import com.yzy.im.server.IConstants;
import com.yzy.im.util.CommonUtil;
import com.yzy.im.util.DialogUtils;
import com.yzy.im.util.LogUtil;
import com.yzy.im.util.ToastUtils;

public class LoginActivity extends Activity implements OnClickListener,IEventCallback
{
  private static final String TAG = "LoginActivity";
  private ClearEditText mName;
  private ClearEditText mpwd;
  private Button btnLogin;
  private ProgressDialog dialog;
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    this.setContentView(R.layout.login_layout);
    initView();
    IMApplication.getInstance().getCallback().add(this);
  }
  
  private void initView()
  {
    mName=(ClearEditText)this.findViewById(R.id.username);
    mpwd=(ClearEditText)this.findViewById(R.id.password);
    btnLogin=(Button)this.findViewById(R.id.login);
    btnLogin.setOnClickListener(this);
  }

  @Override
  public void onClick(View view)
  {
    if(view.getId()==R.id.login)
    {
      if(mName.getText().length()==0 || mpwd.getText().length()==0)
      {
        ToastUtils.AlertMessageInBottom(R.string.name_pwd_notnull);
        return ;
      }else
      {
        IMApplication.getInstance().getSharePreference().setNick(mName.getText().toString());
        IMApplication.getInstance().getSharePreference().setHeadId(R.drawable.h0);
        PushManager.startWork(this, PushConstants.LOGIN_TYPE_API_KEY,IConstants.mapikey);
        dialog=DialogUtils.createProcgressDialog(this,CommonUtil.getStringById(R.string.login_loading));
      }
      
    }
  }

  @Override
  public void onMessage(IMMessage message)
  {
  }



  @Override
  public void onNotify(String title, String content)
  {
  }

  @Override
  public void onNetChange(boolean isNetConnected)
  {
  }

  @Override
  public void onBind(Context context, int errorCode, String content)
  {
    if(dialog!=null)
    {
      dialog.dismiss();
    }
    if(errorCode==0)
    {
      LogUtil.getLogger().v(content);
      try
      {
        JSONObject json=new JSONObject(content);
        JSONObject child=json.getJSONObject("response_params");
        String appid=child.getString("appid");
        String channel_id=child.getString("channel_id");
        String user_id=child.getString("user_id");
        
        IMApplication.getInstance().getSharePreference().setChannelId(channel_id);
        IMApplication.getInstance().getSharePreference().setUserId(user_id);
        IMApplication.getInstance().getSharePreference().setAppId(user_id);
        
        //通知其他用户，你已经登录成功
        IMMessage msg=new IMMessage("hello", "tag");
        PushAsyncTask task=new PushAsyncTask();
        task.execute(new Gson().toJson(msg),"",new IPushMessageCallback()
        {
          
          @Override
          public void onSuccess()
          {
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            LoginActivity.this.startActivity(intent);
          }
          
          @Override
          public void onFailure()
          {
            ToastUtils.AlertMessageInBottom("Login Failure");
          }
        });
      } catch (JSONException e)
      {
        LogUtil.getLogger().i("error-->"+e.getMessage());
        e.printStackTrace();
      }
      
    }else
    {
      
    }
  }
  
  @Override
  protected void onDestroy()
  {
    IMApplication.getInstance().getCallback().remove(this);
    super.onDestroy();
  }


}
