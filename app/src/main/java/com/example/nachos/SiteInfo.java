package com.example.nachos;

import java.util.ArrayList;

public class SiteInfo {

    private String name;
    private String url;
    private String logoRef;
    private ArrayList<String> tags;
    private ArrayList<ProductInfo> products;

    SiteInfo() {
        this.name = "";
        this.url = "";
        this.logoRef = "";
        this.tags = null;
        this.products = null;
    }

    SiteInfo(String name, String url, String logoRef, ArrayList<String> tags, ArrayList<ProductInfo> products) {
        this.name = name;
        this.url = url;
        this.logoRef = logoRef;
        this.tags = tags;
        this.products = products;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogoRef() {
        return logoRef;
    }

    public void setLogoRef(String logoRef) {
        this.logoRef = logoRef;
    }

    public ArrayList<ProductInfo> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<ProductInfo> products) {
        this.products = products;
    }
}