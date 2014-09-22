package com.yzy.im.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.FrameLayout;
import android.widget.PopupWindow.OnDismissListener;

import com.google.gson.Gson;
import com.yzy.im.IMApplication;
import com.yzy.im.R;
import com.yzy.im.adapter.TreeViewAdapter;
import com.yzy.im.bean.IMMessage;
import com.yzy.im.bean.User;
import com.yzy.im.callback.IEventCallback;
import com.yzy.im.config.IMConfiguration;
import com.yzy.im.customview.IphoneTreeView;
import com.yzy.im.customview.QuickAction;
import com.yzy.im.customview.QuickActionBar;
import com.yzy.im.customview.QuickActionWidget;
import com.yzy.im.model.PushAsyncTask;
import com.yzy.im.server.IConstants;
import com.yzy.im.server.IMManager;
import com.yzy.im.slidemenu.BaseSlidingFragmentActivity;
import com.yzy.im.slidemenu.SlidingMenu;
import com.yzy.im.util.CommonUtil;
import com.yzy.im.util.SharePreferenceHelper;
import com.yzy.im.util.ToastUtils;

@SuppressLint("NewApi")
public class MainActivity extends BaseSlidingFragmentActivity implements IEventCallback,OnDismissListener
{
  private static  ArrayList groups=null;
  private Map<Integer,List<User>> mChilds=new HashMap<Integer,List<User>>();
  private SlidingMenu mSlidingMenu;
  private IphoneTreeView xListView;
  private TreeViewAdapter adapter;
  private QuickActionWidget mQuickAction;
  
  
 

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    initView();
    //添加测试数据
    addGroupData();
    
    adapter=new TreeViewAdapter(this,groups, mChilds,xListView);
    
    //将自己加入到我的好友
    User user=new User(SharePreferenceHelper.getInstance(this).getUserId(),SharePreferenceHelper.getInstance(this).getChannelId(),SharePreferenceHelper.getInstance(this).getNick());
    mChilds.get(0).add(user);
    xListView.setAdapter(adapter);
    addLeft();
    String tmp=null;
    tmp.length();
  }
  
  public void initView()
  {
    xListView=(IphoneTreeView)this.findViewById(R.id.friend_xlistview);
    xListView.setGroupIndicator(null);
    
    xListView.setOnItemLongClickListener(new OnItemLongClickListener()
    {

      @Override
      public boolean onItemLongClick(AdapterView<?> parent, View view, int arg2, long arg3)
      {
        if(view.getTag() instanceof TreeViewAdapter.ViewHolder)
        {
          showQuickActionBarForChild(view.findViewById(R.id.icon));
        }else
        {
          showQuickActionBarForGroup(view.findViewById(R.id.group_indicator));
        }
        
        return true;
      }
    });
    
    xListView.setOnChildClickListener(new OnChildClickListener()
    {
      
      @Override
      public boolean onChildClick(ExpandableListView parent, View view, int groupPosition, int childPosition, long id)
      {
        Intent intent=new Intent(MainActivity.this,ChatActivity.class);
        User user=mChilds.get(groupPosition).get(childPosition);
        //判断该用户是否是自己
        if(!user.getUserId().equals(SharePreferenceHelper.getInstance(MainActivity.this).getUserId())){
          intent.putExtra("user", user);
          MainActivity.this.startActivity(intent);
        }
        
        return true;
      }
    });
  }
  
  private void showQuickActionBarForChild(View view)
  {
    if(mQuickAction==null)
    {
      mQuickAction=new QuickActionBar(this);
      mQuickAction.setOnDismissListener(this);
      mQuickAction.addQuickAction(new QuickAction(this, R.drawable.ic_action_share_pressed, CommonUtil.getStringById(R.string.open)));
      mQuickAction.addQuickAction(new QuickAction(this, R.drawable.ic_action_rename_pressed, CommonUtil.getStringById(R.string.rename)));
      mQuickAction.addQuickAction(new QuickAction(this, R.drawable.ic_action_move_pressed, CommonUtil.getStringById(R.string.move)));
      mQuickAction.addQuickAction(new QuickAction(this, R.drawable.ic_action_delete_pressed, CommonUtil.getStringById(R.string.delete)));
    }
   
    mQuickAction.show(view);
  }
  
  private void showQuickActionBarForGroup(View view)
  {
    if(mQuickAction==null)
    {
      mQuickAction=new QuickActionBar(this);
      mQuickAction.setArrowOffsetY(0);
      mQuickAction.setOnDismissListener(this);
      mQuickAction.addQuickAction(new QuickAction(this, R.drawable.ic_action_share_pressed, CommonUtil.getStringById(R.string.open)));
      mQuickAction.addQuickAction(new QuickAction(this, R.drawable.ic_action_rename_pressed, CommonUtil.getStringById(R.string.rename)));
      
    }
   
    mQuickAction.show(view);
  }
  
  @SuppressLint("NewApi")
  private void addLeft() {
    FrameLayout left = new FrameLayout(this);
    left.setId("LEFT".hashCode());
    setBehindContentView(left);
    getSupportFragmentManager()
    .beginTransaction()
    .replace("LEFT".hashCode(), new MenuFragment())
    .commit();
    
    SlidingMenu sm = getSlidingMenu();
    sm.setMode(SlidingMenu.LEFT);
    sm.setShadowDrawable(R.drawable.shadow);
    sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
   
    int screenWidth=CommonUtil.getScreenWidth(this);
    sm.setFadeEnabled(true);
    sm.setFadeDegree(0.35f);
    sm.setShadowWidth(screenWidth/40);
    sm.setBehindWidth((int)(screenWidth*0.5));
    sm.setBehindScrollScale(0.3333f);
    
  }
  

  @Override
  public void onMessage(IMMessage message)
  {
    //新用户消息
    if(message.getMessage().equals(IConstants.MSG_NEW_USER))
    {
      User user=new User(message.getUserid(),message.getChannelid(),message.getNick());
      if(!message.getUserid().equals(SharePreferenceHelper.getInstance(this).getUserId()))
      {
        mChilds.get(Integer.valueOf(user.getGroup())).add(user);
        adapter.notifyDataSetChanged();
        
        //发送回复
        IMMessage reply=new IMMessage(IConstants.MSG_NEW_USER_REPLY, "tag");
        PushAsyncTask task=new PushAsyncTask();
        task.execute(new Gson().toJson(reply),user.getUserId(),null);
      }
      
    }else if(message.getMessage().equals(IConstants.MSG_NEW_USER_REPLY))
    {
      //回复消息
      User user=new User(message.getUserid(),message.getChannelid(),message.getNick());
      if(!message.getUserid().equals(SharePreferenceHelper.getInstance(this).getUserId()))
      {
        mChilds.get(Integer.valueOf(user.getGroup())).add(user);
        adapter.notifyDataSetChanged();
      }
    }
  }


  @Override
  public void onNotify(String title, String content)
  {
    ToastUtils.AlertMessageInBottom("title-->"+title);
  }

  @Override
  public void onNetChange(boolean isNetConnected)
  {
  }

  @Override
  public void onBind(Context context, int errorCode, String content)
  {
  }

  @Override
  protected void onDestroy()
  {
    super.onDestroy();
  }
  
  private void addGroupData()
  {
    groups=new ArrayList<String>();
    String tmpgroup=IMConfiguration.getProperty("group");
    if(!TextUtils.isEmpty(tmpgroup))
    {
      int i=0;
      for(String tmp:tmpgroup.split("&"))
      {
        groups.add(tmp);
        mChilds.put(i++, new ArrayList());
      }
    }
  }

  
  @Override
  public void onDismiss()
  {
    if(mQuickAction!=null)
    {
      mQuickAction.clearAllQuickActions();
      mQuickAction=null;
    }
  }
  
  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    if(item.getItemId()==android.R.id.home)
    {
      toggle();
    }
    return super.onOptionsItemSelected(item);
  }
}
