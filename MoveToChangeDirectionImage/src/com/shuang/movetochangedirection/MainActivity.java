package com.shuang.movetochangedirection;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MainActivity extends Activity {

	@ViewInject(R.id.iv_jump_me)
	private ImageView iv_jump_me;
	@ViewInject(R.id.fl_jump_back)
	private FrameLayout fl_jump_back;
	private DisplayMetrics screenWidth;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ViewUtils.inject(this);
		screenWidth = ScreenUtils.getScreenWidth(getApplicationContext());
		//����ҳ�汳��
		Bitmap bitmap=Bitmap.createBitmap(screenWidth.widthPixels, screenWidth.heightPixels, Bitmap.Config.ARGB_8888);
		BitmapDrawable drawable = new BitmapDrawable(bitmap);
		fl_jump_back.setBackgroundDrawable(drawable);
		//���õ���¼�
		iv_jump_me.setOnTouchListener(new OnTouchListener() {
			private int startx;
			private int starty;
			private int kx;
			private int ky;
			private int prex;
			private int prey;
			private Bitmap bitmap;
			private float asin=0;
			private double atan=0;
			private boolean flagc=false;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:

					startx = (int) event.getRawX();
					starty = (int) event.getRawY();
					kx=startx;
					ky=starty;
					break;

				case MotionEvent.ACTION_MOVE:

					int endx=(int) event.getRawX();
					int endy=(int) event.getRawY();


					//����б��
					try{
						atan=(endy-ky)/(endx-kx);
					}catch (Exception e) {
						atan=-404;
					}
					//�ж��Ƿ�����
					boolean flag=false;
					//������ת�Ƕ�
					if(atan==-404){//��ֱ����ת
						if(endy>ky){
							asin=180;
						}else{
							asin=0;
						}

					}else if(atan==0){
						if(endx>kx){
							asin=90;
						}else{
							asin=-90;
						}
					}else{
						flag=true;
						asin =(float) Math.toDegrees(Math.atan(atan));
					}
					//��λ�Ƕ�
					if(flag){
						//��������
						if(endy>starty){
							if(atan>0){
								asin+=90;
							}else{
								asin-=90;
							}
						}else{
							if(atan>0){
								asin=-asin;
							}else{
								asin=-asin;
							}
						}
					}

					bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
					//1,������ģ��
					Bitmap bitmapFu = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
					//2,Ϊ��ģ�崴������
					Canvas canvas = new Canvas(bitmapFu);
					//3,������ת����
					Matrix mx=new Matrix();
					mx.setRotate(asin, bitmap.getWidth()/2, bitmap.getHeight()/2);
					//4,���û���
					Paint p=new Paint();
					canvas.drawBitmap(bitmap, mx, p);
					//5,����ͼƬ
					iv_jump_me.setImageBitmap(bitmapFu);
					/////////////////////////////////////////////////////
					//���ӵĸ߶�
					int linehigh=screenWidth.heightPixels-dpTopx(143);
					//�����յ�
					int lineEndY=iv_jump_me.getTop()+iv_jump_me.getHeight();//��
					int lineEndX=iv_jump_me.getLeft()+iv_jump_me.getWidth()/2;

					if(endy>screenWidth.heightPixels-dpTopx(160)){

						Bitmap bitmap2=Bitmap.createBitmap(screenWidth.widthPixels, screenWidth.heightPixels, Bitmap.Config.ARGB_8888);
						Canvas canvas2 = new Canvas(bitmap2);
						Paint p2=new Paint();
						p2.setColor(Color.BLUE);
						p2.setStrokeWidth(10f);
						canvas2.drawLine(0, linehigh, lineEndX, lineEndY, p2);
						canvas2.drawLine(screenWidth.widthPixels, linehigh, lineEndX, lineEndY, p2);
						BitmapDrawable drawable = new BitmapDrawable(bitmap2);
						fl_jump_back.setBackgroundDrawable(drawable);
						flagc=true;
					}else{
						flagc=false;
					}
					/////////////////////////////////////////////////////
					//6,�ƶ�λ��
					float divX=endx-startx;
					float divY=endy-starty;
					//
					int top = iv_jump_me.getTop();
					int left = iv_jump_me.getLeft();
					//4.2�ƶ���Ӧ��ƫ����
					left+=divX;
					top+=divY;
					int right=left+iv_jump_me.getWidth();
					int bottom=top+iv_jump_me.getHeight();
					iv_jump_me.layout(left,top,right, bottom);
					/////////////////////////////////////////////////////
					//7,�任��ʼλ��
					prex=startx;
					prey=starty;
					kx=endx;
					ky=endy;
					startx=endx;
					starty=endy;
					break;
				case MotionEvent.ACTION_UP:

					if(flagc){
						if(asin==180){//��ֱ
							asin=0;
							bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
							//1,������ģ��
							Bitmap bitmapFu3= Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
							//2,Ϊ��ģ�崴������
							Canvas canvas3 = new Canvas(bitmapFu3);
							//3,������ת����
							Matrix mx3=new Matrix();
							mx3.setRotate(asin, bitmap.getWidth()/2, bitmap.getHeight()/2);
							//4,���û���
							Paint p3=new Paint();
							canvas3.drawBitmap(bitmap, mx3, p3);
							//5,����ͼƬ
							iv_jump_me.setImageBitmap(bitmapFu3);
						}else{//�Ǵ�ֱ
							int endx3=(int) event.getRawX();
							if(endx3<startx){//����
								//�ı�ת��
								asin=180+asin;
							}else{//����
								asin=asin-180;
							}

							bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
							//1,������ģ��
							Bitmap bitmapFu3= Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
							//2,Ϊ��ģ�崴������
							Canvas canvas3 = new Canvas(bitmapFu3);
							//3,������ת����
							Matrix mx3=new Matrix();
							mx3.setRotate(asin, bitmap.getWidth()/2, bitmap.getHeight()/2);
							//4,���û���
							Paint p3=new Paint();
							canvas3.drawBitmap(bitmap, mx3, p3);
							//5,����ͼƬ
							iv_jump_me.setImageBitmap(bitmapFu3);
							//�ƶ�
							/**
							 * 1,����,0,����,-1�Ǵ�ֱ,-2����
							 */
							int redirect=-2;
							if(asin==180){//��ֱ
								redirect=-1;
							}else if(asin==90||asin==-90){//����
								redirect=-2;
							}else{//����
								if(endx3>prex){//����
									redirect=1;
								}else if(endx3<prex){//����
									redirect=0;
								}
							}
							startPosition(atan,redirect);
						}
					}
					break;
				}
				return true;
			}
		});

	}

	/**
	 * б�ʷ���
	 * @param atan
	 * @param redirect
	 */
	public void startPosition(final double atan,final int redirect){

		new Thread(new Runnable() {
			int y;
			int x;
			@Override
			public void run() {
				y=iv_jump_me.getTop();
				x=iv_jump_me.getLeft();
				/**
				 * 1,����,0,����,-1�Ǵ�ֱ,-2����
				 */
				switch (redirect) {
				case 1://����
					System.out.println("����1");
					moveLeft(atan);
					break;
				case 0://����
					break;
				case -1://��ֱ
					moveHigh();
					break;
				case -2://����
					break;	
				}

			}
			
			private void moveHigh() {
				
				while(y>50){
					//�޸�ͼƬλ��
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							iv_jump_me.layout(x,y,(int) (x+iv_jump_me.getWidth()),y+iv_jump_me.getHeight());
						}
					});
					//˯��
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//��ȥ����
					System.out.println("y=="+y);
					y=y-100;
				}
			}
			//����
			private void moveLeft(final double atan) {
				while(y>50){
					//�޸�ͼƬλ��
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							double x=(double) (y/atan);
							//�������
							double bili=screenWidth.heightPixels/screenWidth.widthPixels;
							x=x/bili;
							System.out.println("x="+x);
							iv_jump_me.layout((int)x,y,(int) (x+iv_jump_me.getWidth()),y+iv_jump_me.getHeight());
						}
					});
					//˯��
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//��ȥ����
					y=y-100;
				}
			}
		}).start();
	}



	public int dpTopx(int dp){
		int dpToPx = DensityUtils.dpToPx(getApplicationContext(), dp);
		return dpToPx;
	}

}
