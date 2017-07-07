package com.agraeta.user.btl.model.notifications;

import com.agraeta.user.btl.model.AppModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 19-Jun-17.
 */

public class InboxResponse extends AppModel {

    List<InboxData> data=new ArrayList<>();

    public List<InboxData> getData() {
        return data;
    }

    public class InboxData{
        Inbox Inbox=new Inbox();

        public InboxResponse.Inbox getInbox() {
            return Inbox;
        }
    }


    public class Inbox{
        String id="";
        String user_id="";
        String owner_id="";
        String msg="";
        String is_read="";
        String created="";

        public String getId() {
            return id;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getOwner_id() {
            return owner_id;
        }

        public String getMsg() {
            return msg;
        }

        public String getIs_read() {
            return is_read;
        }

        public void setIs_read(String is_read) {
            this.is_read = is_read;
        }

        public String getCreated() {
            return created;
        }
    }
}
