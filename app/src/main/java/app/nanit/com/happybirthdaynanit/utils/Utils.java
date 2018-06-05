package app.nanit.com.happybirthdaynanit.utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

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

    public static Uri getUriResource(int intResourceId) {
         //TODO: get resource uri from context remove hard code
         return Uri.parse("android.resource://app.nanit.com.happybirthdaynanit/" +intResourceId);
    }

    public static Pair<String,Integer> getAgeData(long age) {


        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        int thisYear = calendar.get(Calendar.YEAR);
        int thisMonth = calendar.get(Calendar.MONTH) + 1 ;

        calendar.setTimeInMillis(age);
        int year  = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        String ageString = "";
        int ageInt = 0;
        int yearDif = thisYear - year;

        if ( (yearDif) > 0 ){
            ageInt = yearDif ;
            ageString = (yearDif) > 1 ? "Years old" : "Year old";//TODO: implement plural in xml for translation
        }else{
            int monthDif = thisMonth - month;
            if ( (monthDif) > 0 ) {
                ageInt = monthDif;
                ageString = (monthDif) > 1 ? "Months old" : "Month old";
            }
        }

        return Pair.create(ageString,ageInt);
    }

}
