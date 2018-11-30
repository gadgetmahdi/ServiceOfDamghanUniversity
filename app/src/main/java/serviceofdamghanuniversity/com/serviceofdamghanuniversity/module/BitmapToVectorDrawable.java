package serviceofdamghanuniversity.com.serviceofdamghanuniversity.module;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

public class BitmapToVectorDrawable {

  public static Bitmap getVectorDrawable(Context context, int drawableId) {
    Drawable drawable = ContextCompat.getDrawable(context, drawableId);
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
      if (drawable != null) {
        drawable = (DrawableCompat.wrap(drawable)).mutate();
      }
    }

    Bitmap bitmap = null;

    if (drawable != null) {
      bitmap = Bitmap.createBitmap(95,
        126, Bitmap.Config.ARGB_8888);
      Canvas canvas = new Canvas(bitmap);
      drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
      drawable.draw(canvas);

    }

    return bitmap;
  }

}
