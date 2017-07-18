package com.profuturo.example.motorfirma;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by praxis on 13/07/17.
 */

public class SignatureView extends View {
    private boolean clear = false;
    private Paint paint = new Paint();
    private Path path = new Path();
    private boolean marked = false;

    public SignatureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(3);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    /**
     * This method schedules the canvas for clearing
     */
    public void clear() {
        clear = true;
        this.setBackground(null);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (clear) {
            Paint paint = new Paint();
            paint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
            canvas.drawPaint(paint);
            paint.setXfermode(new PorterDuffXfermode(Mode.SRC));
            path.reset();
            canvas.drawColor(Color.WHITE);
            clear = false;
        }
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(eventX, eventY);
                return true;
            case MotionEvent.ACTION_MOVE:
                setMarked(true);
                path.lineTo(eventX, eventY);
                break;
            case MotionEvent.ACTION_UP:
                // nothing to do
                break;
            default:
                return false;
        }

        // Schedules a repaint.
        invalidate();
        return true;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }
}
