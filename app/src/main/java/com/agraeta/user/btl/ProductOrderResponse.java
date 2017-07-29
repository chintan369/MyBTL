package com.agraeta.user.btl;

import com.agraeta.user.btl.model.AppModel;

/**
 * Created by NWSPL-17 on 22-Aug-17.
 */

public class ProductOrderResponse extends AppModel {

    SchemeDetailData data = new SchemeDetailData();

    public SchemeDetailData getData() {
        return data;
    }

    public class SchemeDetailData {
        Bean_Product Product = new Bean_Product();

        public Bean_Product getGetProduct() {
            return Product;
        }
    }
}
