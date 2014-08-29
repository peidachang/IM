package com.yzy.im.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yzy.im.R;

public class MenuFragment extends Fragment
{
  private static final String TAG = "MenuFragment";
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
  {
    View view=inflater.inflate(R.layout.menufragment , container,false);
    return view;
  }
}
