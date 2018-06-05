package app.nanit.com.happybirthdaynanit.viewmodel;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import app.nanit.com.happybirthdaynanit.model.UserLiveData;
import app.nanit.com.happybirthdaynanit.utils.Utils;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class UserViewModel extends ViewModel {

    private ObservableField<UserLiveData> mUserLiveData = new ObservableField<>();

    public UserLiveData getUserLiveData() {
        if(mUserLiveData.get() == null){
            mUserLiveData.set(new UserLiveData());
        }
        return mUserLiveData.get();
    }

    public void fetchData(Context context) {
        getUserLiveData().loadSaved(context);
    }

    public void showDatePicker(Context context) {
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
        getUserLiveData().getUser().setImageUriString(Uri.fromFile(imagesFiles.get(0)).toString());
        getUserLiveData().save(context);
    }
}
