package com.agraeta.user.btl.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NWSPL-17 on 14-Sep-17.
 */

public class LROrderInvoice {


    OrderInvoiceItem OrderInvoice = new OrderInvoiceItem();
    InvoiceLR InvoiceLr = new InvoiceLR();

    public OrderInvoiceItem getOrderInvoice() {
        return OrderInvoice;
    }


    public InvoiceLR getLr() {
        return InvoiceLr;
    }

    public class OrderInvoiceItem {
        String id = "";
        String order_id = "";
        String invoice_no = "";
        String order_total = "";
        String invoice_date = "";
        String invoice_file = "";

        public String getId() {
            return id;
        }

        public String getOrder_id() {
            return order_id;
        }

        public String getInvoice_no() {
            return invoice_no;
        }

        public String getOrder_total() {
            return order_total;
        }

        public String getInvoice_date() {
            return invoice_date;
        }

        public String getInvoice_file() {
            return invoice_file;
        }
    }

    public class InvoiceLR {
        String id = "";
        String invoice_id = "";
        String lr_id = "";
        String created = "";

        List<LRDetail> LrDetail = new ArrayList<>();

        public String getId() {
            return id;
        }

        public String getInvoice_id() {
            return invoice_id;
        }

        public String getLr_id() {
            return lr_id;
        }

        public String getCreated() {
            return created;
        }

        public List<LRDetail> getLrDetail() {
            return LrDetail;
        }
    }

    public class LRDetail {
        String lr_no = "";
        String lr_date = "";
        String courier_receipt_file = "";
        String transporter_name = "";
        String destination = "";
        String no_bundles = "";
        String remarks = "";
        String invoice_no = "";
        String invoice_date = "";
        String cc_attached = "";

        public String getLr_no() {
            return lr_no;
        }

        public String getLr_date() {
            return lr_date;
        }

        public String getCourier_receipt_file() {
            return courier_receipt_file;
        }

        public String getTransporter_name() {
            return transporter_name;
        }

        public String getDestination() {
            return destination;
        }

        public String getNo_bundles() {
            return no_bundles;
        }

        public String getRemarks() {
            return remarks;
        }

        public String getInvoice_no() {
            return invoice_no;
        }

        public String getInvoice_date() {
            return invoice_date;
        }

        public String getCc_attached() {
            return cc_attached;
        }
    }


}
