package app.nanit.com.happybirthdaynanit.viewmodel;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.net.Uri;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import app.nanit.com.happybirthdaynanit.model.User;
import app.nanit.com.happybirthdaynanit.model.UserLiveData;
import app.nanit.com.happybirthdaynanit.utils.Utils;
import app.nanit.com.happybirthdaynanit.view.HappyBirthdayShareActivity;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class EditInfoViewModel extends UserViewModel {

    public boolean mCanShowBirthdayScreen = false;
    public TextWatcher mWatcher = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable s) {
            if (!TextUtils.equals(getUserLiveData().getUser().getName(), s.toString())) {
                getUserLiveData().getUser().setName(s.toString());
            }
        }

        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}

    };

    public View.OnFocusChangeListener mRemoveKeyboard = (v, hasFocus) -> {
        if(hasFocus){
            getUserLiveData().save(v.getContext());
            Utils.showKeyboard(v, false);
            showDatePicker(v.getContext());
        }
    };

    public View.OnClickListener mOnSetBirthdayListener = v -> {
        showDatePicker(v.getContext());
    };

    public View.OnClickListener mShowBirthdayScreen = v -> {
        getUserLiveData().save(v.getContext());
        v.getContext().startActivity(new Intent(v.getContext(), HappyBirthdayShareActivity.class));
    };

    public void onChanged(User user) {
        if(user.getName() != null && user.getBirthDate() > 0){
            mCanShowBirthdayScreen = true;
        }
    }
}
