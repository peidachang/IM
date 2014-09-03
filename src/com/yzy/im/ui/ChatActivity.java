package com.yzy.im.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.gson.Gson;
import com.yzy.im.R;
import com.yzy.im.bean.IMMessage;
import com.yzy.im.bean.User;
import com.yzy.im.callback.IPushMessageCallback;
import com.yzy.im.model.PushAsyncTask;
import com.yzy.im.server.IConstants;
import com.yzy.im.server.IMManager;
import com.yzy.im.util.ToastUtils;

public class ChatActivity extends Activity implements OnClickListener
{
  private static final String TAG = "ChatActivity";
  private Button btnSend;
  private User user;
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.chat_layout);
    initView();
    
    user=(User) this.getIntent().getSerializableExtra("user");
  }
  
  private void initView()
  {
    btnSend=(Button)this.findViewById(R.id.send_btn);
    btnSend.setOnClickListener(this);
  }

  @Override
  public void onClick(View v)
  {
    IMMessage msg=new IMMessage("Gavin", "tag");
    PushAsyncTask task=new PushAsyncTask();
    task.execute(new Gson().toJson(msg),user.getUserId(),new IPushMessageCallback()
    {
      
      @Override
      public void onSuccess()
      {
        ToastUtils.AlertMessageInCenter("Good");
      }
      
      @Override
      public void onFailure()
      {
        ToastUtils.AlertMessageInBottom("Bad");
      }
    });
  }
}
