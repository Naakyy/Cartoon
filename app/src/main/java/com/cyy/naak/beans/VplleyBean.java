package com.cyy.naak.beans;

/**
 * Created by naak on 15/6/18.
 */
public class VplleyBean {
    private String ccid;
    private String title;
    private String eps;
    private String isclosed;
    private String pic;

    public String getCcid() {
        return ccid;
    }

    public void setCcid(String ccid) {
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

    public VplleyBean() {
    }

    public VplleyBean(String ccid, String title, String eps, String isclosed, String pic) {
        this.ccid = ccid;
        this.title = title;
        this.eps = eps;
        this.isclosed = isclosed;
        this.pic = pic;
    }

    @Override
    public String toString() {
        return "VplleyBean{" +
                "ccid='" + ccid + '\'' +
                ", title='" + title + '\'' +
                ", eps='" + eps + '\'' +
                ", isclosed='" + isclosed + '\'' +
                ", pic='" + pic + '\'' +
                '}';
    }
}
