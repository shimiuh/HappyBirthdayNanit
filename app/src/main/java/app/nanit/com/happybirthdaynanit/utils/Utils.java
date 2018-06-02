package app.nanit.com.happybirthdaynanit.utils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Utils {

    private static String TAG = Utils.class.getSimpleName();

    public static void showKeyboard(View view, boolean show) {

        try {

            if (view == null){
                return;
            }

            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm == null){
                return;
            }
            if (show){
                imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
            } else{
                imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
                imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            Log.e(TAG, "won't hide keyboard error == "+e.getMessage());
        }
    }
}
