package com.yzy.im.ui;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.google.gson.Gson;
import com.yzy.im.AppManager;
import com.yzy.im.IMApplication;
import com.yzy.im.R;
import com.yzy.im.annotation.InjectView;
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

public class LoginActivity extends BaseActivity implements OnClickListener,IEventCallback
{
  private static final String TAG = "LoginActivity";
  @InjectView(id=R.id.username)
  private ClearEditText mName;
  
  @InjectView(id=R.id.password)
  private ClearEditText mpwd;
  
  @InjectView(id=R.id.login,click="onClickLogin")
  private Button btnLogin;
  
  private Button btnRegister;
  private ProgressDialog dialog;
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.login_layout);
    this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }
  

  @Override
  public void onClick(View view)
  {
    if(view==btnRegister)
    {
      ToastUtils.AlertMessageInBottom("注册");
    }
  }
  
  public void onClickLogin(View view)
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
  

  @Override
  public void onBind(Context context, int errorCode, String content)
  {
    
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
        IMMessage msg=new IMMessage(IConstants.MSG_NEW_USER, "tag");
        PushAsyncTask task=new PushAsyncTask();
        task.execute(new Gson().toJson(msg),"",new IPushMessageCallback()
        {
          
          @Override
          public void onSuccess()
          {
            if(dialog!=null)
            {
              dialog.dismiss();
            }
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            LoginActivity.this.startActivity(intent);
          }
          
          @Override
          public void onFailure()
          {
            if(dialog!=null)
            {
              dialog.dismiss();
            }
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
    super.onDestroy();
  }
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    getMenuInflater().inflate(R.menu.login, menu);
    MenuItem item=menu.findItem(R.id.action_register);
    btnRegister=(Button)MenuItemCompat.getActionView(item).findViewById(R.id.action_right);
    btnRegister.setText(R.string.register);
    btnRegister.setBackgroundResource(R.drawable.login_button_bg);
    btnRegister.setOnClickListener(this);
    return super.onCreateOptionsMenu(menu);
  }
  
  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    switch(item.getItemId())
    {
      case android.R.id.home:
        AppManager.getInstance().exitApp(false);
        return true;
    }
    return super.onOptionsItemSelected(item);
  }


}
