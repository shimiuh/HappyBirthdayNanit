package app.nanit.com.happybirthdaynanit.model;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Process;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class UserLiveData extends MutableLiveData<User> {

    private static final String USER_KEY = "USER_KEY";
    private final User mUser;


    public UserLiveData(){
        mUser = new User();
    }

    public User getUser() {
        return mUser;
    }


    private void setUser(User user) {
        getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                setValue(user);
            }
        });

    }

    public void save(final Context context){

        getBackgroundHandler().postDelayed(() -> {
            SharedPreferences sharedPref = context.getSharedPreferences(context.getPackageName(),Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(USER_KEY, toGsonString(mUser));
            editor.commit();
            setUser(fromGsonString(sharedPref.getString(USER_KEY, "")));
        },100);

    }

    public void loadSaved(final Context context){

        getBackgroundHandler().postDelayed(() -> {
            SharedPreferences sharedPref = context.getSharedPreferences(context.getPackageName(),Context.MODE_PRIVATE);
            User user = fromGsonString(sharedPref.getString(USER_KEY, ""));
            if(user != null) {
                setUser(user);
            }
        },100);

    }

    private static User fromGsonString(String userGsonString) {
        Type type = new TypeToken<User>() {}.getType();
        return new Gson().fromJson(userGsonString, type);
    }
    private String toGsonString(User user) {
        return new Gson().toJson(user);
    }

    private static Handler mBackgroundHandler = null;
    synchronized public static Handler getBackgroundHandler(){
        if(mBackgroundHandler == null){
            HandlerThread thread = new HandlerThread("user-background-thread", Process.THREAD_PRIORITY_BACKGROUND);
            thread.start();
            mBackgroundHandler = new Handler(thread.getLooper());
        }
        return mBackgroundHandler;
    }

    private static Handler mUiHandler = null;
    synchronized public static Handler getMainHandler(){
        if (mUiHandler==null){
            mUiHandler = new Handler(Looper.getMainLooper());
        }
        return mUiHandler;
    }

}
