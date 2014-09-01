package com.yzy.im.util;

import com.yzy.im.IMApplication;

import android.view.Gravity;
import android.widget.Toast;

public class ToastUtils {

	// Toast框提示，信息居中显示
	public static void AlertMessageInCenter(int msg) {
		IMApplication app = IMApplication.getInstance();
		String message = app.getString(msg);
		Toast toast = Toast.makeText(app, message, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	// Toast框提示,信息居下显示
	public static void AlertMessageInBottom(int msg) {
	  IMApplication app = IMApplication.getInstance();
		String message = app.getString(msg);
		Toast toast = Toast.makeText(app, message, Toast.LENGTH_SHORT);
		toast.show();
	}

	// Toast框提示，信息居中显示
	public static void AlertMessageInCenter(String msg) {
	  IMApplication app = IMApplication.getInstance();
		Toast toast = Toast.makeText(app, msg, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	// Toast框提示,信息居下显示
	public static void AlertMessageInBottom(String msg) {
	  IMApplication app = IMApplication.getInstance();
		Toast toast = Toast.makeText(app, msg, Toast.LENGTH_SHORT);
		toast.show();
	}

}
