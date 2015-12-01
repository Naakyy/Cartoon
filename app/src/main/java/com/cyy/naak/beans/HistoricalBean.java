package com.cyy.naak.beans;

/**
 * Created by naak on 15/7/15.
 */
public class HistoricalBean {
    public int ccid;
    public String hTitle;
    public String ep;
    public String orgUrl;
    public String createTime;

    public int getCcid() {
        return ccid;
    }

    public void setCcid(int ccid) {
        this.ccid = ccid;
    }

    public String gethTitle() {
        return hTitle;
    }

    public void sethTitle(String hTitle) {
        this.hTitle = hTitle;
    }

    public String getEp() {
        return ep;
    }

    public void setEp(String ep) {
        this.ep = ep;
    }

    public String getOrgUrl() {
        return orgUrl;
    }

    public void setOrgUrl(String orgUrl) {
        this.orgUrl = orgUrl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "HistoricalBean{" +
                "ccid=" + ccid +
                ", hTitle='" + hTitle + '\'' +
                ", ep='" + ep + '\'' +
                '}';
    }
}
