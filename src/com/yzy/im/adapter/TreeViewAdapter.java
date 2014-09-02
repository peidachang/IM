package com.yzy.im.adapter;

import java.util.List;
import java.util.Map;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.yzy.im.bean.User;
import com.yzy.im.customview.IphoneTreeView.IphoneTreeHeaderAdapter;

public class TreeViewAdapter extends BaseExpandableListAdapter implements IphoneTreeHeaderAdapter
{
  private static final String TAG = "TreeViewAdapter";
  private List<String> mGroup;// 组名
  private Map<Integer, List<User>> mChildren;// 每一组对应的child
  
  public TreeViewAdapter(List<String> mGroup,Map<Integer,List<User>> mChildren)
  {
    this.mGroup=mGroup;
    this.mChildren=mChildren;
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
    return null;
  }

  @Override
  public int getChildrenCount(int arg0)
  {
    return 0;
  }

  @Override
  public Object getGroup(int groupPosition)
  {
    return null;
  }

  @Override
  public int getGroupCount()
  {
    return 0;
  }

  @Override
  public long getGroupId(int groupPosition)
  {
    return 0;
  }

  @Override
  public View getGroupView(int groupPosition, boolean isExpanded,
      View convertView, ViewGroup parent)
  {
    return null;
  }

  @Override
  public boolean hasStableIds()
  {
    return false;
  }

  @Override
  public boolean isChildSelectable(int groupPosition, int childPosition)
  {
    return false;
  }

  @Override
  public int getTreeHeaderState(int groupPosition, int childPosition)
  {
    return 0;
  }

  @Override
  public void configureTreeHeader(View header, int groupPosition, int childPosition, int alpha)
  {
  }

  @Override
  public void onHeadViewClick(int groupPosition, int status)
  {
  }

  @Override
  public int getHeadViewClickStatus(int groupPosition)
  {
    return 0;
  }
  
}
