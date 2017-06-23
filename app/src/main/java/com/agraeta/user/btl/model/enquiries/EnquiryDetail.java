package com.agraeta.user.btl.model.enquiries;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Nivida new on 27-Mar-17.
 */

public class EnquiryDetail {

    @SerializedName("id")
    String id="";

    @SerializedName("first_name")
    String firstName="";

    @SerializedName("last_name")
    String lastName="";

    @SerializedName("email")
    String email="";

    @SerializedName("contact_no")
    String contactNo="";

    @SerializedName("city_name")
    String cityName="";

    @SerializedName("state_name")
    String stateName="";

    @SerializedName("created")
    String onDate="";

    public String getId() {
        return id;
    }

    public String getName() {
        return firstName+" "+lastName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public String getOnDate(String givenFormat, String dateFormat){
        SimpleDateFormat currentFormat=new SimpleDateFormat(givenFormat, Locale.getDefault());
        SimpleDateFormat requiredFormat=new SimpleDateFormat(dateFormat,Locale.getDefault());

        try {
            Date currentDate=currentFormat.parse(onDate.split(" ")[0]);
            return requiredFormat.format(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return onDate;
        }
    }

    public String getEmail() {
        return email;
    }

    public String getCityName() {
        return cityName;
    }

    public String getStateName() {
        return stateName;
    }
}
