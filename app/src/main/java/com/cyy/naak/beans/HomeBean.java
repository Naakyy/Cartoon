package com.cyy.naak.beans;

import java.io.Serializable;

/**
 * Created by naak on 15/7/1.
 */
public class HomeBean{

    public int ccid;
    public String title;
    public String eps;  //总集数
    public String isclosed;
    public String pic;

    public HomeBean() {
    }

    public int getCcid() {
        return ccid;
    }

    public void setCcid(int ccid) {
        this.ccid = ccid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEps() {
        return eps;
    }

    public void setEps(String eps) {
        this.eps = eps;
    }

    public String getIsclosed() {
        return isclosed;
    }

    public void setIsclosed(String isclosed) {
        this.isclosed = isclosed;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public HomeBean(int ccid, String title, String eps, String isclosed, String pic) {
        this.ccid = ccid;
        this.title = title;
        this.eps = eps;
        this.isclosed = isclosed;
        this.pic = pic;
    }

    @Override
    public String toString() {
        return "HomeBean{" +
                "ccid='" + ccid + '\'' +
                ", title='" + title + '\'' +
                ", eps='" + eps + '\'' +
                ", isclosed='" + isclosed + '\'' +
                ", pic='" + pic + '\'' +
                '}';
    }
}
