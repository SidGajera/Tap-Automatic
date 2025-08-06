package com.smartclick.auto.tap.autoclicker.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class LineView extends View {
    private float endX;
    private float endY;
    private Paint linePaint;
    private Paint pointPaint;
    private float startX;
    private float startY;

    public LineView(Context context) {
        super(context);
        Paint paint = new Paint();
        this.linePaint = paint;
        paint.setStrokeWidth(60.0f);
        this.linePaint.setColor(Color.parseColor("#B061C665"));
        this.linePaint.setStrokeCap(Paint.Cap.ROUND);
        Paint paint2 = new Paint();
        this.pointPaint = paint2;
        paint2.setColor(Color.parseColor("#B061C665"));
    }

    public void setStartPoint(float f, float f2) {
        this.startX = f;
        this.startY = f2;
        invalidate();
    }

    public void setEndPoint(float f, float f2) {
        this.endX = f;
        this.endY = f2;
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(this.startX, this.startY, this.endX, this.endY, this.linePaint);
        canvas.drawCircle(this.startX, this.startY, 10.0f, this.pointPaint);
        canvas.drawCircle(this.endX, this.endY, 10.0f, this.pointPaint);
    }
}
