package jp.co.webtech.android.pngopttestapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import java.io.IOException;
import java.io.InputStream;

public class Util {

    public static void showAbout(Context context) {
        AlertDialog.Builder dlg = new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.app_name))
                .setMessage(context.getString(R.string.version) + " " + context.getString(R.string.app_version) + "\n" +
                        "\n" +
                        context.getString(R.string.copyright)
                );
        dlg.setPositiveButton("OK", null);
        dlg.show();
    }

    public static Bitmap loadBitmapFromAssets(AssetManager as, String file) {
        InputStream is = null;
        try {
            is = as.open(file);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Bitmap createThumbnail(Bitmap bitmap, int size) {
        if (bitmap == null) {
            return null;
        }

        Bitmap thumb = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(thumb);

        Paint paint = new Paint();
        paint.setColor(Color.argb(255, 255, 255, 255));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0.0f, 0.0f, size, size, paint);

        float x, y, w, h;
        float ws = bitmap.getWidth();
        float hs = bitmap.getHeight();
        float wd = size;
        float hd = size;
        if (ws <= wd && hs <= hd) {
            w = ws;
            h = hs;
        } else {
            w = ws * 100 / wd;
            h = hs * 100 / hd;
            if (w > h) {
                if (wd < ws) {
                    w = wd;
                    h = wd * hs / ws;
                }
            } else {
                if (hd < hs) {
                    h = hd;
                    w = hd * ws / hs;
                }
            }
        }
        x = 0 + (size - w) / 2;
        y = 0 + (size - h) / 2;

        Rect rectSrc = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectDst = new RectF(x, y, x + w, y + h);

        paint = new Paint();
        paint.setFilterBitmap(true);
        canvas.drawBitmap(bitmap, rectSrc, rectDst, paint);

        return thumb;
    }
}
