package com.agraeta.user.btl.model;

import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.model.area.AreaData;
import com.agraeta.user.btl.model.combooffer.ComboCartEdit;
import com.agraeta.user.btl.model.combooffer.ComboOfferData;
import com.agraeta.user.btl.model.combooffer.ComboOfferDetail;
import com.agraeta.user.btl.model.coupons.CouponData;
import com.agraeta.user.btl.model.coupons.SchemeData;
import com.agraeta.user.btl.model.enquiries.BusinessEnquiryData;
import com.agraeta.user.btl.model.enquiries.CareerData;
import com.agraeta.user.btl.model.enquiries.CorporateOfficeData;
import com.agraeta.user.btl.model.enquiries.CustomerComplaintData;
import com.agraeta.user.btl.model.enquiries.ProductEnquiryData;
import com.agraeta.user.btl.model.enquiries.ProductRegistrationData;
import com.agraeta.user.btl.model.enquiries.RegisteredDealerData;
import com.agraeta.user.btl.model.notifications.InboxResponse;
import com.agraeta.user.btl.model.quotation.QuotationResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface AdminAPI {

    @FormUrlEncoded
    @POST(Globals.USER_LIST)
    Call<UserList> userList(@Field("owner_id") String ownerID,@Field("role_id") String roleID, @Field("page") int page);

    @FormUrlEncoded
    @POST(Globals.USER_LIST_UNDER_SALESPERSON)
    Call<UserList> userListUnderSales(@Field("user_id") String ownerID,@Field("role_id") String roleID, @Field("user_type_id") String userType);

    @FormUrlEncoded
    @POST(Globals.ORDER_LIST_FOR_ADMIN)
    Call<OrderData> orderData(@Field("user_id") String userID,@Field("role_id") String roleID, @Field("page") int page);

    @FormUrlEncoded
    @POST(Globals.ORDER_DETAIL_FOR_ADMIN)
    Call<OrderDetailData> orderDetailData(@Field("order_id") String orderID);

    @FormUrlEncoded
    @POST(Globals.ADMIN_DASHBOARD)
    Call<Dashboard> dashBoardData(@Field("from_date") String fromDate,@Field("to_date") String toDate);

    @FormUrlEncoded
    @POST(Globals.ORDER_TRACKING)
    Call<OrderTrackingData> trackingDataCall(@Field("order_id") String orderID);

    @FormUrlEncoded
    @POST(Globals.ORDER_INQUIRY)
    Call<OrderEnquiryData> enquiryDataCall(@Field("order_id") String orderID);

    @FormUrlEncoded
    @POST(Globals.ALL_ENQUIRY_COUNT)
    Call<EnquiryCounts> enquiryCountCall(@Field("from_date") String fromDate, @Field("to_date") String toDate);

    @FormUrlEncoded
    @POST(Globals.PRODUCT_ENQUIRY_LIST)
    Call<ProductEnquiryData> productEnquiryDataCall(@Field("from_date") String fromDate,@Field("to_date") String toDate,@Field("page") int page);

    @FormUrlEncoded
    @POST(Globals.BUSINESS_ENQUIRY)
    Call<BusinessEnquiryData> businessEnquiryDataCall(@Field("from_date") String fromDate,@Field("to_date") String toDate,@Field("page") int page);

    @FormUrlEncoded
    @POST(Globals.CUSTOMER_COMPLAINTS)
    Call<CustomerComplaintData> complaintDataCall(@Field("from_date") String fromDate,@Field("to_date") String toDate,@Field("page") int page);

    @FormUrlEncoded
    @POST(Globals.CORP_OFFICE_ENQUIRY_LIST)
    Call<CorporateOfficeData> officeDataCall(@Field("from_date") String fromDate, @Field("to_date") String toDate, @Field("page") int page);

    @FormUrlEncoded
    @POST(Globals.PRODUCT_REGISTRATION_LIST)
    Call<ProductRegistrationData> registrationDataCall(@Field("from_date") String fromDate, @Field("to_date") String toDate, @Field("page") int page);

    @FormUrlEncoded
    @POST(Globals.CARRER_LIST)
    Call<CareerData> careerDataCall(@Field("from_date") String fromDate, @Field("to_date") String toDate, @Field("page") int page);

    @FormUrlEncoded
    @POST(Globals.COUPON_LIST)
    Call<CouponData> couponDataCall(@Field("from_date") String fromDate, @Field("to_date") String toDate, @Field("page") int page);

    @FormUrlEncoded
    @POST(Globals.SCHEME_LIST)
    Call<SchemeData> schemeDataCall(@Field("from_date") String fromDate, @Field("to_date") String toDate, @Field("page") int page);

    @FormUrlEncoded
    @POST(Globals.REORDER_PRODUCT_IN_CART)
    Call<AppModel> reOrderDataCall(@Field("user_id") String userID,@Field("role_id") String roleID,@Field("owner_id") String ownerID, @Field("product_id") String productIDs);

    @Multipart
    @POST(Globals.NONMRP_PRODUCT_ENQUIRY)
    Call<AppModel> productEnquiryCall(@Part("full_name") String fullName, @Part("firm_name") String firmName, @Part("email") String email, @Part("mobile") String mobile, @Part("product_name") String productName, @Part("product_id") String productID, @Part("comment") String comment,@Part List<MultipartBody.Part> files);

    @FormUrlEncoded
    @POST(Globals.TALLY_BILLS)
    Call<TallyBill> tallyBillCall(@Field("user_id") String userID);

    @FormUrlEncoded
    @POST(Globals.COMBO_LIST)
    Call<ComboOfferData> comboOfferDataCall(@Field("user_id") String userID,@Field("role_id") String roleID);

    @FormUrlEncoded
    @POST(Globals.COMBO_DETAIL)
    Call<ComboOfferDetail> comboOfferDetailCall(@Field("role_id") String roleID, @Field("combo_id") String comboID);

    @FormUrlEncoded
    @POST(Globals.ADD_COMBO)
    Call<AppModel> addComboCall(@Field("combo_cart_id") String comboCartID,@Field("combo_id") String comboID, @Field("user_id") String userID, @Field("role_id") String roleID, @Field("owner_id") String ownerID, @Field("total_qty") String totalQty, @Field("total_mrp") String totalMRP, @Field("total_selling_price") String totalSellingPrice, @Field("products") String products);

    @FormUrlEncoded
    @POST(Globals.DELETE_COMBO)
    Call<AppModel> deleteCombo(@Field("combo_cart_id") String comboCartID);

    @FormUrlEncoded
    @POST(Globals.EDIT_COMBO_DETAIL)
    Call<ComboCartEdit> editComboCartDetailCall(@Field("combo_cart_id") String comboCartID,@Field("combo_id") String comboID,@Field("role_id") String roleID);

    @POST(Globals.COUNTRY_LIST)
    Call<AreaData> getCountryList();

    @FormUrlEncoded
    @POST(Globals.STATE_LIST)
    Call<AreaData> getAreasData(@Field("country_id") String countryID,@Field("state_id") String stateID);

    @FormUrlEncoded
    @POST(Globals.CITY_LIST)
    Call<AreaData> getCityData(@Field("state_id") String stateID);

    @FormUrlEncoded
    @POST(Globals.AREA_LIST)
    Call<AreaData> getAreaData(@Field("city_id") String cityID);

    @POST(Globals.USER_ROLES)
    Call<AreaData> getUserRoles();

    @Multipart
    @POST(Globals.BECOME_SELLER)
    Call<AppModel> becomeDealerCall(@Part("first_name") String firstName,@Part("last_name") String lastName,@Part("email") String emailID,@Part("role_id") String roleID, @Part("contact_no") String contactNo,@Part("firm_name") String firmName, @Part("contact_person") String contactPerson, @Part("alternate_no") String alterNateNo, @Part("address_1") String address1,@Part("address_2") String address2, @Part("state_id") String stateID, @Part("city_id") String cityID, @Part("pincode") String pinCode,@Part("pan_no") String panNo,@Part("service_tax_no") String STNo,@Part("vat_tin_no") String vatNo,@Part List<MultipartBody.Part> files);

    @FormUrlEncoded
    @POST(Globals.ADD_OTHER_CITY)
    Call<AppModel> addOtherCity(@Field("state_id") String stateID, @Field("city_name") String cityName);

    @Multipart
    @POST(Globals.ADD_UNREGISTERED_USER)
    Call<AppModel> addUnRegisteredUser(@Part("sales_person_id") String salesPersonID,@Part("firm_name") String firmName,@Part("address_1") String addr1,@Part("address_2") String addr2,@Part("address_3") String addr3,@Part("country") String country,@Part("state") String state,@Part("city") String city,@Part("area") String area,@Part("pincode") String pincode,@Part("contact_person") String contact_person,@Part("email") String email,@Part("mobile_no") String mobile_no,@Part("party_report") String party_report,@Part("dealing_in_brand") String dealing_in_brand,@Part("customer_type") String customer_type,@Part("comments") String comments,@Part("joint_visit_with") String joint_visit_with,@Part List<MultipartBody.Part> files);

    @Multipart
    @POST(Globals.ADD_UNREGISTERED_USER)
    Call<AppModel> addRegisteredUser(@Part("user_id") String skipPersonID,@Part("skip_person_id") String salesPersonID,@Part("reason_title") String reasonTitle,@Part("other_reason_title") String otherReasonTitle,@Part("latitude") String latitude,@Part("longitude") String longitude,@Part("firm_name") String firmName,@Part("address_1") String addr1,@Part("address_2") String addr2,@Part("address_3") String addr3,@Part("country") String country,@Part("state") String state,@Part("city") String city,@Part("area") String area,@Part("pincode") String pincode,@Part("contact_person") String contact_person,@Part("email") String email,@Part("mobile_no") String mobile_no,@Part("party_report") String party_report,@Part("dealing_in_brand") String dealing_in_brand,@Part("customer_type") String customer_type,@Part("comment") String comments,@Part("joint_visit_with") String joint_visit_with,@Part("is_past_order") String isPastOrder,@Part("order_id") String orderID,@Part List<MultipartBody.Part> files);

    @FormUrlEncoded
    @POST(Globals.GET_UNREGISTERED_USER)
    Call<UnregisteredUserData> getAllUnregisteredUser(@Field("sales_person_id") String salesPersonID);

    @POST(Globals.GET_REGISTERED_SELLER)
    Call<RegisteredDealerData> getRegisteredDealer();

    @FormUrlEncoded
    @POST(Globals.GET_REGISTERED_USER_DSR)
    Call<RegisteredUserData> getAllRegisteredUserDSR(@Field("user_id") String userID, @Field("skip_person_id") String salesPersonID);

    @FormUrlEncoded
    @POST(Globals.SEND_QUOTATION)
    Call<AppModel> sendQuotationCall(@Field("user_id") String userID,@Field("role_id") String roleID, @Field("email_id") String emailID,@Field("email_cc") String emailCC, @Field("title") String title,@Field("to_name") String toName, @Field("quotation_id") String quotationID, @Field("owner_id") String ownerID,@Field("add_less") String addLessData, @Field("vat") String vat);

    @POST(Globals.VIDEO_GALLERY)
    Call<VideoResponse> videoResponseCall();

    @FormUrlEncoded
    @POST(Globals.PAGE_DOWNLOAD)
    Call<PageResponse> getInformtionPage(@Field("id") String id);

    @FormUrlEncoded
    @POST(Globals.GET_NOTIFICATION_LIST)
    Call<InboxResponse> inboxResponseCall(@Field("owner_id") String ownerID, @Field("user_id") String userID, @Field("page") String page);

    @FormUrlEncoded
    @POST(Globals.CLEAR_NOTIFICATIONS)
    Call<AppModel> clearNotificationCall(@Field("owner_id") String ownerID, @Field("user_id") String userID);

    @FormUrlEncoded
    @POST(Globals.ORDER_TRACKING)
    Call<OrderInvoiceResponse> orderInvoiceCall(@Field("order_id") String orderID);

    @FormUrlEncoded
    @POST(Globals.GET_TOUR)
    Call<TourResponse> getTourData(@Field("user_id") String userID);

    @FormUrlEncoded
    @POST(Globals.USER_SALES_STATEWISE)
    Call<RegisteredUserTourResponse> userTourResponseCall(@Field("state_id") String stateID,@Field("search_term") String searchBy);

    @FormUrlEncoded
    @POST(Globals.GET_UNREGISTERED_USER)
    Call<UnregisteredUserData> unregisteredUserTourCall(@Field("state_id") String stateID, @Field("search_term") String searchBy);

    @FormUrlEncoded
    @POST(Globals.PRODUCT_STOCK_REPORT)
    Call<ProductStockResponse> productStockResponseCall(@Field("search_term") String searchKey);

    @FormUrlEncoded
    @POST(Globals.SEARCH_BY_KEYWORD)
    Call<ProductSuggestionResponse> suggestionResponseCall(@Field("product_name") String product_name);

    @FormUrlEncoded
    @POST(Globals.GET_QUOTATION_LIST)
    Call<QuotationResponse> quotationResponseCall(@Field("from_date") String fromDate, @Field("to_date") String toDate, @Field("page") String page);
}
