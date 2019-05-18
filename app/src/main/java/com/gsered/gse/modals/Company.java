package com.gsered.gse.modals;

public class Company {

    private String compName, compDescp, url, imgUrl, admitad_id, raise, activate, probability, coupon, category;

    public Company(){
    }

    public Company(String compName, String compDescp, String url, String imgUrl, String admitad_id, String raise, String activate, String probability, String coupon, String category) {
        this.compName = compName;
        this.compDescp = compDescp;
        this.url = url;
        this.imgUrl = imgUrl;
        this.admitad_id = admitad_id;
        this.raise = raise;
        this.activate = activate;
        this.probability = probability;
        this.coupon = coupon;
        this.category = category;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getCompDescp() {
        return compDescp;
    }

    public void setCompDescp(String compDescp) {
        this.compDescp = compDescp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getAdmitad_id() {
        return admitad_id;
    }

    public void setAdmitad_id(String admitad_id) {
        this.admitad_id = admitad_id;
    }

    public String getRaise() {
        return raise;
    }

    public void setRaise(String raise) {
        this.raise = raise;
    }

    public String getActivate() {
        return activate;
    }

    public void setActivate(String activate) {
        this.activate = activate;
    }

    public String getProbability() {
        return probability;
    }

    public void setProbability(String probability) {
        this.probability = probability;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
