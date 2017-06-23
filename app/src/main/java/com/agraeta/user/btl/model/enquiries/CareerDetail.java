package com.agraeta.user.btl.model.enquiries;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nivida new on 27-Mar-17.
 */

public class CareerDetail extends EnquiryDetail {

    @SerializedName("vacancy_name")
    String vacancyName="";

    @SerializedName("document")
    String document="";

    public String getVacancyName() {
        return vacancyName;
    }

    public String getDocument() {
        return document;
    }
}
