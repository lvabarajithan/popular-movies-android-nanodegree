package com.popularmovies.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

/**
 * Created by Abarajithan
 */
public class Utils {

    public static int getSpanCount(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float dpWidth = metrics.widthPixels / metrics.density;
        int scalingFactor = 200;
        int columns = (int) dpWidth / scalingFactor;
        if (columns < 2) return 2;
        return columns;
    }

    public static byte[] fromImageView(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

}
