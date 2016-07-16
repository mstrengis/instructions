package lv.androiddev.instructions;

import android.content.res.Resources;
import android.util.TypedValue;

public class DimenUtils {
    public static int dp(int px) {
        try {
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, Resources.getSystem().getDisplayMetrics());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return px;
    }

    public static int getScreenHeight(){
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
