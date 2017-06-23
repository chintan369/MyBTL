package com.agraeta.user.btl.model;

import com.agraeta.user.btl.model.area.AreaItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 02-Jun-17.
 */

public class RegisteredUserData extends AppModel {

    List<RegisteredUser> data=new ArrayList<>();

    public List<RegisteredUser> getData() {
        return data;
    }

    public class RegisteredUser implements Serializable{
        OrderReason OrderReason=new OrderReason();
        User User=new User();
        User OrderUser=new User();
        AreaItem Country;
        AreaItem State;
        AreaItem City;
        AreaItem Area;
        AreaItem Role;

        public RegisteredUserData.OrderReason getOrderReason() {
            return OrderReason;
        }

        public com.agraeta.user.btl.model.User getUser() {
            return User;
        }

        public com.agraeta.user.btl.model.User getOrderUser() {
            return OrderUser;
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

    public class OrderReason implements Serializable{
        String reason_title="";
        String other_reason_title="";
        String latitude="";
        String longitude="";
        String comment="";
        String address_1="";
        String address_2="";
        String address_3="";
        String pincode="";
        String contact_person="";
        String email="";
        String mobile_no="";
        String party_report="";
        String dealing_in_brand="";
        String joint_visit_with="";
        String visiting_card1="";
        String visiting_card2="";
        String attachment1="";
        String attachment2="";
        String attachment3="";
        String attachment4="";
        String firm_name="";
        String is_past_order="";
        String order_id="";
        String created="";

        public String getReason_title() {
            return reason_title;
        }

        public String getOther_reason_title() {
            return other_reason_title;
        }

        public String getLatitude() {
            return latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public String getComment() {
            return comment==null ? "" : comment;
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

        public String getPincode() {
            return pincode;
        }

        public String getContact_person() {
            return contact_person;
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

        public String getFirm_name() {
            return firm_name==null ? "" : firm_name;
        }

        public String getIs_past_order() {
            return is_past_order;
        }

        public String getOrder_id() {
            return order_id;
        }

        public String getCreated() {
            return created;
        }
    }
}
