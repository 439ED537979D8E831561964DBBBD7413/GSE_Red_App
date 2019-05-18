package com.gsered.gse.games.category_without_title;

public class CategoryModals {
    String imageurl, url, gamename, logo;

    public CategoryModals(String imageurl, String url, String gamename, String logo) {
        this.imageurl = imageurl;
        this.url = url;
        this.gamename = gamename;
        this.logo = logo;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGamename() {
        return gamename;
    }

    public void setGamename(String gamename) {
        this.gamename = gamename;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
