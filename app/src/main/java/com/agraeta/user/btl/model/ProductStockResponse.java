package com.agraeta.user.btl.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 24-Jun-17.
 */

public class ProductStockResponse extends AppModel {

    List<ProductStock> data = new ArrayList<>();

    public List<ProductStock> getData() {
        return data;
    }

    public class ProductStock {
        String product_category = "";
        String product_name = "";
        String pack_of = "";
        List<CompanyName> company_name = new ArrayList<>();

        public String getProduct_category() {
            return product_category;
        }

        public String getProduct_name() {
            return product_name;
        }

        public String getPack_of() {
            return pack_of;
        }

        public List<CompanyName> getCompany_name() {
            return company_name;
        }

        public String getTotalQty() {
            int totalQty = 0;

            for (int i = 0; i < company_name.size(); i++) {
                try {
                    totalQty += Integer.parseInt(company_name.get(i).getQty());
                } catch (Exception e) {
                    totalQty += 0;
                }
            }

            return String.valueOf(totalQty);
        }
    }

    public class CompanyName {
        String qty = "";
        String company_name = "";

        public String getQty() {
            return qty;
        }

        public String getCompany_name() {
            return company_name;
        }
    }
}
