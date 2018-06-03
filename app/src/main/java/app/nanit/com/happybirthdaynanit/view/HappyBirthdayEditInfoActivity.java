package app.nanit.com.happybirthdaynanit.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.util.List;

import app.nanit.com.happybirthdaynanit.R;
import app.nanit.com.happybirthdaynanit.databinding.ActivityHappyBirthdayEditInfoBinding;
import app.nanit.com.happybirthdaynanit.model.User;
import app.nanit.com.happybirthdaynanit.viewmodel.UserViewModel;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class HappyBirthdayEditInfoActivity extends AppCompatActivity implements Observer<User> {

    private ActivityHappyBirthdayEditInfoBinding mDataBinder;
    private UserViewModel mUserViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
    }

    private void initBinding() {

        mDataBinder = DataBindingUtil.setContentView(
                this, R.layout.activity_happy_birthday_edit_info);
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mDataBinder.setUserViewModel(mUserViewModel);
        mUserViewModel.getUserLiveData().observe(this,this);
        mUserViewModel.fetchData(this);
        setImageRetrievable();
    }

    private void setImageRetrievable() {
        mDataBinder.editInfoImageParent.setOnClickListener(v -> {
        EasyImage.openChooserWithGallery(this, "Pick source",0);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mUserViewModel.onActivityResult(this,requestCode, resultCode, data);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override @VisibleForTesting
    public void onChanged(@Nullable User user) {
        Log.d("shimi"," in onUserDataChanged user = "+user.getName()+"  "+user.getBirthDate()+"  "+user.getImageUri());
        mDataBinder.editInfoUserName.setText(user.getName());
        mDataBinder.editInfoBirthday.setText(user.getFormattedBirthDate());
        mDataBinder.editInfoImage.setImageURI(Uri.parse(user.getImageUri()));
    }

}
