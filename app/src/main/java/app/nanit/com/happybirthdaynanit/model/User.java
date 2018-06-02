package app.nanit.com.happybirthdaynanit.model;

public class User {

    private String mName;
    private Long mBirthDate;
    private String mImageUri;

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
}
