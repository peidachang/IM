package com.yzy.im.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.yzy.im.R;
import com.yzy.im.adapter.TreeViewAdapter;
import com.yzy.im.bean.User;
import com.yzy.im.customview.IphoneTreeView;
import com.yzy.im.customview.QuickActionWidget;
import com.yzy.im.slidemenu.BaseSlidingFragmentActivity;
import com.yzy.im.slidemenu.SlidingMenu;
import com.yzy.im.util.CommonUtil;

@SuppressLint("NewApi")
public class MainActivity extends BaseSlidingFragmentActivity 
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
    this.setSlidingActionBarEnabled(true);
    replaceRight(new UserListFragment());
    addLeft();
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
    
    mSlidingMenu = getSlidingMenu();
    mSlidingMenu.setMode(SlidingMenu.LEFT);
    mSlidingMenu.setShadowDrawable(R.drawable.shadow);
    mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
   
    int screenWidth=CommonUtil.getScreenWidth(this);
    mSlidingMenu.setFadeEnabled(true);
    mSlidingMenu.setFadeDegree(0.35f);
    mSlidingMenu.setShadowWidth(screenWidth/40);
    mSlidingMenu.setBehindWidth((int)(screenWidth*0.5));
    mSlidingMenu.setBehindScrollScale(0.3333f);
  }
  
  
  public void replaceRight(Fragment fragment)
  {
    getSupportFragmentManager()
    .beginTransaction()
    .replace(R.id.frame_contenet,fragment)
    .commit();
  }
  


  @Override
  protected void onDestroy()
  {
    super.onDestroy();
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
