package com.agraeta.user.btl.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nivida new on 15-Apr-17.
 */

public class TallyBill extends AppModel {

    @SerializedName("data")
    private TallyBillData billData;

    public TallyBillData getBillData() {
        return billData;
    }

    public class TallyBillData{
        @SerializedName("total_due_amount")
        String dueAmount="";

        @SerializedName("total_outstanding_balance")
        String outstandingAmount="";

        @SerializedName("total_due_amount_text")
        String dueAmountLabel="";

        @SerializedName("total_outstanding_balance_text")
        String outstandingLabel="";

        public String getDueAmount() {
            return dueAmount;
        }

        public String getOutstandingAmount() {
            return outstandingAmount;
        }

        public String getDueAmountLabel() {
            return dueAmountLabel;
        }

        public String getOutstandingLabel() {
            return outstandingLabel;
        }
    }
}
