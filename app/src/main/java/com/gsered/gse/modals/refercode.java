package com.gsered.gse.modals;

public class refercode {
    private String title,subtitle,description,date,img,promocode,id;

    public refercode() {
    }

    public refercode(String title, String subtitle, String description, String date, String img, String promocode, String id) {
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.date = date;
        this.img = img;
        this.promocode = promocode;
        this.id = id;
    }

    //getter
    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getImg() {
        return img;
    }

    public String getPromocode() {
        return promocode;
    }

    public String getId() {
        return id;
    }

    //setter
    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setPromocode(String promocode) {
        this.promocode = promocode;
    }

    public void setId(String id) {
        this.id = id;
    }
}
