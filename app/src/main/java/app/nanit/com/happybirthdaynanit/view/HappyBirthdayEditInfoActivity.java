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

import app.nanit.com.happybirthdaynanit.R;
import app.nanit.com.happybirthdaynanit.databinding.ActivityHappyBirthdayEditInfoBinding;
import app.nanit.com.happybirthdaynanit.model.User;
import app.nanit.com.happybirthdaynanit.viewmodel.EditInfoViewModel;
import pl.aprilapps.easyphotopicker.EasyImage;

public class HappyBirthdayEditInfoActivity extends AppCompatActivity implements Observer<User> {

    private ActivityHappyBirthdayEditInfoBinding mDataBinder;
    private EditInfoViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
    }

    private void initBinding() {

        mDataBinder = DataBindingUtil.setContentView(
                this, R.layout.activity_happy_birthday_edit_info);
        mViewModel = ViewModelProviders.of(this).get(EditInfoViewModel.class);
        mDataBinder.setEditInfoViewModel(mViewModel);
        mViewModel.getUserLiveData().observe(this,this);
        setImageRetrievable();
    }

    @Override
    protected void onResume() {
        mViewModel.fetchData(this);
        super.onResume();
    }

    private void setImageRetrievable() {
        mDataBinder.editInfoImageParent.setOnClickListener(v -> {
        EasyImage.openChooserWithGallery(this, "Pick source",0);
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
        if(user.getName() != null) {
            mDataBinder.editInfoUserName.setText(user.getName());
        }
        if(user.getImageUri() != null) {
            mDataBinder.editInfoImage.setImageURI(user.getImageUri());
        }
        mDataBinder.editInfoBirthday.setText(user.getFormattedBirthDate());
        mDataBinder.invalidateAll();
    }

}
