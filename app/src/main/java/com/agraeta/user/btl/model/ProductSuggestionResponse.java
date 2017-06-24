package com.agraeta.user.btl.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 24-Jun-17.
 */

public class ProductSuggestionResponse extends AppModel {

    List<SearchSuggestion> data = new ArrayList<>();

    public List<SearchSuggestion> getData() {
        return data;
    }

    public class SearchSuggestion {
        String id = "";
        String product_name = "";

        public String getId() {
            return id;
        }

        public String getProduct_name() {
            return product_name;
        }
    }
}
