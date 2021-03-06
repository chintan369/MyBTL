package com.agraeta.user.btl.model;

import com.agraeta.user.btl.model.area.AreaItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 24-Jun-17.
 */

public class RegisteredUserTourResponse extends AppModel {

    List<RegisteredUserTour> data = new ArrayList<>();

    public List<RegisteredUserTour> getData() {
        return data;
    }

    public static class Distributor implements Serializable {
        String user_id = "";
        String firm_name = "";
        String tally_pan_no = "";
        String gstin_uin = "";

        public String getUser_id() {
            return user_id;
        }

        public String getFirm_name() {
            return firm_name;
        }

        public String getTally_pan_no() {
            return tally_pan_no;
        }

        public String getGstin_uin() {
            return gstin_uin;
        }
    }

    public static class Address implements Serializable {
        String id = "";
        String user_id = "";
        String first_name = "";
        String last_name = "";
        String email = "";
        String mobile = "";
        String address_1 = "";
        String address_2 = "";
        String address_3 = "";
        String landmark = "";
        String pincode = "";
        String country_id = "";
        String state_id = "";
        String city_id = "";
        String other_city = "";
        String area_id = "";
        String default_address = "";
        AreaItem Country = new AreaItem();
        AreaItem State = new AreaItem();
        AreaItem City = new AreaItem();
        AreaItem Area = new AreaItem();

        public String getId() {
            return id;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getFirst_name() {
            return first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public String getEmail() {
            return email;
        }

        public String getMobile() {
            return mobile;
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

        public String getLandmark() {
            return landmark;
        }

        public String getPincode() {
            return pincode;
        }

        public String getState_id() {
            return state_id;
        }

        public String getCity_id() {
            return city_id;
        }

        public String getOther_city() {
            return other_city;
        }

        public String getArea_id() {
            return area_id;
        }

        public String getDefault_address() {
            return default_address;
        }

        public AreaItem getCountry() {
            return Country;
        }

        public void setCountry(AreaItem country) {
            Country = country;
        }

        public AreaItem getState() {
            return State;
        }

        public void setState(AreaItem state) {
            State = state;
        }

        public AreaItem getCity() {
            return City;
        }

        public void setCity(AreaItem city) {
            City = city;
        }

        public AreaItem getArea() {
            return Area;
        }

        public void setArea(AreaItem area) {
            Area = area;
        }

        public String getCountry_id() {
            return country_id;
        }
    }

    public class RegisteredUserTour {
        User User = new User();
        Distributor Distributor = new Distributor();
        AreaItem Role = new AreaItem();
        List<Address> Address = new ArrayList<>();
        List<RegisteredUserData.OrderReason> SkipOrderPerson = new ArrayList<>();

        public com.agraeta.user.btl.model.User getUser() {
            return User;
        }

        public AreaItem getRole() {
            return Role;
        }

        public RegisteredUserTourResponse.Distributor getDistributor() {
            return Distributor;
        }

        public List<RegisteredUserTourResponse.Address> getAddress() {
            return Address;
        }

        public List<RegisteredUserData.OrderReason> getSkipOrderPerson() {
            return SkipOrderPerson;
        }
    }
}
