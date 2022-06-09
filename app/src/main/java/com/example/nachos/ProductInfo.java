package com.example.nachos;

public class ProductInfo {
    private String storageRef;
    private String productUrl;
    private String name;
    private String introduction;

    public String getStorageRef() {
        return storageRef;
    }

    public void setStorageRef(String storageRef) {
        this.storageRef = storageRef;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public ProductInfo(String storageRef, String productUrl, String name, String introduction) {
        this.storageRef = storageRef;
        this.productUrl = productUrl;
        this.name = name;
        this.introduction = introduction;
    }
}