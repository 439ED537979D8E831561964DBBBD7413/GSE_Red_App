package com.gsered.gse.topslider;

import android.content.Intent;

public class TopSliderModal {
    String category;
    int image;
    Intent url;

    public TopSliderModal(String category, int image, Intent url) {
        this.category = category;
        this.image = image;
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Intent getUrl() {
        return url;
    }

    public void setUrl(Intent url) {
        this.url = url;
    }
}
