package com.yzy.im.ui;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.yzy.im.IMApplication;
import com.yzy.im.R;
import com.yzy.im.adapter.FacePagerAdapter;
import com.yzy.im.adapter.PerPageAdapter;
import com.yzy.im.bean.IMMessage;
import com.yzy.im.bean.User;
import com.yzy.im.callback.IPushMessageCallback;
import com.yzy.im.customview.CirclePageIndicator;
import com.yzy.im.customview.JazzyViewPager;
import com.yzy.im.model.PushAsyncTask;
import com.yzy.im.util.LogUtil;
import com.yzy.im.util.ToastUtils;

public class ChatActivity extends Activity implements OnClickListener
{
  private static final String TAG = "ChatActivity";
  private Button btnSend;
  private User user;
  private ImageButton mImgBtn;
  private View face_panel;
  
  private JazzyViewPager mViewPager;
  private CirclePageIndicator mPageIndicator;
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
    mImgBtn=(ImageButton)this.findViewById(R.id.face_btn);
    face_panel=this.findViewById(R.id.face_ll);
    btnSend.setOnClickListener(this);
    mImgBtn.setOnClickListener(this);
    
    mViewPager=(JazzyViewPager)this.findViewById(R.id.face_pager);
    mPageIndicator=(CirclePageIndicator)this.findViewById(R.id.indicator);
    initFacePanel();
  }
  
  private void initFacePanel()
  {
     HashMap<String,Integer> mFaceMap=IMApplication.getInstance().getFaceMap();
     int size=mFaceMap.size();
     LogUtil.getLogger().i("size-->mFaceMap size is"+size);
     int page=(size%21==0)?(size/21):(size/21+1);
     LogUtil.getLogger().i("size-->page size is"+page);
     ArrayList<View> views=new ArrayList<View>();
     for(int i=0;i<page;i++)
     {
       LogUtil.getLogger().d("i-->"+i);
       View view=LayoutInflater.from(this).inflate(R.layout.face_layout, null);
       GridView grid=(GridView) view.findViewById(R.id.grid);
       LogUtil.getLogger().d("grid-->"+grid);
       ArrayList<Integer>  tmp=new ArrayList<Integer>();
       for(int j=i*21;j<(i+1)*21 && j<mFaceMap.size();j++)
       {
         LogUtil.getLogger().d("j-->"+j);
         tmp.add(mFaceMap.get(j));
       }
       PerPageAdapter adapter=new PerPageAdapter(this,tmp);
       grid.setAdapter(adapter);
       views.add(view);
     }
     FacePagerAdapter fAdapter=new FacePagerAdapter(views, mViewPager);
     mViewPager.setAdapter(fAdapter);
     mPageIndicator.setViewPager(mViewPager, 0);
    
  }

  @Override
  public void onClick(View v)
  {
    if(v.getId()==R.id.send_btn)
    {
      sendMessage();
    }else if(v.getId()==R.id.face_btn)
    {
      if(face_panel!=null)
        face_panel.setVisibility(View.VISIBLE);
    }
    
  }

  private void sendMessage()
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
