/**
 * 2015-2-26
 * 2015-2-26
 */
package com.li.iphotoeditor.utils;

import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;

/**
 * @author Quinn
 * @date 2015-2-26
 */
public class Utils {

	public static String getBitmapSize(Bitmap bitmap) {
		String size = null;
		int count = bitmap.getByteCount();
		if (count < 1024) {
			return count + "B";
		}
		if (count >= 1024 && count < 1024 * 1024) {
			float count_kb = (float) count / 1024;
			return count_kb + " KB";
		}
		if (count >= 1024 * 1024) {
			float count_mb = (float) count / (1024 * 1024);
			return count_mb + " MB";
		}

		return size;
	}

	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}
	
	public static Bitmap rotateBitmap(Bitmap bitmap, float rotateDegree){
		Matrix matrix = new Matrix();
		matrix.postRotate((float)rotateDegree);
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
		return bitmap;
	}

}
