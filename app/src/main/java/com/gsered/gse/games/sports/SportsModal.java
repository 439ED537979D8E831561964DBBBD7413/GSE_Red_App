package com.gsered.gse.games.sports;

public class SportsModal {
    String imageurl,link,title,logo,gamename;
    Boolean showtitle;

    public SportsModal(String imageurl, String link, String title, String logo, String gamename, Boolean showtitle) {
        this.imageurl = imageurl;
        this.link = link;
        this.title = title;
        this.logo = logo;
        this.gamename = gamename;
        this.showtitle = showtitle;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getGamename() {
        return gamename;
    }

    public void setGamename(String gamename) {
        this.gamename = gamename;
    }

    public Boolean getShowtitle() {
        return showtitle;
    }

    public void setShowtitle(Boolean showtitle) {
        this.showtitle = showtitle;
    }
}
