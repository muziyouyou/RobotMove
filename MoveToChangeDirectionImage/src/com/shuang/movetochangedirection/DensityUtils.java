package com.shuang.movetochangedirection;

import android.content.Context;

public class DensityUtils {

	public static int dpToPx(Context context, float dpValue) {  
		final float scale = context.getResources().getDisplayMetrics().density;  
		return (int) (dpValue * scale + 0.5f); 
	}  


	public static int pxToDp(Context context, float pxValue) {  
		final float scale = context.getResources().getDisplayMetrics().density;  
		return (int) (pxValue / scale + 0.5f);  
	}  

}
