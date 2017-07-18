package com.profuturo.example.motorfirma;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by praxis on 14/07/17.
 */

public class Util {
    public static boolean saveView(View view, String filename) {
        boolean isSaved = false;
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = view.getDrawingCache();
        File file = new File(filename);
        FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            isSaved = true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Utils saveView", "Error " + e.getMessage());
            return false;
        }
        return isSaved;
    }

    public static Bitmap getSavedView(String filename) {
        Bitmap bitmap;
        try {
            File file = new File(filename);
            bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            bitmap = null;
        }
        return bitmap;
    }
}
