package com.agraeta.user.btl.model;

import com.agraeta.user.btl.Bean_Product;
import com.agraeta.user.btl.Bean_Schme_value;

/**
 * Created by Nivida new on 19-Aug-17.
 */

public class GetSchemeDetailResponse extends AppModel {

    SchemeDetailData data = new SchemeDetailData();

    public SchemeDetailData getData() {
        return data;
    }

    public class SchemeDetailData {
        Bean_Schme_value Scheme = new Bean_Schme_value();
        Bean_Product GetProduct = new Bean_Product();

        public Bean_Schme_value getScheme() {
            return Scheme;
        }

        public Bean_Product getGetProduct() {
            return GetProduct;
        }
    }
}
