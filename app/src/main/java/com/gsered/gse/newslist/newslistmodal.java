package com.gsered.gse.newslist;

public class newslistmodal {
    String imageurl,title,byline,link;
    Boolean showplay;

    public newslistmodal(String imageurl, String title, String byline, String link, Boolean showplay) {
        this.imageurl = imageurl;
        this.title = title;
        this.byline = byline;
        this.link = link;
        this.showplay = showplay;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getByline() {
        return byline;
    }

    public void setByline(String byline) {
        this.byline = byline;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Boolean getShowplay() {
        return showplay;
    }

    public void setShowplay(Boolean showplay) {
        this.showplay = showplay;
    }
}
