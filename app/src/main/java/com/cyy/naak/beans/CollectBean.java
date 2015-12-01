package com.cyy.naak.beans;

import android.widget.CompoundButton;

import com.cyy.naak.activity.DetailsActivity;
import com.cyy.naak.db.CollectDAO;

import java.util.ArrayList;

/**
 * Created by naak on 15/7/9.
 */
public class CollectBean {

    public int ccid;
    public String cTitle;
    public String eps;
    public String pic;

    public CollectBean() {
    }

    public int getCcid() {
        return ccid;
    }

    public void setCcid(int ccid) {
        this.ccid = ccid;
    }

    public String getcTitle() {
        return cTitle;
    }

    public void setcTitle(String cTitle) {
        this.cTitle = cTitle;
    }

    public String getEps() {
        return eps;
    }

    public void setEps(String eps) {
        this.eps = eps;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public String toString() {
        return "CollectBean{" +
                "ccid=" + ccid +
                ", cTitle='" + cTitle + '\'' +
                ", eps='" + eps + '\'' +
                ", pic='" + pic + '\'' +
                '}';
    }
}
