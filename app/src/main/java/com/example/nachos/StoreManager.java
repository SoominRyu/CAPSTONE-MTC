package com.example.nachos;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StoreManager {
    private FirebaseFirestore db;
    private String info;
    private ArrayList<SiteInfo> infoList = new ArrayList<SiteInfo>();
    private String[] options = {"Donation", "FairTrading", "Vegan", "Upcycling", "PlasticFree", "EcoFriendly"};
    private HashMap<String, SiteInfo> meaningOutInfo;

    // for load JSON
    public StoreManager(String data){
        db = FirebaseFirestore.getInstance();
        this.info = data;
        jsonParsing(info);
        meaningOutInfo = new HashMap<>();
        initMeaningOutInfo();
    }

    // for use Global Variable
    public StoreManager(){
        db = FirebaseFirestore.getInstance();
        jsonParsing(info);
    }

    public void init(){
        jsonParsing(info);
        setDataToFirestore();
        // initMeaningOutInfo();
    }

    public HashMap<String, SiteInfo> getMeaningOutInfo(){
        return meaningOutInfo;
    }

    public void initMeaningOutInfo(){
        for(int i = 0; i < infoList.size(); i++){
            meaningOutInfo.put(infoList.get(i).getName(), infoList.get(i));
        }
    }

    private void setDataToFirestore(){
        for(int i = 0; i < infoList.size(); i++){
            Map<String, Object> docData = new HashMap<>();
            docData.put("name", infoList.get(i).getName());
            docData.put("url", infoList.get(i).getUrl());
            docData.put("logoRef", infoList.get(i).getLogoRef());
            docData.put("tags", infoList.get(i).getTags().get(0));

//          db.collection("encryptionVer")
//                        .document(infoList.get(i).getTags().get(j))
//                        .collection(infoList.get(i).getName())
//                        .add(docData);

            db.collection("finalVer")
                    .document(infoList.get(i).getTags().get(0))
                    .collection(infoList.get(i).getName())
                    .document(infoList.get(i).getName()) // document name 지정시 특징 keyword로 접근 가능, Or 키워드 암호화 형태로
                    .set(docData);

            for(int j = 0; j < infoList.get(i).getProducts().size(); j++){
                Map<String, Object> productsData = new HashMap<>();
                productsData.put("storageRef", infoList.get(i).getProducts().get(j).getStorageRef());
                productsData.put("productUrl", infoList.get(i).getProducts().get(j).getProductUrl());
                productsData.put("name", infoList.get(i).getProducts().get(j).getName());
                productsData.put("introduction", infoList.get(i).getProducts().get(j).getIntroduction());

                db.collection("finalVer")
                        .document(infoList.get(i).getTags().get(0))
                        .collection(infoList.get(i).getName())
                        .document(infoList.get(i).getName()) // document name 지정시 특징 keyword로 접근 가능, Or 키워드 암호화 형태로
                        .collection("products")
                        .document(infoList.get(i).getProducts().get(j).getName())
                        .set(productsData);
            }
        }
    }

    private void jsonParsing(String json)
    {
        try{
            JSONObject jsonObject = new JSONObject(json);
            for(int i = 0; i < options.length; i++) {
                JSONArray infoArray = jsonObject.getJSONArray(options[i]);
                for(int j=0; j < infoArray.length(); j++) {
                    JSONObject infoObject = infoArray.getJSONObject(j);
                    ArrayList<String> tags = new ArrayList<String>();
                    for(int k = 0; k < infoObject.getJSONArray("tags").length(); k++){
                        tags.add(infoObject.getJSONArray("tags").get(k).toString());
                    }
                    ArrayList<ProductInfo> products = new ArrayList<>();
                    for(int k = 0; k < infoObject.getJSONArray("products").length(); k++){
                        String storageRef = infoObject.getJSONArray("products")
                                .getJSONObject(k).get("storageRef").toString();
                        String productUrl = infoObject.getJSONArray("products")
                                .getJSONObject(k).get("productUrl").toString();
                        String name = infoObject.getJSONArray("products")
                                .getJSONObject(k).get("name").toString();
                        String introduction = infoObject.getJSONArray("products")
                                .getJSONObject(k).get("introduction").toString();

                        ProductInfo product = new ProductInfo(
                                storageRef + ".png", productUrl, name, introduction);

                        products.add(product);
                    }

                    SiteInfo info = new SiteInfo(
                            infoObject.getString("name"),
                            infoObject.getString("url"),
                            infoObject.getString("logoRef") + ".png",
                            tags,
                            products);

                    infoList.add(info);
                }
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getData(){
        for(int i = 0; i < infoList.size(); i++){
            Log.e("info : ", infoList.get(i).getName());
            Log.e("info : ", infoList.get(i).getUrl());
            Log.e("info : ", infoList.get(i).getTags().get(0));
        }
    }
}