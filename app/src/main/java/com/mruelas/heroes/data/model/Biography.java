package com.mruelas.heroes.data.model;

import com.google.gson.annotations.SerializedName;

public class Biography {

    @SerializedName("full-name")
    String realName;
    @SerializedName("publisher")
    String publisher;
    @SerializedName("first-appearance")
    String firstAppearance;

    public String getRealName() {
        return realName== null ? "?":realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getFirstAppearance() {
        return firstAppearance;
    }

    public void setFirstAppearance(String firstAppearance) {
        this.firstAppearance = firstAppearance;
    }
}
