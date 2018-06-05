package app.nanit.com.happybirthdaynanit.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import app.nanit.com.happybirthdaynanit.databinding.ActivityHappyBirthdayShareBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;

import app.nanit.com.happybirthdaynanit.R;
import app.nanit.com.happybirthdaynanit.model.User;
import app.nanit.com.happybirthdaynanit.viewmodel.ShareViewModel;
import pl.aprilapps.easyphotopicker.EasyImage;

public class HappyBirthdayShareActivity extends AppCompatActivity implements Observer<User> {

    private ShareViewModel mViewModel;
    private ActivityHappyBirthdayShareBinding mDataBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
    }

    private void initBinding() {

        mDataBinder = DataBindingUtil.setContentView(
                this, R.layout.activity_happy_birthday_share);
        mViewModel = ViewModelProviders.of(this).get(ShareViewModel.class);
        mDataBinder.setShareInfoViewModel(mViewModel);
        mViewModel.getUserLiveData().observe(this,this);
        mViewModel.fetchData(this);
        setImageRetrievable();
    }

    private void setImageRetrievable() {
        mDataBinder.shareImage.setOnClickListener(v -> {
        EasyImage.openChooserWithGallery(this, "Pick source",0);
        });
        mDataBinder.shareCamera.setOnClickListener(v -> {
            EasyImage.openChooserWithGallery(this, "Pick source",0);
        });
        mDataBinder.shareBack.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mViewModel.onActivityResult(this,requestCode, resultCode, data);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override @VisibleForTesting
    public void onChanged(@Nullable User user) {
        if(user == null){
            return;
        }
        mViewModel.onChanged(user);
        mDataBinder.invalidateAll();
    }
}
