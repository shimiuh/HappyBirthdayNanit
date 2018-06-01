package app.nanit.com.happybirthdaynanit;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import app.nanit.com.happybirthdaynanit.databinding.ActivityHappyBirthdayEditInfoBinding;
import app.nanit.com.happybirthdaynanit.viewmodel.UserViewModel;

public class HappyBirthdayEditInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_happy_birthday_edit_info);
        initBinding();
    }

    private void initBinding() {

        ActivityHappyBirthdayEditInfoBinding  dataBinder = DataBindingUtil.setContentView(
                this, R.layout.activity_happy_birthday_edit_info);
        dataBinder.setUserViewModel(new UserViewModel());

    }

}
