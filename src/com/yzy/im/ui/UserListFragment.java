package com.yzy.im.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
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
import com.yzy.im.util.CommonUtil;
import com.yzy.im.util.LogUtil;
import com.yzy.im.util.SharePreferenceHelper;

public class UserListFragment extends Fragment implements IEventCallback,OnDismissListener,OnClickListener
{
  private static final String TAG = "UserListFragment";
  
  private static  ArrayList groups=null;
  private Map<Integer,List<User>> mChilds=new HashMap<Integer,List<User>>();
  private IphoneTreeView xListView;
  private TreeViewAdapter adapter;
  private QuickActionWidget mQuickAction;
  private Button btnAddFriend;
  
  
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    LogUtil.getLogger().d("life->UserListFragment-->onCreate()");
    //在Fragment里面加入ActionItem，必须加入这一句
    setHasOptionsMenu(true);
    IMApplication.getInstance().addEventCallback(this);
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
  {
    return inflater.inflate(R.layout.userlist_layout , container,false);
  }
  
  @Override
  public void onViewCreated(View view, Bundle savedInstanceState)
  {
    super.onViewCreated(view, savedInstanceState);
    initView();
    addGroupData();
    
    adapter=new TreeViewAdapter(this.getActivity(),groups, mChilds,xListView);
    
    //将自己加入到我的好友
    User user=new User(SharePreferenceHelper.getInstance(this.getActivity()).getUserId(),SharePreferenceHelper.getInstance(this.getActivity()).getChannelId(),SharePreferenceHelper.getInstance(this.getActivity()).getNick());
    mChilds.get(0).add(user);
    xListView.setAdapter(adapter);
  }
  
  public void initView()
  {
    xListView=(IphoneTreeView)this.getActivity().findViewById(R.id.friend_xlistview);
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
        Intent intent=new Intent(UserListFragment.this.getActivity(),ChatActivity.class);
        User user=mChilds.get(groupPosition).get(childPosition);
        //判断该用户是否是自己
        if(!user.getUserId().equals(SharePreferenceHelper.getInstance(UserListFragment.this.getActivity()).getUserId())){
          intent.putExtra("user", user);
          UserListFragment.this.getActivity().startActivity(intent);
        }
        
        return true;
      }
    });
  }
  
  private void showQuickActionBarForChild(View view)
  {
    if(mQuickAction==null)
    {
      mQuickAction=new QuickActionBar(this.getActivity());
      mQuickAction.setOnDismissListener(this);
      mQuickAction.addQuickAction(new QuickAction(this.getActivity(), R.drawable.ic_action_share_pressed, CommonUtil.getStringById(R.string.open)));
      mQuickAction.addQuickAction(new QuickAction(this.getActivity(), R.drawable.ic_action_rename_pressed, CommonUtil.getStringById(R.string.rename)));
      mQuickAction.addQuickAction(new QuickAction(this.getActivity(), R.drawable.ic_action_move_pressed, CommonUtil.getStringById(R.string.move)));
      mQuickAction.addQuickAction(new QuickAction(this.getActivity(), R.drawable.ic_action_delete_pressed, CommonUtil.getStringById(R.string.delete)));
    }
   
    mQuickAction.show(view);
  }
  
  private void showQuickActionBarForGroup(View view)
  {
    if(mQuickAction==null)
    {
      mQuickAction=new QuickActionBar(this.getActivity());
      mQuickAction.setArrowOffsetY(0);
      mQuickAction.setOnDismissListener(this);
      mQuickAction.addQuickAction(new QuickAction(this.getActivity(), R.drawable.ic_action_share_pressed, CommonUtil.getStringById(R.string.open)));
      mQuickAction.addQuickAction(new QuickAction(this.getActivity(), R.drawable.ic_action_rename_pressed, CommonUtil.getStringById(R.string.rename)));
      
    }
   
    mQuickAction.show(view);
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
    mQuickAction.clearAllQuickActions();
    mQuickAction=null;
  }

  @Override
  public void onMessage(IMMessage message)
  {
  //新用户消息
    if(message.getMessage().equals(IConstants.MSG_NEW_USER))
    {
      User user=new User(message.getUserid(),message.getChannelid(),message.getNick());
      if(!message.getUserid().equals(SharePreferenceHelper.getInstance(this.getActivity()).getUserId()))
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
      if(!message.getUserid().equals(SharePreferenceHelper.getInstance(this.getActivity()).getUserId()))
      {
        mChilds.get(Integer.valueOf(user.getGroup())).add(user);
        adapter.notifyDataSetChanged();
      }
    }
  }

  @Override
  public void onBind(Context context, int errorCode, String content)
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
  public void onDestroy()
  {
    IMApplication.getInstance().removeEventCallback(this);
    LogUtil.getLogger().d("life->UserListFragment-->onDestroy()");
    super.onDestroy();
  }
  
  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
  {
    inflater.inflate(R.menu.user_list, menu);  
    MenuItem item=menu.findItem(R.id.action_add_friend);
    btnAddFriend=(Button)MenuItemCompat.getActionView(item).findViewById(R.id.action_right);
    btnAddFriend.setText(R.string.scan_scan);
    btnAddFriend.setOnClickListener(this);
    btnAddFriend.setBackgroundResource(R.drawable.login_button_bg);
    super.onCreateOptionsMenu(menu, inflater);  
  }

  @Override
  public void onClick(View v)
  {
    if(v==btnAddFriend)
    {
      Intent intent=new Intent(this.getActivity(),CaptureActivity.class);
      this.startActivity(intent);
    }
  }
}
