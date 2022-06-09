package com.example.nachos;

import com.google.firebase.storage.StorageReference;

public class Product {

    private String site;
    private String productName;
    private String introduction;
    private String image;
    private String url;
    private StorageReference ref;

    public Product(String site, String productName, String introduction, String image, String url, StorageReference ref) {
        this.site = site;
        this.productName = productName;
        this.introduction = introduction;
        this.image = image;
        this.url = url;
        this.ref = ref;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public StorageReference getRef() {
        return ref;
    }

    public void setRef(StorageReference ref) {
        this.ref = ref;
    }
}
