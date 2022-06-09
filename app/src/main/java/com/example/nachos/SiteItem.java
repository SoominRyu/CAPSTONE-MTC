package com.example.nachos;


import android.net.Uri;
import android.view.View;

import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.geo.type.Viewport;


public class SiteItem {


    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://nacho-da37d-default-rtdb.asia-southeast1.firebasedatabase.app");

    String site;
    String category;
    String logo;
    String url;
    int resId;
    View.OnClickListener gotosite;
    StorageReference ref;
    private WebView mWebView;

    //FirebaseStorage storage = FirebaseStorage.getInstance();
    //private ApplicationState appState;
    //StorageReference storageRef = storage.getReference(appState.getMeaningOutInfo().get("119레오").getDownloadUrl());



    //FirebaseStorage storage = FirebaseStorage.getInstance();
    //private ApplicationState appState;
    //StorageReference storageRef = storage.getReference(appState.getMeaningOutInfo().get("119레오").getDownloadUrl());

    //생성
    /*
    public SiteItem(String site, String category, int resId) {
        this.site = site;
        this.category = category;
        this.resId = resId;
    }*/

    public SiteItem(String site, String category, String logo, String url, StorageReference ref) {
        this.site = site;
        this.category = category;
        this.logo = logo;
        this.url = url;
        this.ref = ref;
    }

    //변수에 접근할 때 .OO 접근하기보다는 안전하게 getter, setter를 이용합니다.
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

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getResId() {
        return resId;
    }

    public String setLogo() {
        return logo;
    }

    public void getLogo(String logo) {
        this.logo = logo;
    }


    public View.OnClickListener getGotosite() {
        return gotosite;
    }

    public void setGotosite(View.OnClickListener gotosite) {
        this.gotosite = gotosite;
    }

    @Override
    public String toString() {
        return "SiteItem{" +
                "site='" + site + '\'' +
                ", category='" + category + '\'' +
                ", url='" + gotosite + '\''+
                '}';
    }

}