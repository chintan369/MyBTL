package com.agraeta.user.btl.CompanySalesPerson;

import java.io.Serializable;

/**
 * Created by SEO on 9/21/2016.
 */
public class Bean_SkipIcon implements Serializable {

    String id;
    String user_id;
    String skip_person_id;
    String reason_title;
    String other_reason_title;
    String latitude;
    String longitude;
    String attachment_1;
    String attachment_2;
    String comment;
    String created;
    String modified;
    String skipByFname;
    String skipByLname;
    String skipForFname;
    String skipForLname;

    public Bean_SkipIcon() {
    }

    public Bean_SkipIcon(String id, String user_id, String skip_person_id, String reason_title, String other_reason_title, String latitude, String longitude, String attachment_1, String attachment_2, String comment, String created, String modified, String skipByFname, String skipByLname, String skipForFname, String skipForLname) {
        this.id = id;
        this.user_id = user_id;
        this.skip_person_id = skip_person_id;
        this.reason_title = reason_title;
        this.other_reason_title = other_reason_title;
        this.latitude = latitude;
        this.longitude = longitude;
        this.attachment_1 = attachment_1;
        this.attachment_2 = attachment_2;
        this.comment = comment;
        this.created = created;
        this.modified = modified;
        this.skipByFname = skipByFname;
        this.skipByLname = skipByLname;
        this.skipForFname = skipForFname;
        this.skipForLname = skipForLname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSkip_person_id() {
        return skip_person_id;
    }

    public void setSkip_person_id(String skip_person_id) {
        this.skip_person_id = skip_person_id;
    }

    public String getReason_title() {
        return reason_title;
    }

    public void setReason_title(String reason_title) {
        this.reason_title = reason_title;
    }

    public String getOther_reason_title() {
        return other_reason_title;
    }

    public void setOther_reason_title(String other_reason_title) {
        this.other_reason_title = other_reason_title;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAttachment_1() {
        return attachment_1;
    }

    public void setAttachment_1(String attachment_1) {
        this.attachment_1 = attachment_1;
    }

    public String getAttachment_2() {
        return attachment_2;
    }

    public void setAttachment_2(String attachment_2) {
        this.attachment_2 = attachment_2;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getSkipByFname() {
        return skipByFname;
    }

    public void setSkipByFname(String skipByFname) {
        this.skipByFname = skipByFname;
    }

    public String getSkipByLname() {
        return skipByLname;
    }

    public void setSkipByLname(String skipByLname) {
        this.skipByLname = skipByLname;
    }

    public String getSkipForFname() {
        return skipForFname;
    }

    public void setSkipForFname(String skipForFname) {
        this.skipForFname = skipForFname;
    }

    public String getSkipForLname() {
        return skipForLname;
    }

    public void setSkipForLname(String skipForLname) {
        this.skipForLname = skipForLname;
    }
}
