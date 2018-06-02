package app.nanit.com.happybirthdaynanit.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.navigation.ActivityNavigator;
import app.nanit.com.happybirthdaynanit.model.User;
import app.nanit.com.happybirthdaynanit.model.UserLiveData;
import app.nanit.com.happybirthdaynanit.utils.Utils;
import app.nanit.com.happybirthdaynanit.view.HappyBirthdayEditInfoActivity;

public class UserViewModel extends ViewModel {

    private ObservableField<UserLiveData> mUserLiveData = new ObservableField<>();

    public TextWatcher mWatcher = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable s) {
            if (!TextUtils.equals(mUserLiveData.get().getUser().getName(), s.toString())) {
                mUserLiveData.get().getUser().setName(s.toString());
            }
        }

        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}

    };

    public View.OnFocusChangeListener mRemoveKeyboard = (v, hasFocus) -> {
        if(hasFocus){
            Utils.showKeyboard(v, false);
        }
    };

    public View.OnClickListener mOnSetBirthdayListener = v -> {
        Log.d("shimi","in mOnSetBirthdayListener");
    };

    public View.OnClickListener mShowBirthdayScreen = v -> {
        getUserLiveData().save(v.getContext());
        Log.d("shimi","in mShowBirthdayScreen");
//        //Navigation.findNavController(v).
//                new ActivityNavigator(v.getContext())
//                        .createDestination()
//                .setIntent(new Intent(v.getContext(), HappyBirthdayEditInfoActivity.class))
//                    .navigate(null, null);
    };


    public UserLiveData getUserLiveData() {
        if(mUserLiveData.get() == null){
            mUserLiveData.set(new UserLiveData());
        }
        return mUserLiveData.get();
    }

    public void fetchData(Context context) {
        getUserLiveData().loadSaved(context);
    }
}
