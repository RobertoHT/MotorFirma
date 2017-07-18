package com.profuturo.example.motorfirma;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by praxis on 13/07/17.
 */

public class DialogFirma extends DialogFragment {
    private OnFirmaClickListener listener;
    private String pathFirma;

    public void setListener(OnFirmaClickListener listener) {
        this.listener = listener;
    }

    public void setPathFirma(String pathFirma) {
        this.pathFirma = pathFirma;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_firma, null);
        final SignatureView firma = (SignatureView) view.findViewById(R.id.signatureView);
        Button btnClear = (Button) view.findViewById(R.id.btnClear);

        builder.setView(view)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String fileName = null;
                        Log.d("MARKED","result: " + firma.isMarked());
                        if (firma.isMarked()) {
                            File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                            fileName = directory.getAbsolutePath() + "/prueba.png";
                            Log.d("PATH", fileName);
                            SignatureView signatureView = (SignatureView) getDialog().findViewById(R.id.signatureView);
                            Util.saveView(signatureView, fileName);
                        }
                        listener.drawFirma(fileName);
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });

        if(pathFirma != null && !pathFirma.isEmpty()) {
            Bitmap bitmap = Util.getSavedView(pathFirma);
            Drawable drawable = new BitmapDrawable(getResources(), bitmap);
            firma.setMarked(true);
            firma.setBackground(drawable);
        }

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firma.clear();
            }
        });

        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }
}
