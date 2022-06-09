package com.example.nachos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity{

    private ApplicationState appState;
    private int top_ve, top_up, top_ft, top_do, top_ani, top_pf;
    private Button hash_up, hash_ve, hash_ft, hash_do, hash_aw, hash_pf;

    @Override
    protected void onStop() {//5
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            appState.saveScoreToFirebase();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {//6
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            appState.saveScoreToFirebase();
        }
        super.onDestroy();
    }

    private void clickCount(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            System.out.println(user.getEmail());
            appState.setScore(appState.getScore() + 1);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        appState = (ApplicationState) getApplication();

        Intent intent_ve = getIntent();
        Intent intent_an = getIntent();
        Intent intent_do = getIntent();
        Intent intent_ft = getIntent();
        Intent intent_pf = getIntent();
        Intent intent_up = getIntent();

        String fromvegan = "";
        String froman = "";
        String fromdo = "";
        String fromft = "";
        String frompf = "";
        String fromup = "";


        fromvegan = intent_ve.getStringExtra("fromvegan");
        froman = intent_an.getStringExtra("froman");
        fromdo = intent_do.getStringExtra("fromdo");
        fromft = intent_ft.getStringExtra("fromft");
        frompf = intent_pf.getStringExtra("frompf");
        fromup = intent_up.getStringExtra("fromup");

        RecyclerView recyclerView_up = findViewById(R.id.recyclerView_up);
        RecyclerView recyclerView_ve = findViewById(R.id.recyclerView_ve);
        RecyclerView recyclerView_ft = findViewById(R.id.recyclerView_ft);
        RecyclerView recyclerView_do = findViewById(R.id.recyclerView_do);
        RecyclerView recyclerView_pl = findViewById(R.id.recyclerView_pl);
        RecyclerView recyclerView_fr = findViewById(R.id.recyclerView_fr); //유기농 전 동물복지

        //up
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView_up.setLayoutManager(layoutManager);

        //recyclerView_ve.setLayoutManager(layoutManager);
        //recyclerView_ft.setLayoutManager(layoutManager);
//        recyclerView_do.setLayoutManager(layoutManager);
//        recyclerView_pl.setLayoutManager(layoutManager);
        final ProductAdapter adapter = new ProductAdapter();

        //ve
        GridLayoutManager layoutManager_ve = new GridLayoutManager(this,2);
        recyclerView_ve.setLayoutManager(layoutManager_ve);
        final ProductAdapter adapter_ve = new ProductAdapter();

        //ft
        GridLayoutManager layoutManager_ft = new GridLayoutManager(this,2);
        recyclerView_ft.setLayoutManager(layoutManager_ft);
        final ProductAdapter adapter_ft = new ProductAdapter();

        //do
        GridLayoutManager layoutManager_do = new GridLayoutManager(this,2);
        recyclerView_do.setLayoutManager(layoutManager_do);
        final ProductAdapter adapter_do = new ProductAdapter();

        //pl
        GridLayoutManager layoutManager_pl = new GridLayoutManager(this,2);
        recyclerView_pl.setLayoutManager(layoutManager_pl);
        final ProductAdapter adapter_pl = new ProductAdapter();

        //fr
        GridLayoutManager layoutManager_fr = new GridLayoutManager(this,2);
        recyclerView_fr.setLayoutManager(layoutManager_fr);
        final ProductAdapter adapter_fr = new ProductAdapter();


        ScrollView sv = (ScrollView) findViewById(R.id.scrollsite);

        LinearLayout text_ve;
        text_ve = (LinearLayout) findViewById(R.id.title_ve);
        LinearLayout text_up = findViewById(R.id.title_up);
        LinearLayout text_ani = findViewById(R.id.title_ani);
        LinearLayout text_do = findViewById(R.id.title_do);
        LinearLayout text_ft = findViewById(R.id.title_fair);
        LinearLayout text_pf = findViewById(R.id.title_pla);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                top_ve = text_ve.getTop();
                top_up = text_up.getTop();
                top_ani = text_ani.getTop();
                top_do = text_do.getTop();
                top_ft = text_ft.getTop();
                top_pf = text_pf.getTop();
                System.out.println(  ", top : " + top_ve );

            }

        }, 50);

        if (fromvegan != null){
            if (fromvegan.equals("fromvegan")){
                System.out.println("vegan!!");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sv.scrollTo(0,top_ve);
                    }
                }, 50);
            }
        }
        else if (froman != null){
            if (froman.equals("froman")){
                System.out.println("an!!");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sv.scrollTo(0,top_ani);
                    }
                }, 50);
            }
        }
        else if (fromdo != null){
            if (fromdo.equals("fromdo")){
                System.out.println("do!!");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sv.scrollTo(0,top_do);
                    }
                }, 50);
            }
        }
        else if (fromft != null){
            if (fromft.equals("fromft")){
                System.out.println("ft!!");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sv.scrollTo(0,top_ft);
                    }
                }, 50);
            }
        }
        else if (frompf != null){
            if (frompf.equals("frompf")){
                System.out.println("pf!!");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sv.scrollTo(0,top_pf);
                    }
                }, 50);
            }
        }
        else if (fromup != null){
            if (fromup.equals("fromup")){
                System.out.println("up!!");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sv.scrollTo(0,top_up);
                    }
                }, 50);
            }
        }

        Button title_back, title_prof; // 상단 타이틀
        Button home, cate, prod, stor; // 상단 탑뷰

        title_back = findViewById(R.id.btn_Back);
        title_prof = findViewById(R.id.btn_Profile);
        home = findViewById(R.id.button_home);
        cate = findViewById(R.id.buttom_cate);
        prod = findViewById(R.id.button_prod);
        stor = findViewById(R.id.button_stor);
        hash_up = findViewById(R.id.hash1);
        hash_ve = findViewById(R.id.hash2);
        hash_ft = findViewById(R.id.hash3);
        hash_do = findViewById(R.id.hash4);
        hash_aw = findViewById(R.id.hash5);
        hash_pf = findViewById(R.id.hash6);


        String baseUrl = "gs://nacho-da37d.appspot.com/";


        //up
        ArrayList<Product> items = new ArrayList<Product>();
        FirebaseStorage storage = FirebaseStorage.getInstance();


        //119레오 업
        StorageReference gsReference_119 = storage.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("119레오").getProducts().get(0).getStorageRef());
        items.add(new Product(
                appState.getMeaningOutInfo().get("119레오").getName(),
                appState.getMeaningOutInfo().get("119레오").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("119레오").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("119레오").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("119레오").getProducts().get(0).getProductUrl(),
                gsReference_119));

        StorageReference gsReference_1191 = storage.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("119레오").getProducts().get(1).getStorageRef());
        items.add(new Product(
                appState.getMeaningOutInfo().get("119레오").getName(),
                appState.getMeaningOutInfo().get("119레오").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("119레오").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("119레오").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("119레오").getProducts().get(1).getProductUrl(),
                gsReference_1191));

        //누깍 업
        StorageReference gsReference_nukak = storage.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("누깍").getProducts().get(0).getStorageRef());
        items.add(new Product(
                appState.getMeaningOutInfo().get("누깍").getName(),
                appState.getMeaningOutInfo().get("누깍").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("누깍").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("누깍").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("누깍").getProducts().get(0).getProductUrl(),
                gsReference_nukak));

        StorageReference gsReference_nukak1 = storage.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("누깍").getProducts().get(1).getStorageRef());
        items.add(new Product(
                appState.getMeaningOutInfo().get("누깍").getName(),
                appState.getMeaningOutInfo().get("누깍").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("누깍").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("누깍").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("누깍").getProducts().get(1).getProductUrl(),
                gsReference_nukak1));


        //메리우드 업
        StorageReference gsReference_merry = storage.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("메리우드").getProducts().get(0).getStorageRef());
        items.add(new Product(
                appState.getMeaningOutInfo().get("메리우드").getName(),
                appState.getMeaningOutInfo().get("메리우드").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("메리우드").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("메리우드").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("메리우드").getProducts().get(0).getProductUrl(),
                gsReference_merry));

        StorageReference gsReference_merry1 = storage.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("메리우드").getProducts().get(1).getStorageRef());
        items.add(new Product(
                appState.getMeaningOutInfo().get("메리우드").getName(),
                appState.getMeaningOutInfo().get("메리우드").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("메리우드").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("메리우드").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("메리우드").getProducts().get(1).getProductUrl(),
                gsReference_merry1));

        //밀키프로젝트 업
        StorageReference gsReference_milky = storage.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("밀키프로젝트").getProducts().get(0).getStorageRef());
        items.add(new Product(
                appState.getMeaningOutInfo().get("밀키프로젝트").getName(),
                appState.getMeaningOutInfo().get("밀키프로젝트").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("밀키프로젝트").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("밀키프로젝트").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("밀키프로젝트").getProducts().get(0).getProductUrl(),
                gsReference_milky));

        StorageReference gsReference_milky1 = storage.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("밀키프로젝트").getProducts().get(1).getStorageRef());
        items.add(new Product(
                appState.getMeaningOutInfo().get("밀키프로젝트").getName(),
                appState.getMeaningOutInfo().get("밀키프로젝트").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("밀키프로젝트").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("밀키프로젝트").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("밀키프로젝트").getProducts().get(1).getProductUrl(),
                gsReference_milky1));

        //얼킨 업
        StorageReference gsReference_ulkin = storage.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("얼킨").getProducts().get(0).getStorageRef());
        items.add(new Product(
                appState.getMeaningOutInfo().get("얼킨").getName(),
                appState.getMeaningOutInfo().get("얼킨").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("얼킨").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("얼킨").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("얼킨").getProducts().get(0).getProductUrl(),
                gsReference_ulkin));

        StorageReference gsReference_ulkin1 = storage.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("얼킨").getProducts().get(1).getStorageRef());
        items.add(new Product(
                appState.getMeaningOutInfo().get("얼킨").getName(),
                appState.getMeaningOutInfo().get("얼킨").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("얼킨").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("얼킨").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("얼킨").getProducts().get(1).getProductUrl(),
                gsReference_ulkin1));

        //에코파티메아리 업
        StorageReference gsReference_ecoparty = storage.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("에코파티메아리").getProducts().get(0).getStorageRef());
        items.add(new Product(
                appState.getMeaningOutInfo().get("에코파티메아리").getName(),
                appState.getMeaningOutInfo().get("에코파티메아리").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("에코파티메아리").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("에코파티메아리").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("에코파티메아리").getProducts().get(0).getProductUrl(),
                gsReference_ecoparty));

        StorageReference gsReference_ecoparty1 = storage.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("에코파티메아리").getProducts().get(1).getStorageRef());
        items.add(new Product(
                appState.getMeaningOutInfo().get("에코파티메아리").getName(),
                appState.getMeaningOutInfo().get("에코파티메아리").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("에코파티메아리").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("에코파티메아리").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("에코파티메아리").getProducts().get(1).getProductUrl(),
                gsReference_ecoparty1));

        //오버랩 업
        StorageReference gsReference_overlab = storage.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("오버랩").getProducts().get(0).getStorageRef());
        items.add(new Product(
                appState.getMeaningOutInfo().get("오버랩").getName(),
                appState.getMeaningOutInfo().get("오버랩").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("오버랩").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("오버랩").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("오버랩").getProducts().get(0).getProductUrl(),
                gsReference_overlab));

        StorageReference gsReference_overlab1 = storage.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("오버랩").getProducts().get(1).getStorageRef());
        items.add(new Product(
                appState.getMeaningOutInfo().get("오버랩").getName(),
                appState.getMeaningOutInfo().get("오버랩").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("오버랩").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("오버랩").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("오버랩").getProducts().get(1).getProductUrl(),
                gsReference_overlab1));

        //오운유 업
        StorageReference gsReference_ownu = storage.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("오운유").getProducts().get(0).getStorageRef());
        items.add(new Product(
                appState.getMeaningOutInfo().get("오운유").getName(),
                appState.getMeaningOutInfo().get("오운유").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("오운유").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("오운유").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("오운유").getProducts().get(0).getProductUrl(),
                gsReference_ownu));

        StorageReference gsReference_ownu1 = storage.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("오운유").getProducts().get(1).getStorageRef());
        items.add(new Product(
                appState.getMeaningOutInfo().get("오운유").getName(),
                appState.getMeaningOutInfo().get("오운유").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("오운유").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("오운유").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("오운유").getProducts().get(1).getProductUrl(),
                gsReference_ownu1));

        //컷더트래쉬 업
        StorageReference gsReference_cut = storage.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("컷더트래쉬").getProducts().get(0).getStorageRef());
        items.add(new Product(
                appState.getMeaningOutInfo().get("컷더트래쉬").getName(),
                appState.getMeaningOutInfo().get("컷더트래쉬").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("컷더트래쉬").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("컷더트래쉬").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("컷더트래쉬").getProducts().get(0).getProductUrl(),
                gsReference_cut));

        StorageReference gsReference_cut1 = storage.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("컷더트래쉬").getProducts().get(1).getStorageRef());
        items.add(new Product(
                appState.getMeaningOutInfo().get("컷더트래쉬").getName(),
                appState.getMeaningOutInfo().get("컷더트래쉬").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("컷더트래쉬").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("컷더트래쉬").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("컷더트래쉬").getProducts().get(1).getProductUrl(),
                gsReference_cut1));


        //큐클리프 업
        StorageReference gsReference_cueclyp = storage.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("큐클리프").getProducts().get(0).getStorageRef());
        items.add(new Product(
                appState.getMeaningOutInfo().get("큐클리프").getName(),
                appState.getMeaningOutInfo().get("큐클리프").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("큐클리프").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("큐클리프").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("큐클리프").getProducts().get(0).getProductUrl(),
                gsReference_cueclyp));

        StorageReference gsReference_cueclyp1 = storage.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("큐클리프").getProducts().get(1).getStorageRef());
        items.add(new Product(
                appState.getMeaningOutInfo().get("큐클리프").getName(),
                appState.getMeaningOutInfo().get("큐클리프").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("큐클리프").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("큐클리프").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("큐클리프").getProducts().get(1).getProductUrl(),
                gsReference_cueclyp1));



        //ve
        ArrayList<Product> items_ve = new ArrayList<Product>();
        FirebaseStorage storage_ve = FirebaseStorage.getInstance();

        //러빙헛 비
        StorageReference gsReference_lovinghut = storage_ve.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("러빙헛").getProducts().get(0).getStorageRef());
        items_ve.add(new Product(
                appState.getMeaningOutInfo().get("러빙헛").getName(),
                appState.getMeaningOutInfo().get("러빙헛").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("러빙헛").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("러빙헛").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("러빙헛").getProducts().get(0).getProductUrl(),
                gsReference_lovinghut));

        StorageReference gsReference_lovinghut1 = storage_ve.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("러빙헛").getProducts().get(1).getStorageRef());
        items_ve.add(new Product(
                appState.getMeaningOutInfo().get("러빙헛").getName(),
                appState.getMeaningOutInfo().get("러빙헛").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("러빙헛").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("러빙헛").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("러빙헛").getProducts().get(1).getProductUrl(),
                gsReference_lovinghut1));

        //멜릭서 비
        StorageReference gsReference_melix = storage_ve.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("멜릭서").getProducts().get(0).getStorageRef());
        items_ve.add(new Product(
                appState.getMeaningOutInfo().get("멜릭서").getName(),
                appState.getMeaningOutInfo().get("멜릭서").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("멜릭서").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("멜릭서").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("멜릭서").getProducts().get(0).getProductUrl(),
                gsReference_melix));

        StorageReference gsReference_melix1 = storage_ve.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("멜릭서").getProducts().get(1).getStorageRef());
        items_ve.add(new Product(
                appState.getMeaningOutInfo().get("멜릭서").getName(),
                appState.getMeaningOutInfo().get("멜릭서").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("멜릭서").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("멜릭서").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("멜릭서").getProducts().get(1).getProductUrl(),
                gsReference_melix1));

        //베지맘 비
        StorageReference gsReference_vegemom = storage_ve.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("베지맘").getProducts().get(0).getStorageRef());
        items_ve.add(new Product(
                appState.getMeaningOutInfo().get("베지맘").getName(),
                appState.getMeaningOutInfo().get("베지맘").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("베지맘").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("베지맘").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("베지맘").getProducts().get(0).getProductUrl(),
                gsReference_vegemom));

        StorageReference gsReference_vegemom1 = storage_ve.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("베지맘").getProducts().get(1).getStorageRef());
        items_ve.add(new Product(
                appState.getMeaningOutInfo().get("베지맘").getName(),
                appState.getMeaningOutInfo().get("베지맘").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("베지맘").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("베지맘").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("베지맘").getProducts().get(1).getProductUrl(),
                gsReference_vegemom1));

        //베지푸드 비
        StorageReference gsReference_vegefood = storage_ve.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("베지푸드").getProducts().get(0).getStorageRef());
        items_ve.add(new Product(
                appState.getMeaningOutInfo().get("베지푸드").getName(),
                appState.getMeaningOutInfo().get("베지푸드").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("베지푸드").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("베지푸드").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("베지푸드").getProducts().get(0).getProductUrl(),
                gsReference_vegefood));

        StorageReference gsReference_vegefood1 = storage_ve.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("베지푸드").getProducts().get(1).getStorageRef());
        items_ve.add(new Product(
                appState.getMeaningOutInfo().get("베지푸드").getName(),
                appState.getMeaningOutInfo().get("베지푸드").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("베지푸드").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("베지푸드").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("베지푸드").getProducts().get(1).getProductUrl(),
                gsReference_vegefood1));

        //보나쥬르 비
        StorageReference gsReference_bonajour = storage_ve.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("보나쥬르").getProducts().get(0).getStorageRef());
        items_ve.add(new Product(
                appState.getMeaningOutInfo().get("보나쥬르").getName(),
                appState.getMeaningOutInfo().get("보나쥬르").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("보나쥬르").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("보나쥬르").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("보나쥬르").getProducts().get(0).getProductUrl(),
                gsReference_bonajour));

        StorageReference gsReference_bonajour1 = storage_ve.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("보나쥬르").getProducts().get(1).getStorageRef());
        items_ve.add(new Product(
                appState.getMeaningOutInfo().get("보나쥬르").getName(),
                appState.getMeaningOutInfo().get("보나쥬르").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("보나쥬르").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("보나쥬르").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("보나쥬르").getProducts().get(1).getProductUrl(),
                gsReference_bonajour1));

        //비건스페이스 비
        StorageReference gsReference_veganspace = storage_ve.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("비건스페이스").getProducts().get(0).getStorageRef());
        items_ve.add(new Product(
                appState.getMeaningOutInfo().get("비건스페이스").getName(),
                appState.getMeaningOutInfo().get("비건스페이스").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("비건스페이스").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("비건스페이스").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("비건스페이스").getProducts().get(0).getProductUrl(),
                gsReference_veganspace));

        StorageReference gsReference_veganspace1 = storage_ve.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("비건스페이스").getProducts().get(1).getStorageRef());
        items_ve.add(new Product(
                appState.getMeaningOutInfo().get("비건스페이스").getName(),
                appState.getMeaningOutInfo().get("비건스페이스").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("비건스페이스").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("비건스페이스").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("비건스페이스").getProducts().get(1).getProductUrl(),
                gsReference_veganspace1));

        //비건푸드 비
        StorageReference gsReference_veganfood = storage_ve.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("비건푸드").getProducts().get(0).getStorageRef());
        items_ve.add(new Product(
                appState.getMeaningOutInfo().get("비건푸드").getName(),
                appState.getMeaningOutInfo().get("비건푸드").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("비건푸드").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("비건푸드").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("비건푸드").getProducts().get(0).getProductUrl(),
                gsReference_veganfood));

        StorageReference gsReference_veganfood1 = storage_ve.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("비건푸드").getProducts().get(1).getStorageRef());
        items_ve.add(new Product(
                appState.getMeaningOutInfo().get("비건푸드").getName(),
                appState.getMeaningOutInfo().get("비건푸드").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("비건푸드").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("비건푸드").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("비건푸드").getProducts().get(1).getProductUrl(),
                gsReference_veganfood1));

        //진선푸드 비
        StorageReference gsReference_jinsun = storage_ve.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("진선푸드").getProducts().get(0).getStorageRef());
        items_ve.add(new Product(
                appState.getMeaningOutInfo().get("진선푸드").getName(),
                appState.getMeaningOutInfo().get("진선푸드").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("진선푸드").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("진선푸드").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("진선푸드").getProducts().get(0).getProductUrl(),
                gsReference_jinsun));

        StorageReference gsReference_jinsun1 = storage_ve.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("진선푸드").getProducts().get(1).getStorageRef());
        items_ve.add(new Product(
                appState.getMeaningOutInfo().get("진선푸드").getName(),
                appState.getMeaningOutInfo().get("진선푸드").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("진선푸드").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("진선푸드").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("진선푸드").getProducts().get(1).getProductUrl(),
                gsReference_jinsun1));

        //채식나라 비
        StorageReference gsReference_chaesik = storage_ve.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("채식나라").getProducts().get(0).getStorageRef());
        items_ve.add(new Product(
                appState.getMeaningOutInfo().get("채식나라").getName(),
                appState.getMeaningOutInfo().get("채식나라").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("채식나라").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("채식나라").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("채식나라").getProducts().get(0).getProductUrl(),
                gsReference_chaesik));

        StorageReference gsReference_chaesik1 = storage_ve.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("채식나라").getProducts().get(1).getStorageRef());
        items_ve.add(new Product(
                appState.getMeaningOutInfo().get("채식나라").getName(),
                appState.getMeaningOutInfo().get("채식나라").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("채식나라").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("채식나라").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("채식나라").getProducts().get(1).getProductUrl(),
                gsReference_chaesik1));


        //채식한끼몰 비
        StorageReference gsReference_hanggi = storage_ve.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("채식한끼몰").getProducts().get(0).getStorageRef());
        items_ve.add(new Product(
                appState.getMeaningOutInfo().get("채식한끼몰").getName(),
                appState.getMeaningOutInfo().get("채식한끼몰").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("채식한끼몰").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("채식한끼몰").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("채식한끼몰").getProducts().get(0).getProductUrl(),
                gsReference_hanggi));

        StorageReference gsReference_hanggi1 = storage_ve.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("채식한끼몰").getProducts().get(1).getStorageRef());
        items_ve.add(new Product(
                appState.getMeaningOutInfo().get("채식한끼몰").getName(),
                appState.getMeaningOutInfo().get("채식한끼몰").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("채식한끼몰").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("채식한끼몰").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("채식한끼몰").getProducts().get(1).getProductUrl(),
                gsReference_hanggi1));


        //ft
        ArrayList<Product> items_ft = new ArrayList<Product>();
        FirebaseStorage storage_ft = FirebaseStorage.getInstance();

//        //AFN 공
//        StorageReference gsReference_AFN = storage_ft.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("AFN").getProducts().get(0).getStorageRef());
//        items_ft.add(new Product(
//                appState.getMeaningOutInfo().get("AFN").getName(),
//                appState.getMeaningOutInfo().get("AFN").getProducts().get(0).getName(),
//                appState.getMeaningOutInfo().get("AFN").getProducts().get(0).getIntroduction(),
//                appState.getMeaningOutInfo().get("AFN").getProducts().get(0).getStorageRef(),
//                appState.getMeaningOutInfo().get("AFN").getProducts().get(0).getProductUrl(),
//                gsReference_AFN));
//
//        //AFN 공
//        StorageReference gsReference_AFN1 = storage_ft.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("AFN").getProducts().get(1).getStorageRef());
//        items_ft.add(new Product(
//                appState.getMeaningOutInfo().get("AFN").getName(),
//                appState.getMeaningOutInfo().get("AFN").getProducts().get(1).getName(),
//                appState.getMeaningOutInfo().get("AFN").getProducts().get(1).getIntroduction(),
//                appState.getMeaningOutInfo().get("AFN").getProducts().get(1).getStorageRef(),
//                appState.getMeaningOutInfo().get("AFN").getProducts().get(1).getProductUrl(),
//                gsReference_AFN1));

        //FairTradeKorea 공
        StorageReference gsReference_FairTradeKorea = storage_ft.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("FairTradeKorea").getProducts().get(0).getStorageRef());
        items_ft.add(new Product(
                appState.getMeaningOutInfo().get("FairTradeKorea").getName(),
                appState.getMeaningOutInfo().get("FairTradeKorea").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("FairTradeKorea").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("FairTradeKorea").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("FairTradeKorea").getProducts().get(0).getProductUrl(),
                gsReference_FairTradeKorea));

        StorageReference gsReference_FairTradeKorea1 = storage_ft.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("FairTradeKorea").getProducts().get(1).getStorageRef());
        items_ft.add(new Product(
                appState.getMeaningOutInfo().get("FairTradeKorea").getName(),
                appState.getMeaningOutInfo().get("FairTradeKorea").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("FairTradeKorea").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("FairTradeKorea").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("FairTradeKorea").getProducts().get(1).getProductUrl(),
                gsReference_FairTradeKorea1));

        //fynbo 공
        StorageReference gsReference_fynbo = storage_ft.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("fynbo").getProducts().get(0).getStorageRef());
        items_ft.add(new Product(
                appState.getMeaningOutInfo().get("fynbo").getName(),
                appState.getMeaningOutInfo().get("fynbo").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("fynbo").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("fynbo").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("fynbo").getProducts().get(0).getProductUrl(),
                gsReference_fynbo));

        StorageReference gsReference_fynbo1 = storage_ft.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("fynbo").getProducts().get(1).getStorageRef());
        items_ft.add(new Product(
                appState.getMeaningOutInfo().get("fynbo").getName(),
                appState.getMeaningOutInfo().get("fynbo").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("fynbo").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("fynbo").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("fynbo").getProducts().get(1).getProductUrl(),
                gsReference_fynbo1));


        //공기 공
        StorageReference gsReference_gonggi = storage_ft.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("공기").getProducts().get(0).getStorageRef());
        items_ft.add(new Product(
                appState.getMeaningOutInfo().get("공기").getName(),
                appState.getMeaningOutInfo().get("공기").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("공기").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("공기").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("공기").getProducts().get(0).getProductUrl(),
                gsReference_gonggi));

        StorageReference gsReference_gonggi1 = storage_ft.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("공기").getProducts().get(1).getStorageRef());
        items_ft.add(new Product(
                appState.getMeaningOutInfo().get("공기").getName(),
                appState.getMeaningOutInfo().get("공기").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("공기").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("공기").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("공기").getProducts().get(1).getProductUrl(),
                gsReference_gonggi1));

        //아름다운커피 공
        StorageReference gsReference_beautiful = storage_ft.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("아름다운커피").getProducts().get(0).getStorageRef());
        items_ft.add(new Product(
                appState.getMeaningOutInfo().get("아름다운커피").getName(),
                appState.getMeaningOutInfo().get("아름다운커피").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("아름다운커피").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("아름다운커피").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("아름다운커피").getProducts().get(0).getProductUrl(),
                gsReference_beautiful));

        StorageReference gsReference_beautiful1 = storage_ft.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("아름다운커피").getProducts().get(1).getStorageRef());
        items_ft.add(new Product(
                appState.getMeaningOutInfo().get("아름다운커피").getName(),
                appState.getMeaningOutInfo().get("아름다운커피").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("아름다운커피").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("아름다운커피").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("아름다운커피").getProducts().get(1).getProductUrl(),
                gsReference_beautiful1));


        //어스맨 공
        StorageReference gsReference_earthman = storage_ft.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("어스맨").getProducts().get(0).getStorageRef());
        items_ft.add(new Product(
                appState.getMeaningOutInfo().get("어스맨").getName(),
                appState.getMeaningOutInfo().get("어스맨").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("어스맨").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("어스맨").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("어스맨").getProducts().get(0).getProductUrl(),
                gsReference_earthman));

        StorageReference gsReference_earthman1 = storage_ft.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("어스맨").getProducts().get(1).getStorageRef());
        items_ft.add(new Product(
                appState.getMeaningOutInfo().get("어스맨").getName(),
                appState.getMeaningOutInfo().get("어스맨").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("어스맨").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("어스맨").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("어스맨").getProducts().get(1).getProductUrl(),
                gsReference_earthman1));

        //에코몽 공
        StorageReference gsReference_ecomont = storage_ft.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("에코몽").getProducts().get(0).getStorageRef());
        items_ft.add(new Product(
                appState.getMeaningOutInfo().get("에코몽").getName(),
                appState.getMeaningOutInfo().get("에코몽").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("에코몽").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("에코몽").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("에코몽").getProducts().get(0).getProductUrl(),
                gsReference_ecomont));

        StorageReference gsReference_ecomont1 = storage_ft.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("에코몽").getProducts().get(1).getStorageRef());
        items_ft.add(new Product(
                appState.getMeaningOutInfo().get("에코몽").getName(),
                appState.getMeaningOutInfo().get("에코몽").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("에코몽").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("에코몽").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("에코몽").getProducts().get(1).getProductUrl(),
                gsReference_ecomont1));

        //옥스팜 공
        StorageReference gsReference_oxfam = storage_ft.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("옥스팜").getProducts().get(0).getStorageRef());
        items_ft.add(new Product(
                appState.getMeaningOutInfo().get("옥스팜").getName(),
                appState.getMeaningOutInfo().get("옥스팜").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("옥스팜").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("옥스팜").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("옥스팜").getProducts().get(0).getProductUrl(),
                gsReference_oxfam));

        StorageReference gsReference_oxfam1 = storage_ft.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("옥스팜").getProducts().get(1).getStorageRef());
        items_ft.add(new Product(
                appState.getMeaningOutInfo().get("옥스팜").getName(),
                appState.getMeaningOutInfo().get("옥스팜").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("옥스팜").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("옥스팜").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("옥스팜").getProducts().get(1).getProductUrl(),
                gsReference_oxfam1));

        //울림 공
        StorageReference gsReference_ullim = storage_ft.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("울림").getProducts().get(0).getStorageRef());
        items_ft.add(new Product(
                appState.getMeaningOutInfo().get("울림").getName(),
                appState.getMeaningOutInfo().get("울림").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("울림").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("울림").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("울림").getProducts().get(0).getProductUrl(),
                gsReference_ullim));

        StorageReference gsReference_ullim1 = storage_ft.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("울림").getProducts().get(1).getStorageRef());
        items_ft.add(new Product(
                appState.getMeaningOutInfo().get("울림").getName(),
                appState.getMeaningOutInfo().get("울림").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("울림").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("울림").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("울림").getProducts().get(1).getProductUrl(),
                gsReference_ullim1));

        //트립티 공
        StorageReference gsReference_tripti = storage_ft.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("트립티").getProducts().get(0).getStorageRef());
        items_ft.add(new Product(
                appState.getMeaningOutInfo().get("트립티").getName(),
                appState.getMeaningOutInfo().get("트립티").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("트립티").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("트립티").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("트립티").getProducts().get(0).getProductUrl(),
                gsReference_tripti));

        StorageReference gsReference_tripti1 = storage_ft.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("트립티").getProducts().get(1).getStorageRef());
        items_ft.add(new Product(
                appState.getMeaningOutInfo().get("트립티").getName(),
                appState.getMeaningOutInfo().get("트립티").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("트립티").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("트립티").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("트립티").getProducts().get(1).getProductUrl(),
                gsReference_tripti1));


        //do
        ArrayList<Product> items_do = new ArrayList<Product>();
        FirebaseStorage storage_do = FirebaseStorage.getInstance();

        //4OCEAN 기
        StorageReference gsReference_4OCEAN = storage_do.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("4OCEAN").getProducts().get(0).getStorageRef());
        items_do.add(new Product(
                appState.getMeaningOutInfo().get("4OCEAN").getName(),
                appState.getMeaningOutInfo().get("4OCEAN").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("4OCEAN").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("4OCEAN").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("4OCEAN").getProducts().get(0).getProductUrl(),
                gsReference_4OCEAN));

        StorageReference gsReference_4OCEAN1 = storage_do.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("4OCEAN").getProducts().get(1).getStorageRef());
        items_do.add(new Product(
                appState.getMeaningOutInfo().get("4OCEAN").getName(),
                appState.getMeaningOutInfo().get("4OCEAN").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("4OCEAN").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("4OCEAN").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("4OCEAN").getProducts().get(1).getProductUrl(),
                gsReference_4OCEAN1));


        //녹챠방 기
        StorageReference gsReference_nokcha = storage_do.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("녹챠방").getProducts().get(0).getStorageRef());
        items_do.add(new Product(
                appState.getMeaningOutInfo().get("녹챠방").getName(),
                appState.getMeaningOutInfo().get("녹챠방").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("녹챠방").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("녹챠방").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("녹챠방").getProducts().get(0).getProductUrl(),
                gsReference_nokcha));

        StorageReference gsReference_nokcha1 = storage_do.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("녹챠방").getProducts().get(1).getStorageRef());
        items_do.add(new Product(
                appState.getMeaningOutInfo().get("녹챠방").getName(),
                appState.getMeaningOutInfo().get("녹챠방").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("녹챠방").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("녹챠방").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("녹챠방").getProducts().get(1).getProductUrl(),
                gsReference_nokcha1));

        //데일리유니크 기
        StorageReference gsReference_daily = storage_do.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("데일리유니크").getProducts().get(0).getStorageRef());
        items_do.add(new Product(
                appState.getMeaningOutInfo().get("데일리유니크").getName(),
                appState.getMeaningOutInfo().get("데일리유니크").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("데일리유니크").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("데일리유니크").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("데일리유니크").getProducts().get(0).getProductUrl(),
                gsReference_daily));

        StorageReference gsReference_daily1 = storage_do.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("데일리유니크").getProducts().get(1).getStorageRef());
        items_do.add(new Product(
                appState.getMeaningOutInfo().get("데일리유니크").getName(),
                appState.getMeaningOutInfo().get("데일리유니크").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("데일리유니크").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("데일리유니크").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("데일리유니크").getProducts().get(1).getProductUrl(),
                gsReference_daily1));

        //메리디아니 기
        StorageReference gsReference_meridiani = storage_do.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("메리디아니").getProducts().get(0).getStorageRef());
        items_do.add(new Product(
                appState.getMeaningOutInfo().get("메리디아니").getName(),
                appState.getMeaningOutInfo().get("메리디아니").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("메리디아니").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("메리디아니").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("메리디아니").getProducts().get(0).getProductUrl(),
                gsReference_meridiani));

        StorageReference gsReference_meridiani1 = storage_do.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("메리디아니").getProducts().get(1).getStorageRef());
        items_do.add(new Product(
                appState.getMeaningOutInfo().get("메리디아니").getName(),
                appState.getMeaningOutInfo().get("메리디아니").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("메리디아니").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("메리디아니").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("메리디아니").getProducts().get(1).getProductUrl(),
                gsReference_meridiani1));

//        //모레상점 기
//        StorageReference gsReference_more = storage_do.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("모레상점").getProducts().get(0).getStorageRef());
//        items_do.add(new Product(
//                appState.getMeaningOutInfo().get("모레상점").getName(),
//                appState.getMeaningOutInfo().get("모레상점").getProducts().get(0).getName(),
//                appState.getMeaningOutInfo().get("모레상점").getProducts().get(0).getIntroduction(),
//                appState.getMeaningOutInfo().get("모레상점").getProducts().get(0).getStorageRef(),
//                appState.getMeaningOutInfo().get("모레상점").getProducts().get(0).getProductUrl(),
//                gsReference_more));
//
//        StorageReference gsReference_more1 = storage_do.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("모레상점").getProducts().get(1).getStorageRef());
//        items_do.add(new Product(
//                appState.getMeaningOutInfo().get("모레상점").getName(),
//                appState.getMeaningOutInfo().get("모레상점").getProducts().get(1).getName(),
//                appState.getMeaningOutInfo().get("모레상점").getProducts().get(1).getIntroduction(),
//                appState.getMeaningOutInfo().get("모레상점").getProducts().get(1).getStorageRef(),
//                appState.getMeaningOutInfo().get("모레상점").getProducts().get(1).getProductUrl(),
//                gsReference_more1));


        //비코 기
        StorageReference gsReference_bcoe = storage_do.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("비코").getProducts().get(0).getStorageRef());
        items_do.add(new Product(
                appState.getMeaningOutInfo().get("비코").getName(),
                appState.getMeaningOutInfo().get("비코").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("비코").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("비코").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("비코").getProducts().get(0).getProductUrl(),
                gsReference_bcoe));

        StorageReference gsReference_bcoe1 = storage_do.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("비코").getProducts().get(1).getStorageRef());
        items_do.add(new Product(
                appState.getMeaningOutInfo().get("비코").getName(),
                appState.getMeaningOutInfo().get("비코").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("비코").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("비코").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("비코").getProducts().get(1).getProductUrl(),
                gsReference_bcoe1));

        //비프렌드마켓 기
        StorageReference gsReference_bfirend = storage_do.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("비프렌드마켓").getProducts().get(0).getStorageRef());
        items_do.add(new Product(
                appState.getMeaningOutInfo().get("비프렌드마켓").getName(),
                appState.getMeaningOutInfo().get("비프렌드마켓").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("비프렌드마켓").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("비프렌드마켓").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("비프렌드마켓").getProducts().get(0).getProductUrl(),
                gsReference_bfirend));

        StorageReference gsReference_bfirend1 = storage_do.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("비프렌드마켓").getProducts().get(1).getStorageRef());
        items_do.add(new Product(
                appState.getMeaningOutInfo().get("비프렌드마켓").getName(),
                appState.getMeaningOutInfo().get("비프렌드마켓").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("비프렌드마켓").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("비프렌드마켓").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("비프렌드마켓").getProducts().get(1).getProductUrl(),
                gsReference_bfirend1));

        //아틀리에리케 기
        StorageReference gsReference_atelierlykke = storage_do.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("아틀리에리케").getProducts().get(0).getStorageRef());
        items_do.add(new Product(
                appState.getMeaningOutInfo().get("아틀리에리케").getName(),
                appState.getMeaningOutInfo().get("아틀리에리케").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("아틀리에리케").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("아틀리에리케").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("아틀리에리케").getProducts().get(0).getProductUrl(),
                gsReference_atelierlykke));

        StorageReference gsReference_atelierlykke1 = storage_do.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("아틀리에리케").getProducts().get(1).getStorageRef());
        items_do.add(new Product(
                appState.getMeaningOutInfo().get("아틀리에리케").getName(),
                appState.getMeaningOutInfo().get("아틀리에리케").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("아틀리에리케").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("아틀리에리케").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("아틀리에리케").getProducts().get(1).getProductUrl(),
                gsReference_atelierlykke1));

        //윙블링 기
        StorageReference gsReference_wingbling = storage_do.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("윙블링").getProducts().get(0).getStorageRef());
        items_do.add(new Product(
                appState.getMeaningOutInfo().get("윙블링").getName(),
                appState.getMeaningOutInfo().get("윙블링").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("윙블링").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("윙블링").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("윙블링").getProducts().get(0).getProductUrl(),
                gsReference_wingbling));

        StorageReference gsReference_wingbling1 = storage_do.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("윙블링").getProducts().get(1).getStorageRef());
        items_do.add(new Product(
                appState.getMeaningOutInfo().get("윙블링").getName(),
                appState.getMeaningOutInfo().get("윙블링").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("윙블링").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("윙블링").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("윙블링").getProducts().get(1).getProductUrl(),
                gsReference_wingbling1));

        //팅클유 기
        StorageReference gsReference_tingklu = storage_do.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("팅클유").getProducts().get(0).getStorageRef());
        items_do.add(new Product(
                appState.getMeaningOutInfo().get("팅클유").getName(),
                appState.getMeaningOutInfo().get("팅클유").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("팅클유").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("팅클유").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("팅클유").getProducts().get(0).getProductUrl(),
                gsReference_tingklu));

        StorageReference gsReference_tingklu1 = storage_do.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("팅클유").getProducts().get(1).getStorageRef());
        items_do.add(new Product(
                appState.getMeaningOutInfo().get("팅클유").getName(),
                appState.getMeaningOutInfo().get("팅클유").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("팅클유").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("팅클유").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("팅클유").getProducts().get(1).getProductUrl(),
                gsReference_tingklu1));

        //pl
        ArrayList<Product> items_pl = new ArrayList<Product>();
        FirebaseStorage storage_pl = FirebaseStorage.getInstance();

        //내추럴팁스 제
        StorageReference gsReference_naturaltips = storage_pl.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("내추럴팁스").getProducts().get(0).getStorageRef());
        items_pl.add(new Product(
                appState.getMeaningOutInfo().get("내추럴팁스").getName(),
                appState.getMeaningOutInfo().get("내추럴팁스").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("내추럴팁스").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("내추럴팁스").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("내추럴팁스").getProducts().get(0).getProductUrl(),
                gsReference_naturaltips));

        StorageReference gsReference_naturaltips1 = storage_pl.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("내추럴팁스").getProducts().get(1).getStorageRef());
        items_pl.add(new Product(
                appState.getMeaningOutInfo().get("내추럴팁스").getName(),
                appState.getMeaningOutInfo().get("내추럴팁스").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("내추럴팁스").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("내추럴팁스").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("내추럴팁스").getProducts().get(1).getProductUrl(),
                gsReference_naturaltips1));

        //더피커 제
        StorageReference gsReference_thepicker = storage_pl.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("더피커").getProducts().get(0).getStorageRef());
        items_pl.add(new Product(
                appState.getMeaningOutInfo().get("더피커").getName(),
                appState.getMeaningOutInfo().get("더피커").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("더피커").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("더피커").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("더피커").getProducts().get(0).getProductUrl(),
                gsReference_thepicker));

        StorageReference gsReference_thepicker1 = storage_pl.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("더피커").getProducts().get(1).getStorageRef());
        items_pl.add(new Product(
                appState.getMeaningOutInfo().get("더피커").getName(),
                appState.getMeaningOutInfo().get("더피커").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("더피커").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("더피커").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("더피커").getProducts().get(1).getProductUrl(),
                gsReference_thepicker1));

        //더험블 제
        StorageReference gsReference_thehumble = storage_pl.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("더험블").getProducts().get(0).getStorageRef());
        items_pl.add(new Product(
                appState.getMeaningOutInfo().get("더험블").getName(),
                appState.getMeaningOutInfo().get("더험블").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("더험블").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("더험블").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("더험블").getProducts().get(0).getProductUrl(),
                gsReference_thehumble));

        StorageReference gsReference_thehumble1 = storage_pl.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("더험블").getProducts().get(1).getStorageRef());
        items_pl.add(new Product(
                appState.getMeaningOutInfo().get("더험블").getName(),
                appState.getMeaningOutInfo().get("더험블").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("더험블").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("더험블").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("더험블").getProducts().get(1).getProductUrl(),
                gsReference_thehumble1));

        //덕분애 제
        StorageReference gsReference_thanksto = storage_pl.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("덕분애").getProducts().get(0).getStorageRef());
        items_pl.add(new Product(
                appState.getMeaningOutInfo().get("덕분애").getName(),
                appState.getMeaningOutInfo().get("덕분애").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("덕분애").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("덕분애").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("덕분애").getProducts().get(0).getProductUrl(),
                gsReference_thanksto));

        StorageReference gsReference_thanksto1 = storage_pl.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("덕분애").getProducts().get(1).getStorageRef());
        items_pl.add(new Product(
                appState.getMeaningOutInfo().get("덕분애").getName(),
                appState.getMeaningOutInfo().get("덕분애").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("덕분애").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("덕분애").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("덕분애").getProducts().get(1).getProductUrl(),
                gsReference_thanksto1));

        //루나컵 제
        StorageReference gsReference_lunacup = storage_pl.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("루나컵").getProducts().get(0).getStorageRef());
        items_pl.add(new Product(
                appState.getMeaningOutInfo().get("루나컵").getName(),
                appState.getMeaningOutInfo().get("루나컵").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("루나컵").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("루나컵").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("루나컵").getProducts().get(0).getProductUrl(),
                gsReference_lunacup));

        StorageReference gsReference_lunacup1 = storage_pl.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("루나컵").getProducts().get(1).getStorageRef());
        items_pl.add(new Product(
                appState.getMeaningOutInfo().get("루나컵").getName(),
                appState.getMeaningOutInfo().get("루나컵").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("루나컵").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("루나컵").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("루나컵").getProducts().get(1).getProductUrl(),
                gsReference_lunacup1));

        //쎄비보타닉 제
        StorageReference gsReference_savvy = storage_pl.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("쎄비보타닉").getProducts().get(0).getStorageRef());
        items_pl.add(new Product(
                appState.getMeaningOutInfo().get("쎄비보타닉").getName(),
                appState.getMeaningOutInfo().get("쎄비보타닉").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("쎄비보타닉").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("쎄비보타닉").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("쎄비보타닉").getProducts().get(0).getProductUrl(),
                gsReference_savvy));

        StorageReference gsReference_savvy1 = storage_pl.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("쎄비보타닉").getProducts().get(1).getStorageRef());
        items_pl.add(new Product(
                appState.getMeaningOutInfo().get("쎄비보타닉").getName(),
                appState.getMeaningOutInfo().get("쎄비보타닉").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("쎄비보타닉").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("쎄비보타닉").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("쎄비보타닉").getProducts().get(1).getProductUrl(),
                gsReference_savvy1));

        //아로마티카 제 aroma
        StorageReference gsReference_aroma = storage_pl.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("아로마티카").getProducts().get(0).getStorageRef());
        items_pl.add(new Product(
                appState.getMeaningOutInfo().get("아로마티카").getName(),
                appState.getMeaningOutInfo().get("아로마티카").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("아로마티카").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("아로마티카").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("아로마티카").getProducts().get(0).getProductUrl(),
                gsReference_aroma));

        StorageReference gsReference_aroma1 = storage_pl.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("아로마티카").getProducts().get(1).getStorageRef());
        items_pl.add(new Product(
                appState.getMeaningOutInfo().get("아로마티카").getName(),
                appState.getMeaningOutInfo().get("아로마티카").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("아로마티카").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("아로마티카").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("아로마티카").getProducts().get(1).getProductUrl(),
                gsReference_aroma1));

        //아이엠그리너 제 iamgreen
        StorageReference gsReference_iamgreen = storage_pl.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("아이엠그리너").getProducts().get(0).getStorageRef());
        items_pl.add(new Product(
                appState.getMeaningOutInfo().get("아이엠그리너").getName(),
                appState.getMeaningOutInfo().get("아이엠그리너").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("아이엠그리너").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("아이엠그리너").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("아이엠그리너").getProducts().get(0).getProductUrl(),
                gsReference_iamgreen));

        StorageReference gsReference_iamgreen1 = storage_pl.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("아이엠그리너").getProducts().get(1).getStorageRef());
        items_pl.add(new Product(
                appState.getMeaningOutInfo().get("아이엠그리너").getName(),
                appState.getMeaningOutInfo().get("아이엠그리너").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("아이엠그리너").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("아이엠그리너").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("아이엠그리너").getProducts().get(1).getProductUrl(),
                gsReference_iamgreen1));

        //오랜 제 oren
        StorageReference gsReference_oren = storage_pl.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("오랜").getProducts().get(0).getStorageRef());
        items_pl.add(new Product(
                appState.getMeaningOutInfo().get("오랜").getName(),
                appState.getMeaningOutInfo().get("오랜").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("오랜").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("오랜").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("오랜").getProducts().get(0).getProductUrl(),
                gsReference_oren));

        StorageReference gsReference_oren1 = storage_pl.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("오랜").getProducts().get(1).getStorageRef());
        items_pl.add(new Product(
                appState.getMeaningOutInfo().get("오랜").getName(),
                appState.getMeaningOutInfo().get("오랜").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("오랜").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("오랜").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("오랜").getProducts().get(1).getProductUrl(),
                gsReference_oren1));


        //플라스틱제로 제 plasticzero
        StorageReference gsReference_plasticzero = storage_pl.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("플라스틱제로").getProducts().get(0).getStorageRef());
        items_pl.add(new Product(
                appState.getMeaningOutInfo().get("플라스틱제로").getName(),
                appState.getMeaningOutInfo().get("플라스틱제로").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("플라스틱제로").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("플라스틱제로").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("플라스틱제로").getProducts().get(0).getProductUrl(),
                gsReference_plasticzero));

        StorageReference gsReference_plasticzero1 = storage_pl.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("플라스틱제로").getProducts().get(1).getStorageRef());
        items_pl.add(new Product(
                appState.getMeaningOutInfo().get("플라스틱제로").getName(),
                appState.getMeaningOutInfo().get("플라스틱제로").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("플라스틱제로").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("플라스틱제로").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("플라스틱제로").getProducts().get(1).getProductUrl(),
                gsReference_plasticzero1));

        //fr 유기농 전 동물복지
        ArrayList<Product> items_fr = new ArrayList<Product>();
        FirebaseStorage storage_fr = FirebaseStorage.getInstance();

        //굿백 유 goodbag
        StorageReference gsReference_goodbag = storage_fr.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("굿백").getProducts().get(0).getStorageRef());
        items_fr.add(new Product(
                appState.getMeaningOutInfo().get("굿백").getName(),
                appState.getMeaningOutInfo().get("굿백").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("굿백").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("굿백").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("굿백").getProducts().get(0).getProductUrl(),
                gsReference_goodbag));

        StorageReference gsReference_goodbag1 = storage_fr.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("굿백").getProducts().get(1).getStorageRef());
        items_fr.add(new Product(
                appState.getMeaningOutInfo().get("굿백").getName(),
                appState.getMeaningOutInfo().get("굿백").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("굿백").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("굿백").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("굿백").getProducts().get(1).getProductUrl(),
                gsReference_goodbag1));


        //녹색장터 유 greenproduct
        StorageReference gsReference_greenproduct = storage_fr.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("녹색장터").getProducts().get(0).getStorageRef());
        items_fr.add(new Product(
                appState.getMeaningOutInfo().get("녹색장터").getName(),
                appState.getMeaningOutInfo().get("녹색장터").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("녹색장터").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("녹색장터").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("녹색장터").getProducts().get(0).getProductUrl(),
                gsReference_greenproduct));

        StorageReference gsReference_greenproduct1 = storage_fr.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("녹색장터").getProducts().get(1).getStorageRef());
        items_fr.add(new Product(
                appState.getMeaningOutInfo().get("녹색장터").getName(),
                appState.getMeaningOutInfo().get("녹색장터").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("녹색장터").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("녹색장터").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("녹색장터").getProducts().get(1).getProductUrl(),
                gsReference_greenproduct1));

        //더클레어 유 theklair
        StorageReference gsReference_theklair = storage_fr.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("더클레어").getProducts().get(0).getStorageRef());
        items_fr.add(new Product(
                appState.getMeaningOutInfo().get("더클레어").getName(),
                appState.getMeaningOutInfo().get("더클레어").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("더클레어").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("더클레어").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("더클레어").getProducts().get(0).getProductUrl(),
                gsReference_theklair));

        StorageReference gsReference_theklair1 = storage_fr.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("더클레어").getProducts().get(1).getStorageRef());
        items_fr.add(new Product(
                appState.getMeaningOutInfo().get("더클레어").getName(),
                appState.getMeaningOutInfo().get("더클레어").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("더클레어").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("더클레어").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("더클레어").getProducts().get(1).getProductUrl(),
                gsReference_theklair1));

        //마이아일랜드 유 myisland
        StorageReference gsReference_myisland = storage_fr.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("마이아일랜드").getProducts().get(0).getStorageRef());
        items_fr.add(new Product(
                appState.getMeaningOutInfo().get("마이아일랜드").getName(),
                appState.getMeaningOutInfo().get("마이아일랜드").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("마이아일랜드").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("마이아일랜드").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("마이아일랜드").getProducts().get(0).getProductUrl(),
                gsReference_myisland));

        StorageReference gsReference_myisland1 = storage_fr.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("마이아일랜드").getProducts().get(1).getStorageRef());
        items_fr.add(new Product(
                appState.getMeaningOutInfo().get("마이아일랜드").getName(),
                appState.getMeaningOutInfo().get("마이아일랜드").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("마이아일랜드").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("마이아일랜드").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("마이아일랜드").getProducts().get(1).getProductUrl(),
                gsReference_myisland1));

        //모레상점 유 more
        StorageReference gsReference_more= storage_fr.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("모레상점").getProducts().get(0).getStorageRef());
        items_fr.add(new Product(
                appState.getMeaningOutInfo().get("모레상점").getName(),
                appState.getMeaningOutInfo().get("모레상점").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("모레상점").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("모레상점").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("모레상점").getProducts().get(0).getProductUrl(),
                gsReference_more));

        StorageReference gsReference_more1= storage_fr.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("모레상점").getProducts().get(1).getStorageRef());
        items_fr.add(new Product(
                appState.getMeaningOutInfo().get("모레상점").getName(),
                appState.getMeaningOutInfo().get("모레상점").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("모레상점").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("모레상점").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("모레상점").getProducts().get(1).getProductUrl(),
                gsReference_more1));

        //슈가버블 유 sugarbubble
        StorageReference gsReference_sugarbubble= storage_fr.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("슈가버블").getProducts().get(0).getStorageRef());
        items_fr.add(new Product(
                appState.getMeaningOutInfo().get("슈가버블").getName(),
                appState.getMeaningOutInfo().get("슈가버블").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("슈가버블").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("슈가버블").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("슈가버블").getProducts().get(0).getProductUrl(),
                gsReference_sugarbubble));

        StorageReference gsReference_sugarbubble1= storage_fr.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("슈가버블").getProducts().get(1).getStorageRef());
        items_fr.add(new Product(
                appState.getMeaningOutInfo().get("슈가버블").getName(),
                appState.getMeaningOutInfo().get("슈가버블").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("슈가버블").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("슈가버블").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("슈가버블").getProducts().get(1).getProductUrl(),
                gsReference_sugarbubble1));

        //아렌시아 유 arencia
        StorageReference gsReference_arencia= storage_fr.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("아렌시아").getProducts().get(0).getStorageRef());
        items_fr.add(new Product(
                appState.getMeaningOutInfo().get("아렌시아").getName(),
                appState.getMeaningOutInfo().get("아렌시아").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("아렌시아").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("아렌시아").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("아렌시아").getProducts().get(0).getProductUrl(),
                gsReference_arencia));

        StorageReference gsReference_arencia1= storage_fr.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("아렌시아").getProducts().get(1).getStorageRef());
        items_fr.add(new Product(
                appState.getMeaningOutInfo().get("아렌시아").getName(),
                appState.getMeaningOutInfo().get("아렌시아").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("아렌시아").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("아렌시아").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("아렌시아").getProducts().get(1).getProductUrl(),
                gsReference_arencia1));

        //자연상점 유 onlyeco
        StorageReference gsReference_onlyeco = storage_fr.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("자연상점").getProducts().get(0).getStorageRef());
        items_fr.add(new Product(
                appState.getMeaningOutInfo().get("자연상점").getName(),
                appState.getMeaningOutInfo().get("자연상점").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("자연상점").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("자연상점").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("자연상점").getProducts().get(0).getProductUrl(),
                gsReference_onlyeco));

        StorageReference gsReference_onlyeco1 = storage_fr.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("자연상점").getProducts().get(1).getStorageRef());
        items_fr.add(new Product(
                appState.getMeaningOutInfo().get("자연상점").getName(),
                appState.getMeaningOutInfo().get("자연상점").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("자연상점").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("자연상점").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("자연상점").getProducts().get(1).getProductUrl(),
                gsReference_onlyeco1));

        //톤28 유 tone28
        StorageReference gsReference_tone28 = storage_fr.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("톤28").getProducts().get(0).getStorageRef());
        items_fr.add(new Product(
                appState.getMeaningOutInfo().get("톤28").getName(),
                appState.getMeaningOutInfo().get("톤28").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("톤28").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("톤28").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("톤28").getProducts().get(0).getProductUrl(),
                gsReference_tone28));

        StorageReference gsReference_tone281 = storage_fr.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("톤28").getProducts().get(1).getStorageRef());
        items_fr.add(new Product(
                appState.getMeaningOutInfo().get("톤28").getName(),
                appState.getMeaningOutInfo().get("톤28").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("톤28").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("톤28").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("톤28").getProducts().get(1).getProductUrl(),
                gsReference_tone281));

        //헬로네이처 유 hellonature
        StorageReference gsReference_hellonature = storage_fr.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("헬로네이처").getProducts().get(0).getStorageRef());
        items_fr.add(new Product(
                appState.getMeaningOutInfo().get("헬로네이처").getName(),
                appState.getMeaningOutInfo().get("헬로네이처").getProducts().get(0).getName(),
                appState.getMeaningOutInfo().get("헬로네이처").getProducts().get(0).getIntroduction(),
                appState.getMeaningOutInfo().get("헬로네이처").getProducts().get(0).getStorageRef(),
                appState.getMeaningOutInfo().get("헬로네이처").getProducts().get(0).getProductUrl(),
                gsReference_hellonature));

        StorageReference gsReference_hellonature1 = storage_fr.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("헬로네이처").getProducts().get(1).getStorageRef());
        items_fr.add(new Product(
                appState.getMeaningOutInfo().get("헬로네이처").getName(),
                appState.getMeaningOutInfo().get("헬로네이처").getProducts().get(1).getName(),
                appState.getMeaningOutInfo().get("헬로네이처").getProducts().get(1).getIntroduction(),
                appState.getMeaningOutInfo().get("헬로네이처").getProducts().get(1).getStorageRef(),
                appState.getMeaningOutInfo().get("헬로네이처").getProducts().get(1).getProductUrl(),
                gsReference_hellonature1));


        recyclerView_up.setAdapter(adapter);
        adapter.setItems(items);

        adapter.setOnItemClickListener(new OnProductItemClickListener() {
            @Override
            public void onItemClick(ProductAdapter.ViewHolder holder, View view, int position) {
                clickCount();
                Product item = adapter.getItem(position);
                //Toast.makeText(getApplicationContext(), "이름 : " + item.getName() + "\n 가격 : " + item.getCost() +"\n 설명 : " + item.getNotification(),Toast.LENGTH_LONG).show();
            }
        });

        recyclerView_ve.setAdapter(adapter_ve);
        adapter_ve.setItems(items_ve);
        adapter_ve.setOnItemClickListener(new OnProductItemClickListener() {
            @Override
            public void onItemClick(ProductAdapter.ViewHolder holder, View view, int position) {
                clickCount();
                Product item = adapter_ve.getItem(position);
                //Toast.makeText(getApplicationContext(), "이름 : " + item.getName() + "\n 가격 : " + item.getCost() +"\n 설명 : " + item.getNotification(),Toast.LENGTH_LONG).show();
            }
        });

        recyclerView_ft.setAdapter(adapter_ft);
        adapter_ft.setItems(items_ft);
        adapter_ft.setOnItemClickListener(new OnProductItemClickListener() {
            @Override
            public void onItemClick(ProductAdapter.ViewHolder holder, View view, int position) {
                clickCount();
                Product item = adapter_ft.getItem(position);
                //Toast.makeText(getApplicationContext(), "이름 : " + item.getName() + "\n 가격 : " + item.getCost() +"\n 설명 : " + item.getNotification(),Toast.LENGTH_LONG).show();
            }
        });

        recyclerView_do.setAdapter(adapter_do);
        adapter_do.setItems(items_do);
        adapter_do.setOnItemClickListener(new OnProductItemClickListener() {
            @Override
            public void onItemClick(ProductAdapter.ViewHolder holder, View view, int position) {
                clickCount();
                Product item = adapter_do.getItem(position);
                //Toast.makeText(getApplicationContext(), "이름 : " + item.getName() + "\n 가격 : " + item.getCost() +"\n 설명 : " + item.getNotification(),Toast.LENGTH_LONG).show();
            }
        });

        recyclerView_pl.setAdapter(adapter_pl);
        adapter_pl.setItems(items_pl);
        adapter_pl.setOnItemClickListener(new OnProductItemClickListener() {
            @Override
            public void onItemClick(ProductAdapter.ViewHolder holder, View view, int position) {
                clickCount();
                Product item = adapter_pl.getItem(position);
                //Toast.makeText(getApplicationContext(), "이름 : " + item.getName() + "\n 가격 : " + item.getCost() +"\n 설명 : " + item.getNotification(),Toast.LENGTH_LONG).show();
            }
        });

        recyclerView_fr.setAdapter(adapter_fr);
        adapter_fr.setItems(items_fr);
        adapter_fr.setOnItemClickListener(new OnProductItemClickListener() {
            @Override
            public void onItemClick(ProductAdapter.ViewHolder holder, View view, int position) {
                clickCount();
                Product item = adapter_fr.getItem(position);
                //Toast.makeText(getApplicationContext(), "이름 : " + item.getName() + "\n 가격 : " + item.getCost() +"\n 설명 : " + item.getNotification(),Toast.LENGTH_LONG).show();
            }
        });


        // 상단 타이틀 버튼 클릭 시 이벤트
        title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount();
                onBackPressed();
                overridePendingTransition(0, 0);
            }
        });

        title_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null){
                    // User is signed in
                    System.out.println(user.getEmail());
                    appState.setScore(appState.getScore() + 1);
                    System.out.println(appState.getScore());
                    Intent intent = new Intent(ProductActivity.this, MypageActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }else{
                    // No user is signed in
                    Intent intent = new Intent(ProductActivity.this, AboutGoogleLogin.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
            }
        });//HashTag 키워드 클릭 시 이벤트
        hash_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sv.scrollTo(0,top_up);
                    }
                }, 50);
            }
        });

        hash_ve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sv.scrollTo(0,top_ve);
                    }
                }, 50);
            }
        });

        hash_ft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sv.scrollTo(0,top_ft);
                    }
                }, 50);
            }
        });

        hash_do.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sv.scrollTo(0,top_do);
                    }
                }, 50);
            }
        });

        hash_aw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sv.scrollTo(0,top_ani);
                    }
                }, 50);
            }
        });

        hash_pf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sv.scrollTo(0,top_pf);
                    }
                }, 50);
            }
        });
        // 상단 탑뷰 클릭 시 이벤트
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount();
                Intent intent = new Intent(ProductActivity.this, HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        cate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount();
                Intent intent = new Intent(ProductActivity.this, CategoryActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        prod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount();
                Intent intent = new Intent(ProductActivity.this, ProductActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        stor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount();
                Intent intent = new Intent(ProductActivity.this, SiteActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }

}
