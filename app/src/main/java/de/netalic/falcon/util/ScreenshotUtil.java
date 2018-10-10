package de.netalic.falcon.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class ScreenshotUtil {

    public static Bitmap takeScreenshot(View view) {

        view.setDrawingCacheEnabled(true);
        Bitmap viewBitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return viewBitmap;
    }


    public static File saveScreenshot(Bitmap finalBitmap,int quality,String path) {

        String root = Environment.getExternalStorageDirectory().toString();
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
        File myDir = new File(root + path);
        myDir.mkdirs();
        String fileName = "Image-" + now + ".PNG";
        File file = new File(myDir, fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, quality, out);
            out.flush();
            out.close();
            return file;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static File saveScreenshot(Bitmap finalBitmap,int quality,String outerPath,String innerPath) {

        String root = Environment.getExternalStorageDirectory().toString();
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
        File myDir = new File(root + outerPath+innerPath);
        myDir.mkdirs();
        String fileName = "Image-" + now + ".PNG";
        File file = new File(myDir, fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, quality, out);
            out.flush();
            out.close();
            return file;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void shareScreenshot(File file, Context context) {

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName()+".provider", file));
        shareIntent.setType("image/png");
        context.startActivity(Intent.createChooser(shareIntent, "Share Screenshot"));

    }
}
