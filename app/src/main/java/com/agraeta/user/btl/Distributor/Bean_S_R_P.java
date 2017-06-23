package com.agraeta.user.btl.Distributor;

/**
 * Created by SEO on 9/20/2016.
 */
public class Bean_S_R_P {
    private String user_id = new String();
    int sales_id;
    int retailer_id;
    int professional_id;
    int role_id;

    String firstName;
    String lastName;
    String emailid;
    String phone_no;
    String altPhone_no;
    String password;
    String status="0";
    String pancardno;
    String aadhaarcardno;

    public Bean_S_R_P() {
    }

    public Bean_S_R_P(String user_id, int sales_id, int retailer_id, int professional_id, int role_id, String firstName, String lastName, String emailid, String phone_no, String altPhone_no, String password, String status, String pancardno, String aadhaarcardno) {
        this.user_id = user_id;
        this.sales_id = sales_id;
        this.retailer_id = retailer_id;
        this.professional_id = professional_id;
        this.role_id = role_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailid = emailid;
        this.phone_no = phone_no;
        this.altPhone_no = altPhone_no;
        this.password = password;
        this.status = status;
        this.pancardno = pancardno;
        this.aadhaarcardno = aadhaarcardno;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getSales_id() {
        return sales_id;
    }

    public void setSales_id(int sales_id) {
        this.sales_id = sales_id;
    }

    public int getRetailer_id() {
        return retailer_id;
    }

    public void setRetailer_id(int retailer_id) {
        this.retailer_id = retailer_id;
    }

    public int getProfessional_id() {
        return professional_id;
    }

    public void setProfessional_id(int professional_id) {
        this.professional_id = professional_id;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getAltPhone_no() {
        return altPhone_no;
    }

    public void setAltPhone_no(String altPhone_no) {
        this.altPhone_no = altPhone_no;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPancardno() {
        return pancardno;
    }

    public void setPancardno(String pancardno) {
        this.pancardno = pancardno;
    }

    public String getAadhaarcardno() {
        return aadhaarcardno;
    }

    public void setAadhaarcardno(String aadhaarcardno) {
        this.aadhaarcardno = aadhaarcardno;
    }
}
