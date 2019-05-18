package com.gsered.gse.games.recommended;

public class RecommendedModals {
    String url, gamename, logo;

    public RecommendedModals(String url, String gamename, String logo) {
        this.url = url;
        this.gamename = gamename;
        this.logo = logo;
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
