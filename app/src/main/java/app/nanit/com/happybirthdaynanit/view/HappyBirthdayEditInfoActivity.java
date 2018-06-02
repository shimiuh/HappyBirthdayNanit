package app.nanit.com.happybirthdaynanit.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import app.nanit.com.happybirthdaynanit.R;
import app.nanit.com.happybirthdaynanit.databinding.ActivityHappyBirthdayEditInfoBinding;
import app.nanit.com.happybirthdaynanit.model.User;
import app.nanit.com.happybirthdaynanit.model.UserLiveData;
import app.nanit.com.happybirthdaynanit.viewmodel.UserViewModel;

public class HappyBirthdayEditInfoActivity extends AppCompatActivity implements Observer<User> {

    private ActivityHappyBirthdayEditInfoBinding mDataBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
    }

    private void initBinding() {

        mDataBinder = DataBindingUtil.setContentView(
                this, R.layout.activity_happy_birthday_edit_info);
        UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mDataBinder.setUserViewModel(userViewModel);
        userViewModel.getUserLiveData().observe(this,this);
        userViewModel.fetchData(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override @VisibleForTesting
    public void onChanged(@Nullable User user) {
        Log.d("shimi"," in onUserDataChanged user = "+user.getName()+"  "+user.getBirthDate()+"  "+user.getImageUri());
        mDataBinder.editInfoUserName.setText(user.getName());
    }
}
