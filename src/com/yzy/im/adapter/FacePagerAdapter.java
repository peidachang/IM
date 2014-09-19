package com.yzy.im.adapter;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;

import com.yzy.im.customview.JazzyViewPager;

public class FacePagerAdapter extends PagerAdapter
{
  private static final String TAG = "FacePagerAdapter";
  private List<View> views;
  private JazzyViewPager mViewPager;
  
  public FacePagerAdapter(List<View> views,JazzyViewPager mViewPager)
  {
    this.views=views;
    this.mViewPager=mViewPager;
  }

  @Override
  public int getCount()
  {
    return views.size();
  }

  
  
  @Override
  public Object instantiateItem(ViewGroup container, int position)
  {
    ((ViewPager)container).addView(views.get(position),0);
    mViewPager.setObjectForPosition(views.get(position), position);
    return views.get(position);
  }
  
  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    // TODO Auto-generated method stub
    ((ViewPager)container).removeView(views.get(position));
  }
  
  
  @Override
  public boolean isViewFromObject(View arg0, Object arg1) {
    // TODO Auto-generated method stub
    
    return (arg0 == arg1);
  }
  
  
}
