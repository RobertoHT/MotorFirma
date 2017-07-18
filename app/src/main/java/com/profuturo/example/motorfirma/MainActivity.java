package com.profuturo.example.motorfirma;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements OnFirmaClickListener {
    RelativeLayout layoutImagen;
    ImageView imageView;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.img_firma);
        layoutImagen = (RelativeLayout) findViewById(R.id.layout_firma);
        layoutImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               showDialogFragment();
            }
        });
    }

    private void showDialogFragment() {
        DialogFirma dialog = new DialogFirma();
        dialog.setListener(this);
        dialog.setPathFirma(path);
        dialog.show(getSupportFragmentManager(), "dialogFirma");
    }

    @Override
    public void drawFirma(String path) {
        this.path = path;
        if (path != null) {
            Bitmap bitmap = Util.getSavedView(path);
            Log.d("SIZE","Width: " + bitmap.getWidth() + " Height: " + bitmap.getHeight());
            Bitmap resizeBitmap = getResizedBitmap(bitmap, 300, 300);
            //imageView.setAdjustViewBounds(true);
            imageView.setImageBitmap(resizeBitmap);
        } else {
            imageView.setImageBitmap(null);
        }
    }

    private Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);
        // RECREATE THE NEW BITMAP

        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
    }
}
