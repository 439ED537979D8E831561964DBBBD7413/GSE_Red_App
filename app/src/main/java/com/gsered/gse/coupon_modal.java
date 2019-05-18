package com.gsered.gse;

public class coupon_modal {

    private String title,couponcode;
    private String description;
    private String expires,verified;
    private String used;
    private String url;

    public coupon_modal() {
    }

    public coupon_modal(String title, String couponcode, String description, String expires, String verified, String used, String url) {
        this.title = title;
        this.couponcode = couponcode;
        this.description = description;
        this.expires = expires;
        this.verified = verified;
        this.used = used;
        this.url = url;
    }
    //getter
    public String getTitle() {
        return title;
    }

    public String getCouponcode() {
        return couponcode;
    }

    public String getDescription() {
        return description;
    }

    public String getExpires() {
        return expires;
    }

    public String getVerified() {
        return verified;
    }

    public String getUsed() {
        return used;
    }

    public String getUrl() {
        return url;
    }
    //setter
    public void setTitle(String title) {
        this.title = title;
    }

    public void setCouponcode(String couponcode) {
        this.couponcode = couponcode;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
