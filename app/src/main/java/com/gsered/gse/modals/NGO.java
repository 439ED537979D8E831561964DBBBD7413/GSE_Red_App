package com.gsered.gse.modals;


public class NGO {

    private String compName, compDescp, url, imgUrl,nid;

    public NGO(){

    }

    public NGO(String compName){
        this.compName = compName;
        this.imgUrl = "https://cdn.sstatic.net/Sites/stackoverflow/img/apple-touch-icon@2.png?v=73d79a89bded";
        this.url = "https://www.google.com";
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }
}
