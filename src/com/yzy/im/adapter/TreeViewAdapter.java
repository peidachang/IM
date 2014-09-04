package com.yzy.im.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzy.im.R;
import com.yzy.im.bean.User;
import com.yzy.im.customview.IphoneTreeView;
import com.yzy.im.customview.IphoneTreeView.IphoneTreeHeaderAdapter;
import com.yzy.im.util.LogUtil;

public class TreeViewAdapter extends BaseExpandableListAdapter implements IphoneTreeHeaderAdapter
{
  private static final String TAG = "TreeViewAdapter";
  private List<String> mGroup;// 组名
  private Map<Integer, List<User>> mChildren;// 每一组对应的child
  private Context mContext;
  private IphoneTreeView xListView;
  private HashMap<Integer, Integer> groupStatusMap = new HashMap<Integer, Integer>();
  
  public TreeViewAdapter(Context mContext,List<String> mGroup,Map<Integer,List<User>> mChildren,IphoneTreeView xListView)
  {
    this.mGroup=mGroup;
    this.mChildren=mChildren;
    this.mContext=mContext;
    this.xListView=xListView;
  }

  @Override
  public Object getChild(int groupPosition, int childPosition)
  {
    return mChildren.get(groupPosition).get(childPosition);
  }

  @Override
  public long getChildId(int groupPosition, int childPosition)
  {
    return childPosition;
  }

  @Override
  public View getChildView(int groupPosition, int childPosition,boolean isLastChild, View convertView, ViewGroup parent)
  {
    ViewHolder holder=null;
    if(convertView==null)
    {
      convertView=LayoutInflater.from(mContext).inflate(R.layout.friend_item, null);
      holder=new ViewHolder();
      holder.mImageView=(ImageView)convertView.findViewById(R.id.icon);
      holder.mTvUserId=(TextView)convertView.findViewById(R.id.tv_user_id);
      holder.mTvUserName=(TextView)convertView.findViewById(R.id.tv_uname);
      convertView.setTag(holder);
      convertView.setTag(R.string.groupid,groupPosition);
      convertView.setTag(R.string.childid,childPosition);
    }else
    {
      holder=(ViewHolder) convertView.getTag();
    }
    
    User user=(User) getChild(groupPosition, childPosition);
    holder.mTvUserName.setText(user.getNick());
    holder.mTvUserId.setText(user.getUserId());
    holder.mImageView.setImageResource(user.getHeadId());
    
    return convertView;
  }

  @Override
  public int getChildrenCount(int groupPosition)
  {
    if(mChildren.size()>groupPosition)
    {
      return mChildren.get(groupPosition).size();
    }
    return 0;
  }

  @Override
  public Object getGroup(int groupPosition)
  {
    return mChildren.get(groupPosition);
  }

  @Override
  public int getGroupCount()
  {
    return mGroup.size();
  }

  @Override
  public long getGroupId(int groupPosition)
  {
    return groupPosition;
  }

  @Override
  public View getGroupView(int groupPosition, boolean isExpanded,
      View convertView, ViewGroup parent)
  {
    //简单做法，公用一个ViewHolder类
    ViewHolder2 holder=null;
    if(convertView==null)
    {
      convertView=LayoutInflater.from(mContext).inflate(R.layout.friend_group, null);
      holder=new ViewHolder2();
      holder.mImageView=(ImageView)convertView.findViewById(R.id.group_indicator);
      holder.mTvGroupName=(TextView)convertView.findViewById(R.id.group_name);
      holder.mTvonLineNum=(TextView)convertView.findViewById(R.id.online_count);
      convertView.setTag(holder);
      convertView.setTag(R.string.groupid, groupPosition);
      
    }else
    {
      holder=(ViewHolder2) convertView.getTag();
    }
    
    holder.mTvGroupName.setText(mGroup.get(groupPosition));
    holder.mTvonLineNum.setText(mChildren.get(groupPosition).size()+"/"+mChildren.get(groupPosition).size());
    if(isExpanded)
    {
      holder.mImageView.setImageResource(R.drawable.indicator_expanded);
    }else
    {
      holder.mImageView.setImageResource(R.drawable.indicator_unexpanded);
    }
    return convertView;
  }

  @Override
  public boolean hasStableIds()
  {
    return true;
  }

  @Override
  public boolean isChildSelectable(int groupPosition, int childPosition)
  {
    return true;
  }

  @Override
  public int getTreeHeaderState(int groupPosition, int childPosition)
  {
    LogUtil.getLogger().d("head-->getTreeHeaderState");
    final int childCount = getChildrenCount(groupPosition);
    if (childPosition == childCount - 1) {
      return PINNED_HEADER_PUSHED_UP;
    } else if (childPosition == -1
        && !xListView.isGroupExpanded(groupPosition)) {
      return PINNED_HEADER_GONE;
    } else {
      return PINNED_HEADER_VISIBLE;
    }
  }

  @Override
  public void configureTreeHeader(View header, int groupPosition, int childPosition, int alpha)
  {
    LogUtil.getLogger().d("head-->configureTreeHeader");
  }

  @Override
  public void onHeadViewClick(int groupPosition, int status)
  {
    LogUtil.getLogger().d("head-->onHeadViewClick    status-->"+status);
    groupStatusMap.put(groupPosition, status);
  }

  @Override
  public int getHeadViewClickStatus(int groupPosition)
  {
    LogUtil.getLogger().d("head-->getHeadViewClickStatus");
    if (groupStatusMap.containsKey(groupPosition)) {
      return groupStatusMap.get(groupPosition);
    } else {
      return 0;
    }
  }
  
  
  public static class ViewHolder
  {
    public ImageView mImageView;
    public TextView mTvUserName;
    public TextView mTvUserId;
  }
  
  public static class ViewHolder2
  {
    public ImageView mImageView;
    public TextView mTvGroupName;
    public TextView mTvonLineNum;
  }
  
  
}
