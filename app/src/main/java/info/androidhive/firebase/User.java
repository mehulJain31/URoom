package info.androidhive.firebase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Divyanshu Sharma on 11/27/2017.
 */

public class User {

    private String name;
    private String date_of_birth;
    private String gender;
    private String user_type;
    private String major;
    private String sleep_Preferences;
    private String cleaning_Frequency;
    private String guests;
    private String bio;
    private String phone_Number;
    private String UserId;



    public User(String name, String date_of_birth, String gender, String user_type, String major, String sleep_Preferences, String cleaning_Frequency, String guests, String bio, String phone_Number) {
        this.name = name;
        this.date_of_birth = date_of_birth;
        this.gender = gender;
        this.user_type = user_type;
        this.major = major;
        this.sleep_Preferences = sleep_Preferences;
        this.cleaning_Frequency = cleaning_Frequency;
        this.guests = guests;
        this.bio = bio;
        this.phone_Number= phone_Number;

    }

    public User( ) {


    }


    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }


    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getMajor() {
        return major;
    }

    public String getPhone_number() {
        return phone_Number;
    }



    public void setMajor(String major) {
        this.major = major;
    }

    public String getSleep_Preferences() {
        return sleep_Preferences;
    }

    public void setSleep_Preferences(String sleep_Preferences) {
        this.sleep_Preferences = sleep_Preferences;
    }

    public String getCleaning_Frequency() {
        return cleaning_Frequency;
    }

    public void setCleaning_Frequency(String cleaning_Frequency) {
        this.cleaning_Frequency = cleaning_Frequency;
    }

    public String getGuests() {
        return guests;
    }

    public void setPhone_number(String phone_Number) {

        this.phone_Number= phone_Number;
    }


    public void setGuests(String guests) {
        this.guests = guests;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
