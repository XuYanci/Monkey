package com.example.yanci.core;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

/** 
 * 文件名称:   com.example.yanci.core.AsynImageLoader.java
 * 功能描述:  
 * 版本信息:   Copyright (c)2013 
 * 开发人员:   vincent
 * 版本日志:   1.0 
 * 创建时间:   2013年11月26日 上午10:34:27 
 * 
 * 修改历史: 
 * 时间         开发者      版本号    修改内容 
 * ------------------------------------------------------------------ 
 * 2013年11月26日   vincent      1.0         1.0 Version 
 */
@SuppressLint("DefaultLocale")
public class AsynImageLoader {
	/**
	 * 1，图片首先从缓存中读取，如果缓存中不存在，则继续步骤2
	 * 2，图片从sdcard中读取，如果没有则继续步骤3
	 * 3，从网络上下载图片，并加入缓存和sdcard中
	 * 	 a,下载图片时，使用线程池下载
	 */
	private static String TAG = "com.example.yanci.core.AsynImageLoader";
	/**
	 * 图片缓存
	 */
	private Map<String, SoftReference<Drawable>> imageCache = new HashMap<String, SoftReference<Drawable>>();
	/**
	 * 定义线程池，线程数量为5
	 */
	private ExecutorService executorService = Executors.newFixedThreadPool(5);
	
	/**
	 * sdcard初始路径
	 */
	private String path = Environment.getExternalStorageDirectory().getPath()+"/YUImagePath/image/";
	
	public void loadImage(final String imageUrl,final ImageLoadInterface callback){
		
		Log.d(TAG, "cache size "+imageCache.size());
		if(imageCache.containsKey(imageUrl)){
			//从缓存中取图片
			SoftReference<Drawable> softReference = imageCache.get(imageUrl);
			if(softReference.get()!=null){
				callback.loadImageCallback(imageCache.get(imageUrl).get());
				Log.d(TAG, "softReference");
				return;
			}
		}else if(getDrawableFromSDCard(imageUrl)!=null){
			//从sdcard中取图片
			callback.loadImageCallback(getDrawableFromSDCard(imageUrl));
			Log.d(TAG, "getDrawableFromSDCard");
			return;
		}
		//通过线程池从网络下载图片
		executorService.submit(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Drawable drawable = loadImageFromUrl(imageUrl);
				Log.d(TAG, "load image");
				if (drawable != null) {
					imageCache.put(imageUrl, new SoftReference<Drawable>(
							drawable));//加入缓存
					saveImageToSDCard(drawable, imageUrl);
					callback.loadImageCallback(drawable);
				}else{
					callback.loadImageCallback(null);
				}
			}

			private void saveImageToSDCard(Drawable drawable,String imageUrl){
				// TODO Auto-generated method stub
				String imagePath = path+imageUrl.substring(imageUrl.lastIndexOf("/")+1, imageUrl.length()).toLowerCase();
				File file = new File(imagePath);
				File directoryFile1 = new File(Environment.getExternalStorageDirectory().getPath() +"/YUImagePath/");
				File directoryFile = new File(path);
				boolean sdcradState = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
				if(sdcradState){
					if(!directoryFile1.exists()){
						directoryFile1.mkdir();
					}
					if(!directoryFile.exists()){
						directoryFile.mkdir();
					}
					if(!file.exists()){
						try {
							BitmapDrawable bd = (BitmapDrawable) drawable;
							Bitmap bm = bd.getBitmap();
							file.createNewFile();
							BufferedOutputStream bos = new BufferedOutputStream(
									new FileOutputStream(file));
							bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
							bos.flush();
							bos.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}

			private Drawable loadImageFromUrl(String imageUrl) {
				// TODO Auto-generated method stub
				try {
					URL url = new URL(imageUrl);
					InputStream in = url.openStream();
					Bitmap bitmap = BitmapFactory.decodeStream(in);
					return new BitmapDrawable(bitmap);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					return null;
				}
			}
		});
	}
	
	private Drawable getDrawableFromSDCard(String imageUrl){
		String sdCardPath = path+imageUrl.substring(imageUrl.lastIndexOf("/")+1, imageUrl.length()).toLowerCase();
		Bitmap bitmap = BitmapFactory.decodeFile(sdCardPath);
		File file = new File(sdCardPath);
		if(!file.exists()){
			return null;
		}
		if(bitmap != null && bitmap.toString().length() > 3){
			@SuppressWarnings("deprecation")
			Drawable drawable = new BitmapDrawable(bitmap);
			return drawable;
		}else{
			return null;
		}
		
	}
	
	public interface ImageLoadInterface{
		public void loadImageCallback(Drawable drawable);
	}
	
}
