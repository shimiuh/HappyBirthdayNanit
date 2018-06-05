package app.nanit.com.happybirthdaynanit.viewmodel;

import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import java.util.List;
import java.util.Map;
import java.util.Random;

import app.nanit.com.happybirthdaynanit.R;
import app.nanit.com.happybirthdaynanit.model.User;
import app.nanit.com.happybirthdaynanit.utils.Utils;

public class ShareViewModel extends UserViewModel {

    //TODO: move to constants
    public int[]   mResColorBgList  = new int[]{Color.parseColor("#F9F0D7"),Color.parseColor("#C5E8DF"),Color.parseColor("#DAF1F6")};
    public int[]   mResBgList       = new int[]{R.drawable.android_elephant_popup,R.drawable.android_fox_popup,R.drawable.android_pelican_popup};
    public int[]   mResCameraBgList = new int[]{R.drawable.camera_icon_yellow,R.drawable.camera_icon_green,R.drawable.camera_icon_blue};
    public int[]   mResBgDefaultList = new int[]{R.drawable.default_place_holder_yellow,R.drawable.default_place_holder_green,R.drawable.default_place_holder_blue};
    public int[]   mResAgeList = new int[]{R.drawable.n0,R.drawable.n1,R.drawable.n2, R.drawable.n3,R.drawable.n4 ,R.drawable.n5,R.drawable.n6
            ,R.drawable.n7,R.drawable.n8, R.drawable.n9,R.drawable.n10 ,R.drawable.n11,R.drawable.n12};

    public Uri     mImageUrl ;
    public String  mName, mYearOrMonth;
    public int     mResBg, mResDefaultBg, mResColorBg, mResCameraBg, mResAge;


    public ShareViewModel(){
        setRandomData();
    }

    public View.OnClickListener mShareClicked = v -> {
//        //TODO: share screen
    };

    public void onChanged(@Nullable User user){

        mName = user.getName();
        Uri uri = user.getImageUri();
        if(uri != null) {
            mImageUrl = uri;
        }
        setDataForAge();
    }

    private void setRandomData() {
        final Random r = new Random();
        int randomInt = r.nextInt(3);
        mResBg            = mResBgList[randomInt] ;
        mResDefaultBg     = mResBgDefaultList[randomInt];
        mResColorBg       = mResColorBgList[randomInt] ;
        mResCameraBg      = mResCameraBgList[randomInt] ;
        mImageUrl         = Utils.getUriResource(mResBgDefaultList[randomInt]);
    }

    public void setDataForAge(){
        Pair<String,Integer> data = Utils.getAgeData(getUserLiveData().getUser().getBirthDate());
        mYearOrMonth = data.first;
        if(data.second < mResAgeList.length) {
            mResAge = mResAgeList[data.second];
        }
    }
}
