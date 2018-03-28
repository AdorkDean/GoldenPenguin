package com.guo.goldenpenguin.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class BigImageView extends ImageView {
    private BitmapRegionDecoder mDecoder;
    private Rect bsrc;
    private Rect src;
    private Rect dst;
    private Paint paint;
    private ArrayList<Bitmap> bmps;

    public BigImageView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init();
    }

    public BigImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        bmps = new ArrayList<Bitmap>();
        src = new Rect();
        dst = new Rect();
        bsrc = new Rect();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (bmps.size() > 0) {
            // TODO Auto-generated method stub
            int count = bmps.size();
            for (int i = 0; i < count; i++) {
                Bitmap bmp = bmps.get(i);
                src.left = 0;
                src.top = 0;
                src.right = bmp.getWidth();
                src.bottom = bmp.getHeight();

                dst.left = 0;
                dst.top = i * getHeight() / count;
                dst.right = getWidth();
                dst.bottom = dst.top + getHeight() / count;

                canvas.drawBitmap(bmp, src, dst, paint);

            }
        } else {
            super.onDraw(canvas);
        }


    }


    public void showBigImg(final Bitmap bm) {

        if (bm == null) {
            return;
        }


        final int newHeight = bm.getHeight();
        final int newWidth = bm.getWidth();

        new Thread() {
            public void run() {
                try {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    InputStream isBm = new ByteArrayInputStream(baos.toByteArray());
                    mDecoder = BitmapRegionDecoder.newInstance(isBm, false);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ALPHA_8;
                    for (int i = 0; i < 3; i++) {
                        bsrc.left = 0;
                        bsrc.top = i * newHeight / 3;
                        bsrc.right = newWidth;
                        bsrc.bottom = bsrc.top + newHeight / 3;
                        Bitmap bmp = mDecoder.decodeRegion(bsrc, options);
                        bmps.add(bmp);
                    }
                    postInvalidate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

}

