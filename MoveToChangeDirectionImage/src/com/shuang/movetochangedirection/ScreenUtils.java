package com.shuang.movetochangedirection;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class ScreenUtils {
	
	public static DisplayMetrics getScreenWidth(Context context){
		WindowManager windowManager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics;
	}
}
