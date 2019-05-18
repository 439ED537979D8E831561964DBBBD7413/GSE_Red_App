package com.gsered.gse.share;

public class share_modal {

    private String image;
    private String title;
    private String date;
    private String sharetext;

    public share_modal() {
    }

    public share_modal(String image, String title, String date, String sharetext) {
        this.image = image;
        this.title = title;
        this.date = date;
        this.sharetext = sharetext;
    }

    // getter
    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getSharetext() {
        return sharetext;
    }

    //setter
    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setSharetext(String sharetext) {
        this.sharetext = sharetext;
    }
}
