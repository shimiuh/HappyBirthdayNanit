package app.nanit.com.happybirthdaynanit.model;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class User {

    private static final String USER_KEY = "USER_KEY";
    private String mName;
    private Long mBirthDate;
    private String mImageUri;
    private MutableLiveData<User> mUserLiveData;

    public User(){
        mUserLiveData = new MutableLiveData<>();
    }

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

    public String getImageUri() {
        return mImageUri;
    }

    public void setImageUri(String imageUri) {
        this.mImageUri = imageUri;
    }

    public MutableLiveData<User> getUserLiveData() {
        return mUserLiveData;
    }



    private void setUser(User user) {
        Log.d("shimi"," in setUser user = "+user.getName()+"  "+user.getBirthDate()+"  "+user.getImageUri());
        this.mUserLiveData.setValue(user);
    }

    public void save(final Context context){

        SharedPreferences sharedPref = context.getSharedPreferences(context.getPackageName(),Context.MODE_PRIVATE);
        sharedPref.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                Log.d("shimi","in onSharedPreferenceChanged key = "+key);
                if(key.equals(USER_KEY)) {
                    sharedPref.unregisterOnSharedPreferenceChangeListener(this);
                    setUser(fromGsonString(sharedPreferences.getString(key, "")));
                }
            }
        });

        getBackgroundHandler().postDelayed(() -> {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(USER_KEY, toGsonString());
            editor.commit();
            Log.d("shimi","in commit postDelayed");
        },100);

    }

    public void loadSaved(final Context context){

        getBackgroundHandler().postDelayed(() -> {
            Log.d("shimi","in loadSaved postDelayed");
            SharedPreferences sharedPref = context.getSharedPreferences(context.getPackageName(),Context.MODE_PRIVATE);
            User user = fromGsonString(sharedPref.getString(USER_KEY, ""));
            if(user != null) {
                setUser(user);
            }else{
                Log.d("shimi","in loadSaved this is the first time we open the app no user data saved yet");
            }
        },100);

    }

    private static User fromGsonString(String userGsonString) {
        Type type = new TypeToken<User>() {}.getType();
        return new Gson().fromJson(userGsonString, type);
    }
    private String toGsonString() {
        return new Gson().toJson(this);
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

}
