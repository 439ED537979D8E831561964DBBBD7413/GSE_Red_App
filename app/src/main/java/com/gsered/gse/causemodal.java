package com.gsered.gse;

public class causemodal {

    private String id,image,title,description,companyname,buttontext;

    public causemodal(String id, String image, String title, String description, String companyname, String buttontext) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.description = description;
        this.companyname = companyname;
        this.buttontext = buttontext;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getButtontext() {
        return buttontext;
    }

    public void setButtontext(String buttontext) {
        this.buttontext = buttontext;
    }
}
