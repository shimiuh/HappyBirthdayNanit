package app.nanit.com.happybirthdaynanit.model;

import android.net.Uri;
import android.text.format.DateFormat;
import android.util.Log;

import java.util.Calendar;
import java.util.Locale;

public class User {

    private String mName;
    private long   mBirthDate = 0;
    private String mImageUri = null;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public Long getBirthDate() {
        return mBirthDate;
    }

    public void setBirthDate(Long birthDate) {
        this.mBirthDate = birthDate;
    }

    public String getImageUriString() {
        return mImageUri;
    }

    public Uri getImageUri() {
        Uri uri = null;
        if(getImageUriString() != null){
            uri = Uri.parse(getImageUriString());
        }
        return uri;
    }
    public void setImageUriString(String imageUri) {
        this.mImageUri = imageUri;
    }

    public String getFormattedBirthDate() {
        if(getBirthDate() == null || getBirthDate() == 0){
            return "";
        }
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(getBirthDate());
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }
}
