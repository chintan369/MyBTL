package com.agraeta.user.btl;

/**
 * Created by ravina on 4/13/2016.
 */
public class Bean_JobVacancy {

    String vid;
    String jobtitle;
    String novacancy;
    String desc;

    public Bean_JobVacancy() {
    }

    public Bean_JobVacancy(String vid, String jobtitle, String novacancy, String desc) {
        this.vid = vid;
        this.jobtitle = jobtitle;
        this.novacancy = novacancy;
        this.desc = desc;

    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getJobtitle() {
        return jobtitle;
    }

    public void setJobtitle(String jobtitle) {
        this.jobtitle = jobtitle;
    }

    public String getNovacancy() {
        return novacancy;
    }

    public void setNovacancy(String novacancy) {
        this.novacancy = novacancy;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
