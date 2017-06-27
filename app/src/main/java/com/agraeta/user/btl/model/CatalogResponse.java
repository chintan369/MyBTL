package com.agraeta.user.btl.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 27-Jun-17.
 */

public class CatalogResponse extends AppModel {

    List<Catalog> data = new ArrayList<>();

    public List<Catalog> getData() {
        return data;
    }

    public class Catalog {
        String id = "";
        String name = "";
        String img_url = "";
        String file_url = "";
        String created = "";

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getImg_url() {
            return img_url;
        }

        public String getFile_url() {
            return file_url;
        }

        public String getCreated() {
            return created;
        }
    }
}
