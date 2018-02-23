package com.agraeta.user.btl;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by chaitalee on 3/9/2016.
 */
public class AppPrefs {


    private static final String USER_PREFS = "USER_PREFS";
    String currentPage = "";
    String selectedUserType = "";
    boolean isBulkOrder = false;
    private SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;
    private String user_notification = "user_email_prefs";
    private int noticount = 0;
    private String user_Loginwith = "user_Loginwith";
    private String distributor_Loginwith="dis_Loginwith";
    private String PersonalInfo = "PersonalInfo";
    private String LoginInfo = "LoginInfo";
    private String SocialIdInfo = "SocialIdInfo";
    private String SocialFirst = "SocialFirst";
    private String Sociallast = "Sociallast";
    private String Socialemail = "Socialemail";
    private String SocialReInfo = "SocialReInfo";
    private String CatId = "CatId";
    private String Sort_Radio= "sortradio";
    private String Page_Data="pagedata";
    private String Search="search";
    private String Filter_Sort="filtersort";
    private String Filter_Option="filteroption";
    private String Filter_SubCat="filtersubCat";
    private String Filter_Cat="filtercat";
    private String list_id="list_id";
    private String shopping_pro_id="shopping_pro_id";
    private String add_newGroup="add_newGroup";
    private String Product_Sort="productsort";
    private String Ref_Shopping="Ref_Shopping";
    private String Cart_Grand_total="Cart_Grand_total";
    private String wishid="wishid";
    private String product_id="product_id";
    private String Order_history_filter_predefine="Order_history_filter_predefine";
    private String SalesId="SalesId";
    private String Order_history_filter_order_status="Order_history_filter_order_status";
    private String Order_history_filter_to_date="Order_history_filter_to_date";
    private String Order_history_filter_from_date="Order_history_filter_from_date";
    private String Ref_Detail="Ref_Detail";
    private String Order_history_id="Order_history_id";
    private String CatBack="CatBack";
    private String jobname="jobname";
    private String jobid="jobid";
    private String SalesPersonId="SalesPersonId";
    private String DisSalesUserId="DisSalesUserId";
    private String userRoleId="2";
    private String transportation="";
    private String userId="0";
    private String csalesId="0";
    private String subSalesId="";
    private String subDisId="";
    private String disUserId="";
    private String disUserId22="";
    private String Comment="commen";
    private String Attachment="attachmen";
    private String REF_No="ref_no";
    private String OT="open_type";
    private String PT="pend_type";
    private String Cart_QTy="cart_qty";
    private String Order="order";
    private String Ordercheck="ordercheck";
    private String address_id="address_id";
    private String l_name="l_name";
    private String phone="phone";
    private String email="email";
    private String fi_name="fi_name";
    private String a_id="a_id";
    private String disSubSalesID="0";
    private String statusPR="";
    private String statusPR22="";
    private String DsalesId="";
    private String dSalesUserId="";
    private String SRP_Id="";
    private String quo="equo";
    private String qutation_id="qutation_id";
    private String userName="";




    public AppPrefs(Context context) {
        // TODO Auto-generated constructor stub
        this.appSharedPrefs = context.getSharedPreferences(USER_PREFS, Activity.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
        userRoleId=appSharedPrefs.getString("userRoleId","");
        transportation=appSharedPrefs.getString("transportation","");
        userId=appSharedPrefs.getString("userId","");
        csalesId=appSharedPrefs.getString("csalesId","");
        subSalesId=appSharedPrefs.getString("subSalesId","");
        subDisId=appSharedPrefs.getString("subDisId","");
        //userRoleId=appSharedPrefs.getString("subSalesId","");
        currentPage=appSharedPrefs.getString("currentPage","");
        selectedUserType=appSharedPrefs.getString("selectedUserType","");
        disSubSalesID=appSharedPrefs.getString("disSubSalesID","");
        statusPR=appSharedPrefs.getString("statusPR","");
        disUserId=appSharedPrefs.getString("disUserId","");
        disUserId22=appSharedPrefs.getString("disUserId22","");
        statusPR22=appSharedPrefs.getString("statusPR22","");
        DsalesId=appSharedPrefs.getString("DsalesId","");
        dSalesUserId=appSharedPrefs.getString("dSalesUserId","");
        SRP_Id=appSharedPrefs.getString("SRP_Id","");
        noticount = appSharedPrefs.getInt("noticount", 0);
        isBulkOrder = appSharedPrefs.getBoolean("isBulkOrder", false);

    }

    public int getNoticount() {
        return appSharedPrefs.getInt("noticount", 0);
    }

    public void setNoticount(int noticount) {
        this.noticount = noticount;
        prefsEditor.putInt("noticount", noticount).commit();
    }

    public String getUserName() {
        return appSharedPrefs.getString("userName", "");
    }

    public void setUserName(String userName) {
        prefsEditor.putString("userName", userName).commit();
    }

    public String getUser_notification() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(user_notification, "");
    }

    public void setUser_notification(String notification) {
        // TODO Auto-generated method stub
        prefsEditor.putString(user_notification, notification).commit();

    }

    public String getquo() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(quo, "");
    }

    public void setquo(String quo1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(quo, quo1).commit();

    }

    public String geta_id() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(a_id, "");
    }

    public void seta_id(String a_id1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(a_id, a_id1).commit();

    }

    public String getSalesPersonId() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(SalesPersonId, "");
    }

    public void setSalesPersonId(String SalesPersonId1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(SalesPersonId, SalesPersonId1).commit();

    }

    public String getqutation_id() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(qutation_id, "");
    }

    public void setqutation_id(String qutation_id1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(qutation_id, qutation_id1).commit();

    }

    public String getaddress_id() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(address_id, "");
    }

    public void setaddress_id(String address_id1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(address_id, address_id1).commit();

    }

    public String getOrder() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(Order, "");
    }

    public void setOrder(String Order1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(Order, Order1).commit();

    }

    public String getOrdercheck() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(Ordercheck, "");
    }

    public void setOrdercheck(String Ordercheck1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(Ordercheck, Ordercheck1).commit();

    }

    public String getDisSalesUserId() {
        return appSharedPrefs.getString(DisSalesUserId, "");
    }

    public void setDisSalesUserId(String disSalesUserId) {
        DisSalesUserId = disSalesUserId;
        prefsEditor.putString(DisSalesUserId, DisSalesUserId).commit();
    }

    public String getUser_LoginWith() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(user_Loginwith, "");
    }

    public void setUser_LoginWith(String Loginwith) {
        // TODO Auto-generated method stub
        prefsEditor.putString(user_Loginwith, Loginwith).commit();

    }

    public String getDistributor_Loginwith() {
        return distributor_Loginwith;
    }

    public void setDistributor_Loginwith(String distributor_Loginwith) {
        this.distributor_Loginwith = distributor_Loginwith;
    }

    public String getUser_PersonalInfo() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(PersonalInfo, "");
    }

    public void setUser_PersonalInfo(String PersonalInfo1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(PersonalInfo, PersonalInfo1).commit();

    }

    public String getUser_LoginInfo() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(LoginInfo, "");
    }

    public void setUser_LoginInfo(String LoginInfo1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(LoginInfo, LoginInfo1).commit();

    }

    public void setfi_name(String fi_name1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(fi_name, fi_name1).commit();

    }

    public String getfi_name() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(fi_name, "");
    }

    public String getl_name() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(l_name, "");
    }

    public void setl_name(String l_name1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(l_name, l_name1).commit();

    }

    public void setphone(String phone1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(phone, phone1).commit();

    }

    public String getphone() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(phone, "");
    }
    public void setemail(String email1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(email, email1).commit();

    }

    public String getemail() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(email, "");
    }

    public String getCart_QTy() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(Cart_QTy, "");
    }

    public void setCart_QTy(String Cart_QTy1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(Cart_QTy, Cart_QTy1).commit();

    }

    public String getUser_SocialIdInfo() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(SocialIdInfo, "");
    }

    public void setUser_SocialIdInfo(String SocialIdInfo1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(SocialIdInfo, SocialIdInfo1).commit();

    }

    public String getUser_SocialReInfo() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(SocialReInfo, "");
    }

    public void setUser_SocialReInfo(String SocialReInfo1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(SocialReInfo, SocialReInfo1).commit();

    }

    public String getUser_CatBack() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(CatBack, "");
    }

    public void setUser_CatBack(String CatBack1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(CatBack, CatBack1).commit();

    }

    public String getUser_SocialFirst() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(SocialFirst, "");
    }

    public void setUser_SocialFirst(String SocialFirst1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(SocialFirst, SocialFirst1).commit();

    }

    public String getUser_Sociallast() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(Sociallast, "");
    }

    public void setUser_Sociallast(String Sociallast1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(Sociallast, Sociallast1).commit();

    }

    public String getUser_Socialemail() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(Socialemail, "");
    }

    public void setUser_Socialemail(String Socialemail1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(Socialemail, Socialemail1).commit();

    }

    public String getUser_CatId() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(CatId, "");
    }

    public void setUser_CatId(String CatId1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(CatId, CatId1).commit();

    }

    public String getSort_Radio() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(Sort_Radio, "");
    }

    public void setSort_Radio(String Sort_Radio1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(Sort_Radio, Sort_Radio1).commit();

    }

    public String get_search() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(Search, "");
    }

    public void set_search(String Search1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(Search, Search1).commit();

    }

    public String getPage_Data() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(Page_Data, "");
    }

    public void setPage_Data(String Page_Data1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(Page_Data, Page_Data1).commit();

    }

    public String getFilter_Sort() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(Filter_Sort, "");
    }

    public void setFilter_Sort(String Filter_Sort1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(Filter_Sort, Filter_Sort1).commit();

    }

    public String getFilter_Option() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(Filter_Option, "");
    }

    public void setFilter_Option(String Filter_Option1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(Filter_Option, Filter_Option1).commit();

    }

    public String getFilter_SubCat() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(Filter_SubCat, "");
    }

    public void setFilter_SubCat(String Filter_SubCat1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(Filter_SubCat, Filter_SubCat1).commit();

    }

    public String getFilter_Cat() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(Filter_Cat, "");
    }

    public void setFilter_Cat(String Filter_Cat1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(Filter_Cat, Filter_Cat1).commit();

    }

    public String getProduct_Sort() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(Product_Sort, "");
    }

    public void setProduct_Sort(String Product_Sort1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(Product_Sort, Product_Sort1).commit();

    }

    public void setjobid(String jobid1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(jobid, jobid1).commit();

    }

    public String getjobid() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(jobid, "");
    }

    public void setjobname(String jobname1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(jobname, jobname1).commit();

    }

    public String getjobname() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(jobname, "");
    }

    public void setlist_id(String list_id1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(list_id, list_id1).commit();

    }

    public String getlist_id() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(list_id, "");
    }

    public void setshopping_pro_id(String shopping_pro_id1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(shopping_pro_id, shopping_pro_id1).commit();

    }

    public String getshopping_pro_id() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(shopping_pro_id, "");
    }
    public void setadd_newGroup(String add_newGroup1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(add_newGroup, add_newGroup1).commit();

    }

    public String getadd_newGroup() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(add_newGroup, "");
    }

    public String getRef_Shopping() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(Ref_Shopping, "");
    }

    public void setRef_Shopping(String Ref_Shopping1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(Ref_Shopping, Ref_Shopping1).commit();

    }

    public String getCart_Grand_total() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(Cart_Grand_total, "");
    }

    public void setCart_Grand_total(String Cart_Grand_total1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(Cart_Grand_total, Cart_Grand_total1).commit();

    }

    public void setwishid(String wishid1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(wishid, wishid1).commit();

    }

    public String getwishid() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(wishid, "");
    }
    public void setproduct_id(String product_id1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(product_id, product_id1).commit();

    }

    public String getproduct_id() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(product_id, "");
    }

    public String getRef_Detail() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(Ref_Detail, "");
    }

    public void setRef_Detail(String Ref_Detail1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(Ref_Detail, Ref_Detail1).commit();

    }

    public String getOrder_history_filter_order_status() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(Order_history_filter_order_status, "");
    }

    public void setOrder_history_filter_order_status(String Order_history_filter_order_status1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(Order_history_filter_order_status, Order_history_filter_order_status1).commit();

    }

    public String getOrder_history_filter_to_date() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(Order_history_filter_to_date, "");
    }

    public void setOrder_history_filter_to_date(String Order_history_filter_to_date1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(Order_history_filter_to_date, Order_history_filter_to_date1).commit();

    }

    public String getOrder_history_filter_from_date() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(Order_history_filter_from_date, "");
    }

    public void setOrder_history_filter_from_date(String Order_history_filter_from_date1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(Order_history_filter_from_date, Order_history_filter_from_date1).commit();

    }

    public String getOrder_history_filter_predefine() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(Order_history_filter_predefine, "");
    }

    public void setOrder_history_filter_predefine(String Order_history_filter_predefine1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(Order_history_filter_predefine, Order_history_filter_predefine1).commit();

    }

    public String getOrder_history_id() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(Order_history_id, "");
    }

    public void setOrder_history_id(String Order_history_id1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(Order_history_id, Order_history_id1).commit();

    }

    public String getUserRoleId() {
        return appSharedPrefs.getString("userRoleId","");
    }

    public void setUserRoleId(String userRoleId) {
        this.userRoleId = userRoleId;
        prefsEditor.putString("userRoleId",userRoleId).commit();
    }

    public String getTransportation() {
        return appSharedPrefs.getString("transportation","");
    }

    public void setTransportation(String transportation) {
        this.transportation=transportation;
      prefsEditor.putString("transportation",transportation).commit();
    }

    public String getUserId() {

        return appSharedPrefs.getString("userId","");
    }

    public void setUserId(String userId) {
        this.userId = userId;
        prefsEditor.putString("userId",userId).commit();
    }

    public String getCsalesId() {
        return appSharedPrefs.getString("csalesId","");
    }

    public void setCsalesId(String csalesId) {
        this.csalesId = csalesId;
        prefsEditor.putString("csalesId",csalesId).commit();
    }

    public String getSubSalesId() {
        return appSharedPrefs.getString("subSalesId","");
    }

    public void setSubSalesId(String subSalesId) {
        this.subSalesId = subSalesId;
        prefsEditor.putString("subSalesId",subSalesId).commit();
    }

    public String getCurrentPage() {
        return appSharedPrefs.getString("currentPage","");
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
        prefsEditor.putString("currentPage",currentPage).commit();
    }

    public String getSelectedUserType() {
        return appSharedPrefs.getString("selectedUserType","");
    }

    public void setSelectedUserType(String selectedUserType) {
        this.selectedUserType = selectedUserType;
        prefsEditor.putString("selectedUserType",selectedUserType).commit();
    }

    public String getSalesId() {
        return appSharedPrefs.getString(SalesId,"");
    }

    public void setSalesId(String SalesId1) {

        prefsEditor.putString(SalesId,SalesId1).commit();
    }

    public String getSubDisId() {
        return appSharedPrefs.getString("subDisId","");
    }

    public void setSubDisId(String subDisId) {
        this.subDisId = subDisId;
        prefsEditor.putString("subDisId",subDisId).commit();
    }

    public String getStatusPR() {
        return appSharedPrefs.getString("statusPR","");
    }

    public void setStatusPR(String statusPR) {
        this.statusPR = statusPR;
        prefsEditor.putString("statusPR",statusPR).commit();
    }

    public String getDisUserId() {
        return appSharedPrefs.getString("disUserId","");
    }

    public void setDisUserId(String disUserId) {
        this.disUserId = disUserId;
        prefsEditor.putString("disUserId",disUserId).commit();
    }

    public String getDisUserId22() {
        return appSharedPrefs.getString("disUserId22","");
    }

    public void setDisUserId22(String disUserId22) {
        this.disUserId22 = disUserId22;
        prefsEditor.putString("disUserId22",disUserId22).commit();
    }

    public String getStatusPR22() {
        return appSharedPrefs.getString("statusPR22","");
    }

    public void setStatusPR22(String statusPR22) {
        this.statusPR22 = statusPR22;
        prefsEditor.putString("statusPR22",statusPR22).commit();
    }

    public String getDsalesId() {
        return appSharedPrefs.getString("DsalesId","");
    }

    public void setDsalesId(String dsalesId) {
        DsalesId = dsalesId;
        prefsEditor.putString("DsalesId",DsalesId).commit();
    }

    public String getdSalesUserId() {
        return appSharedPrefs.getString("dSalesUserId","");
    }

    public void setdSalesUserId(String dSalesUserId) {
        this.dSalesUserId = dSalesUserId;
        prefsEditor.putString("dSalesUserId",dSalesUserId).commit();
    }

    public String getSRP_Id() {
        return appSharedPrefs.getString("SRP_Id","");
    }

    public void setSRP_Id(String SRP_Id) {
        this.SRP_Id = SRP_Id;
        prefsEditor.putString("SRP_Id",SRP_Id).commit();
    }

    public String getComment() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(Comment, "");
    }

    public void setComment(String Comment1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(Comment, Comment1).commit();

    }

    public String getAttachment() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(Attachment, "");
    }

    public void setAttachment(String Attachment1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(Attachment, Attachment1).commit();

    }

    public String getREF_No() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(REF_No, "");
    }

    public void setREF_No(String REF_No1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(REF_No, REF_No1).commit();

    }

    public String getOT() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(OT, "");
    }

    public void setOT(String OT_1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(OT, OT_1).commit();

    }

    public String getPT() {
        // TODO Auto-generated method stub
        return appSharedPrefs.getString(PT, "");
    }

    public void setPT(String PT_1) {
        // TODO Auto-generated method stub
        prefsEditor.putString(PT, PT_1).commit();

    }

    public boolean isBulkOrder() {
        return appSharedPrefs.getBoolean("isBulkOrder", false);
    }

    public void setBulkOrder(boolean bulkOrder) {
        isBulkOrder = bulkOrder;
        prefsEditor.putBoolean("isBulkOrder", bulkOrder).commit();
    }
}
