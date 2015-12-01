package com.cyy.naak.beans;

import java.io.Serializable;

/**
 * Created by naak on 15/6/18.
 */
public class DetailBean implements Serializable{

    public int ccid;
    public String title;

    private String ccsid;
    private String type;
    private String org_url;
    private String key;
    private String ep;
    private String ep_title;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCcsid() {
        return ccsid;
    }

    public void setCcsid(String ccsid) {
        this.ccsid = ccsid;
    }

    public String getOrg_url() {
        return org_url;
    }

    public void setOrg_url(String org_url) {
        this.org_url = org_url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEp() {
        return ep;
    }

    public void setEp(String ep) {
        this.ep = ep;
    }

    public String getEp_title() {
        return ep_title;
    }

    public void setEp_title(String ep_title) {
        this.ep_title = ep_title;
    }

    @Override
    public String toString() {
        return "DetailBean{" +
                "ccsid='" + ccsid + '\'' +
                ", type='" + type + '\'' +
                ", org_url='" + org_url + '\'' +
                ", key='" + key + '\'' +
                ", ep='" + ep + '\'' +
                ", ep_title='" + ep_title + '\'' +
                '}';
    }
}
