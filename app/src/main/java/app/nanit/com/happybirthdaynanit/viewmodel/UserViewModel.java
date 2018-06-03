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
import android.widget.DatePicker;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import androidx.navigation.ActivityNavigator;
import app.nanit.com.happybirthdaynanit.model.User;
import app.nanit.com.happybirthdaynanit.model.UserLiveData;
import app.nanit.com.happybirthdaynanit.utils.Utils;
import app.nanit.com.happybirthdaynanit.view.HappyBirthdayEditInfoActivity;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

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
            showDatePicker(v.getContext());
        }
    };

    public View.OnClickListener mOnSetBirthdayListener = v -> {
        showDatePicker(v.getContext());
        Log.d("shimi","in mOnSetBirthdayListener");
    };

    public View.OnClickListener mImageClickListener = v -> {
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

    private void showDatePicker(Context context) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(context, android.R.style.Theme_DeviceDefault_Dialog,
                (view, selectedYear, selectedMonth, selectedDayOfMonth) -> {
            GregorianCalendar gc = new GregorianCalendar(selectedYear,selectedMonth,selectedDayOfMonth);
            getUserLiveData().getUser().setBirthDate(gc.getTimeInMillis());
            getUserLiveData().save(context);
        }, year, month, day);

        dialog.getDatePicker().setMaxDate( new Date().getTime() );
        dialog.show();
    }

    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {

        EasyImage.handleActivityResult(requestCode, resultCode, data, activity, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                // Cancel handling, you might wanna remove taken photo if it was canceled
                if (source == EasyImage.ImageSource.CAMERA) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(activity);
                    if (photoFile != null) photoFile.delete();
                }
            }

            @Override
            public void onImagesPicked(List<File> imagesFiles, EasyImage.ImageSource source, int type) {
                //Handle the images
                onPhotosReturned(activity, imagesFiles);
            }
        });
    }

    private void onPhotosReturned(Context context, List<File> imagesFiles) {

        getUserLiveData().getUser().setImageUri(Uri.fromFile(imagesFiles.get(0)).toString());
        getUserLiveData().save(context);

    }
}
