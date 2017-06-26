package com.agraeta.user.btl.model.quotation;

import com.agraeta.user.btl.model.AppModel;
import com.agraeta.user.btl.model.RegisteredUserTourResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 26-Jun-17.
 */

public class QuotationResponse extends AppModel {

    List<QuotationData> data = new ArrayList<>();

    public List<QuotationData> getData() {
        return data;
    }

    public class QuotationData {
        Quotation Quotation = new Quotation();
        User User = new User();
        User Owner = new User();

        public QuotationResponse.Quotation getQuotation() {
            return Quotation;
        }

        public QuotationResponse.User getUser() {
            return User;
        }

        public QuotationResponse.User getOwner() {
            return Owner;
        }
    }

    public class Quotation {
        String id = "";
        String inv_no = "";
        String user_id = "";
        String role_id = "";
        String owner_role_id = "";
        String owner_id = "";
        String email_id = "";
        String email_cc = "";
        String title = "";
        String to_name = "";
        String file_path = "";
        String add_less = "";
        String vat = "";
        String status = "";
        String created = "";

        public String getId() {
            return id;
        }

        public String getInv_no() {
            return inv_no;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getRole_id() {
            return role_id;
        }

        public String getOwner_role_id() {
            return owner_role_id;
        }

        public String getOwner_id() {
            return owner_id;
        }

        public String getEmail_id() {
            return email_id;
        }

        public String getEmail_cc() {
            return email_cc;
        }

        public String getTitle() {
            return title;
        }

        public String getTo_name() {
            return to_name;
        }

        public String getFile_path() {
            return file_path;
        }

        public String getAdd_less() {
            return add_less;
        }

        public String getVat() {
            return vat;
        }

        public String getStatus() {
            return status;
        }

        public String getCreated() {
            return created;
        }
    }

    public class User extends com.agraeta.user.btl.model.User {
        RegisteredUserTourResponse.Distributor Distributor = new RegisteredUserTourResponse.Distributor();

        public RegisteredUserTourResponse.Distributor getDistributor() {
            return Distributor;
        }
    }

}
