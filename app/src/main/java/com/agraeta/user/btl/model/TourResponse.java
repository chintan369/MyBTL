package com.agraeta.user.btl.model;

/**
 * Created by Nivida new on 24-Jun-17.
 */

public class TourResponse extends AppModel {

    Tour data = new Tour();

    public Tour getData() {
        return data;
    }

    public class Tour {
        String state_id = "";
        String tour_name = "";
        String id = "";
        String state_name = "";

        public String getState_id() {
            return state_id;
        }

        public String getTour_name() {
            return tour_name;
        }

        public String getId() {
            return id;
        }

        public String getState_name() {
            return state_name;
        }
    }
}
