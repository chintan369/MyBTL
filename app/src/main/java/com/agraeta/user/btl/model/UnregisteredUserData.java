package com.agraeta.user.btl.model;

import com.agraeta.user.btl.model.area.AreaItem;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 22-May-17.
 */

public class UnregisteredUserData extends AppModel {

    List<UserData> data=new ArrayList<>();

    public List<UserData> getData() {
        return data;
    }

    public class UserData implements Serializable{
        UnregisteredUser UnRegisteredUser;
        AreaItem Country;
        AreaItem State;
        AreaItem City;
        AreaItem Area;
        AreaItem Role;

        public UnregisteredUser getUnRegisteredUser() {
            return UnRegisteredUser;
        }

        public AreaItem getCountry() {
            return Country;
        }

        public AreaItem getState() {
            return State;
        }

        public AreaItem getCity() {
            return City;
        }

        public AreaItem getArea() {
            return Area;
        }

        public AreaItem getRole() {
            return Role;
        }
    }

    public static class UnregisteredUser implements Serializable{
        String id="";
        String address_1="";
        String address_2="";
        String address_3="";
        String country="";
        String state="";
        String city="";
        String area="";
        String pincode="";
        String contact_person="";
        String firm_name="";
        String email="";
        String mobile_no="";
        String party_report="";
        String dealing_in_brand="";
        String customer_type="";
        String comments="";
        String joint_visit_with="";

        String visiting_card1="";
        String visiting_card2="";
        String attachment1="";
        String attachment2="";
        String attachment3="";
        String attachment4="";

        public String getId() {
            return id;
        }

        public String getAddress_1() {
            return address_1;
        }

        public String getAddress_2() {
            return address_2;
        }

        public String getAddress_3() {
            return address_3;
        }

        public String getCountry() {
            return country;
        }

        public String getState() {
            return state;
        }

        public String getCity() {
            return city;
        }

        public String getArea() {
            return area;
        }

        public String getPincode() {
            return pincode;
        }

        public String getContact_person() {
            return contact_person;
        }

        public String getFirm_name() {
            return firm_name;
        }

        public String getEmail() {
            return email;
        }

        public String getMobile_no() {
            return mobile_no;
        }

        public String getParty_report() {
            return party_report;
        }

        public String getDealing_in_brand() {
            return dealing_in_brand;
        }

        public String getCustomer_type() {
            return customer_type;
        }

        public String getComments() {
            return comments;
        }

        public String getJoint_visit_with() {
            return joint_visit_with;
        }

        public String getVisiting_card1() {
            return visiting_card1==null ? "" : visiting_card1;
        }

        public String getVisiting_card2() {
            return visiting_card2==null ? "" : visiting_card2;
        }

        public String getAttachment1() {
            return attachment1==null ? "" : attachment1;
        }

        public String getAttachment2() {
            return attachment2==null ? "" : attachment2;
        }

        public String getAttachment3() {
            return attachment3==null ? "" : attachment3;
        }

        public String getAttachment4() {
            return attachment4==null ? "" : attachment4;
        }
    }
}
