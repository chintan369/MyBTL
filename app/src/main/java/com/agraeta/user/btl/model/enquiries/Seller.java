package com.agraeta.user.btl.model.enquiries;

import com.agraeta.user.btl.model.User;

import java.io.Serializable;

/**
 * Created by Nivida new on 29-May-17.
 */

public class Seller extends User implements Serializable {
    String firm_name="";
    String contact_person="";
    String alternate_no="";
    String address_1="";
    String address_2="";
    String pincode="";
    String pan_no="";
    String service_tax_no="";
    String vat_tin_no="";
    String upload_pan="";
    String upload_service_tax="";
    String upload_vat_tin="";

    public String getFirm_name() {
        return firm_name;
    }

    public String getContact_person() {
        return contact_person;
    }

    public String getAlternate_no() {
        return alternate_no == null ? "" : alternate_no;
    }

    public String getAddress_1() {
        return address_1;
    }

    public String getAddress_2() {
        return address_2 == null ? "" : address_2;
    }

    public String getPincode() {
        return pincode;
    }

    public String getPan_no() {
        return pan_no;
    }

    public String getService_tax_no() {
        return service_tax_no;
    }

    public String getVat_tin_no() {
        return vat_tin_no;
    }

    public String getUpload_pan() {
        return upload_pan;
    }

    public String getUpload_service_tax() {
        return upload_service_tax;
    }

    public String getUpload_vat_tin() {
        return upload_vat_tin;
    }
}
