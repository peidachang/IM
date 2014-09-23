package com.yzy.im.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import com.yzy.im.R;
import com.yzy.im.adapter.TreeViewAdapter;
import com.yzy.im.bean.User;
import com.yzy.im.customview.IphoneTreeView;
import com.yzy.im.customview.QuickAction;
import com.yzy.im.customview.QuickActionBar;
import com.yzy.im.customview.QuickActionWidget;
import com.yzy.im.util.CommonUtil;
import com.yzy.im.util.SharePreferenceHelper;

public class UserListFragment extends Fragment
{
  private static final String TAG = "UserListFragment";
  
  private static  ArrayList groups=null;
  private Map<Integer,List<User>> mChilds=new HashMap<Integer,List<User>>();
  private IphoneTreeView xListView;
  private TreeViewAdapter adapter;
  private QuickActionWidget mQuickAction;
  
  
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
  {
    return inflater.inflate(R.layout.userlist_layout , container,false);
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
      mQuickAction.setOnDismissListener((MainActivity)this.getActivity());
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
      mQuickAction.setOnDismissListener((MainActivity)this.getActivity());
      mQuickAction.addQuickAction(new QuickAction(this.getActivity(), R.drawable.ic_action_share_pressed, CommonUtil.getStringById(R.string.open)));
      mQuickAction.addQuickAction(new QuickAction(this.getActivity(), R.drawable.ic_action_rename_pressed, CommonUtil.getStringById(R.string.rename)));
      
    }
   
    mQuickAction.show(view);
  }
}
