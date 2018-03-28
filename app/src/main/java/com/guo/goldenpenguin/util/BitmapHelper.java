package com.guo.goldenpenguin.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 图片缩放处理类
 * 
 * @Description:
 * @author:ZhongGaoYong
 * @see:
 * @since:
 * @copyright www.wozhongla.com
 * @Date:2015-10-27
 */
public class BitmapHelper {

	public static final int UNCONSTRAINED = -1;

	/**
	 * 压缩图片并且保存 li.图片宽度>=width以定值width输出、高度等比例缩放 li.图片宽度<width 原图输出 li.图片宽度<width 以图片宽度输出
	 * 
	 * @Description:
	 * @param widthZoom
	 * @return
	 * @see:
	 * @since:
	 * @author:www.wozhongla.com
	 * @date:2015-10-27
	 */
	public static void compressionImg2Save(File file, int widthZoom, int maxSize, File outFile) {

		if (file != null && outFile != null && file.isFile() &&  file.exists()) {
			// 宽高压缩
			Bitmap bitmap = getZoomSampledBitmap(file, widthZoom);
			//删除原图片
			file.delete();
			// 质量压缩
			ByteArrayOutputStream outputStream = compressBitmap(bitmap, maxSize);
			// 保存图片
			saveFileToFile(outputStream.toByteArray(), outFile);
			bitmap = BitmapFactory.decodeByteArray(outputStream.toByteArray(), 0, outputStream.toByteArray().length);
			// 释放资源
			closeStream(outputStream);

			if(bitmap!=null){
				bitmap.recycle();
				bitmap=null;
			}
		}
	}

	/**
	 * TODO//大图可能会出现问题。以后修改
	 * 
	 * @Description:
	 * @param file
	 * @param width
	 * @param height
	 * @param maxSize
	 * @return
	 * @see:
	 * @since:
	 * @author:www.wozhongla.com
	 * @date:2015-10-27
	 */
	public static Bitmap getBitMap(File file, int width, int height, int maxSize) {
		BitmapFactory.Options newOptions = new BitmapFactory.Options();
		// 开始读取图片、只获取宽高
		newOptions.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(file.getPath(), newOptions);
		// 原图宽
		int oldBitMapWidth = newOptions.outWidth;
		if (oldBitMapWidth > width) {
			bitmap = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(file.getPath()), width, height);
		} else {
			bitmap = BitmapFactory.decodeFile(file.getPath());
		}
		// 质量压缩
		return bitmap;
	}

	/**
	 * 按照等宽进行图片缩放 li.图片宽度>=width以定值width输出、高度等比例缩放 li.图片宽度<width 原图输出 li.图片宽度<width 以图片宽度输出
	 * 
	 * @Description:
	 * @param file
	 * @param widthZoom
	 * @return
	 * @see:
	 * @since:
	 * @author:www.wozhongla.com
	 * @date:2015-10-27
	 */
	private static Bitmap getZoomSampledBitmap(File file, int widthZoom) {
		BitmapFactory.Options newOptions = new BitmapFactory.Options();
		// 开始读取图片、只获取宽高
		newOptions.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(file.getPath(), newOptions);
		newOptions.inJustDecodeBounds = false;
		// 原图宽
		double oldBitMapWidth = newOptions.outWidth;
		// 原图高度
		double oldBitMapHeight = newOptions.outHeight;
		// 新图宽度
		double newBitMapWidth = oldBitMapWidth >= widthZoom ? widthZoom : oldBitMapWidth;
		// 新图高度
		double newBitMapHeight = (newBitMapWidth/oldBitMapWidth)*oldBitMapHeight;
		newOptions.inSampleSize = calculateInSampleSize(newOptions, (int)newBitMapWidth, (int)newBitMapHeight);
		newOptions.inPurgeable = true;
		newOptions.inInputShareable = true;
		bitmap = BitmapFactory.decodeFile(file.getPath(), newOptions);
		// 比例压缩(解决一个问题：Options中图片不可以定值压缩，只能按比例缩放，而且比例是int，精确度比较低，为了解决这一个问题，那么对比例缩放后的bitmap再次进行压缩(因为是直接操作bitmap可能对内存会有影响))
		if (bitmap.getWidth() > widthZoom) {
			bitmap = ThumbnailUtils.extractThumbnail(bitmap, (int)newBitMapWidth, (int)newBitMapHeight);
		}
		return bitmap;
	}

	/**
	 * 图片保存到本地
	 * 
	 * @Description:
	 * @param bitmap
	 * @param outFile
	 * @see:
	 * @since:
	 * @author:www.wozhongla.com
	 * @date:2015-10-27
	 */
	public static void saveBitmapToFile(Bitmap bitmap, File outFile) {
		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(outFile);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeStream(outputStream);
		}
	}

	/**
	 * byte[]保存到本地
	 * 
	 * @Description:
	 * @param bitmap
	 * @param outFile
	 * @see:
	 * @since:
	 * @author:www.wozhongla.com
	 * @date:2015-10-27
	 */
	private static void saveFileToFile(byte[] bytes, File outFile) {
		OutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(outFile);
			fileOutputStream.write(bytes);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeStream(fileOutputStream);
		}
	}

	/**
	 * 图片质量压缩
	 * 
	 * @Description:
	 * @param bmp
	 * @param
	 * @param maxSize
	 *            /KB
	 * @see:
	 * @since:
	 * @author:www.wozhongla.com
	 * @date:2015-10-27
	 */
	public static ByteArrayOutputStream compressBitmap(Bitmap bmp, int maxSize) {
		int startQuality = 95;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.JPEG, startQuality, outputStream);
		// 如果压缩后图片的size>maxSize、则继续进行压缩
		int bitMapSize = 0;
		while ((bitMapSize = outputStream.toByteArray().length / 1024) > maxSize) {
			outputStream.reset();
			startQuality -= 5;
			bmp.compress(Bitmap.CompressFormat.JPEG, startQuality, outputStream);
		}
		return outputStream;
	}

	/**
	 * 获取缩放值
	 * 
	 * @Description:
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 * @see:
	 * @since:
	 * @author:www.wozhongla.com
	 * @date:2015-10-27
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

	/**
	 * 关闭数据流
	 * 
	 * @Description:
	 * @param outputStream
	 * @see:
	 * @since:
	 * @author:www.wozhongla.com
	 * @date:2015-10-27
	 */
	private static void closeStream(OutputStream outputStream) {
		if (outputStream != null) {
			try {
				outputStream.flush();
				outputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
