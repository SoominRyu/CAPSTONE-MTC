package com.example.nachos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Button;
import android.widget.ScrollView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class SiteActivity extends AppCompatActivity {

    private ApplicationState appState;
    private WebView webView;
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


//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        // 뒤로가기 키를 누를 때 & 웹뷰가 뒤로 갈 수 있는 상태일 때 뒤로 가라는 명령
//        if((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
//            webView.goBack();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site);
        appState = (ApplicationState) getApplication();
        webView = findViewById(R.id.webView);

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


        System.out.println("titleve"+R.id.title_ve);
        System.out.println("text7"+R.id.text7);

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


        ListView listView_ani = findViewById(R.id.SiteList_ani); // 친환경
        ListView listView_do = findViewById(R.id.SiteList_do); // 기부, 후원 (donation)
        ListView listView_fair = findViewById(R.id.SiteList_fair); // 공정무역
        ListView listView_pla = findViewById(R.id.SiteList_pla); // 플라스틱 프리
        ListView listView_up = findViewById(R.id.SiteList_up); // 업사이클링
        ListView listView_ve = findViewById(R.id.SiteList_ve); // 비건

        // 제품 추가
        appState = (ApplicationState) getApplication();
        String baseUrl = "gs://nacho-da37d.appspot.com/";
        FirebaseStorage storage_root = FirebaseStorage.getInstance();

        // ---------------------------------------------------------------------------------------
        // 친환경
        // ---------------------------------------------------------------------------------------
        SiteAdapter adapter_ani = new SiteAdapter();

        // 굿백
        StorageReference gsReference_goodbag = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("굿백").getLogoRef());
        adapter_ani.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("굿백").getName(),
                appState.getMeaningOutInfo().get("굿백").getTags().get(0),
                appState.getMeaningOutInfo().get("굿백").getLogoRef(),
                appState.getMeaningOutInfo().get("굿백").getUrl(),
                gsReference_goodbag));

        // 녹색장터
        StorageReference gsReference_greenproduct = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("녹색장터").getLogoRef());
        adapter_ani.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("녹색장터").getName(),
                appState.getMeaningOutInfo().get("녹색장터").getTags().get(0),
                appState.getMeaningOutInfo().get("녹색장터").getLogoRef(),
                appState.getMeaningOutInfo().get("녹색장터").getUrl(),
                gsReference_greenproduct));

        // 더클레어
        StorageReference gsReference_theklair = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("더클레어").getLogoRef());
        adapter_ani.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("더클레어").getName(),
                appState.getMeaningOutInfo().get("더클레어").getTags().get(0),
                appState.getMeaningOutInfo().get("더클레어").getLogoRef(),
                appState.getMeaningOutInfo().get("더클레어").getUrl(),
                gsReference_theklair));

        // 마이아일랜드
        StorageReference gsReference_myisland = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("마이아일랜드").getLogoRef());
        adapter_ani.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("마이아일랜드").getName(),
                appState.getMeaningOutInfo().get("마이아일랜드").getTags().get(0),
                appState.getMeaningOutInfo().get("마이아일랜드").getLogoRef(),
                appState.getMeaningOutInfo().get("마이아일랜드").getUrl(),
                gsReference_myisland));

//        // 모레상점
//        StorageReference gsReference_morestore = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("모레상점").getLogoRef());
//        adapter_ani.addItem(new SiteItem(
//                appState.getMeaningOutInfo().get("모레상점").getName(),
//                appState.getMeaningOutInfo().get("모레상점").getTags().get(0),
//                appState.getMeaningOutInfo().get("모레상점").getLogoRef(),
//                appState.getMeaningOutInfo().get("모레상점").getUrl(),
//                gsReference_morestore));

        // 슈가버블
        StorageReference gsReference_sugarbubble = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("슈가버블").getLogoRef());
        adapter_ani.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("슈가버블").getName(),
                appState.getMeaningOutInfo().get("슈가버블").getTags().get(0),
                appState.getMeaningOutInfo().get("슈가버블").getLogoRef(),
                appState.getMeaningOutInfo().get("슈가버블").getUrl(),
                gsReference_sugarbubble));

        // 아렌시아
        StorageReference gsReference_arencia = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("아렌시아").getLogoRef());
        adapter_ani.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("아렌시아").getName(),
                appState.getMeaningOutInfo().get("아렌시아").getTags().get(0),
                appState.getMeaningOutInfo().get("아렌시아").getLogoRef(),
                appState.getMeaningOutInfo().get("아렌시아").getUrl(),
                gsReference_arencia));

        // 자연상점
        StorageReference gsReference_onlyeco = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("자연상점").getLogoRef());
        adapter_ani.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("자연상점").getName(),
                appState.getMeaningOutInfo().get("자연상점").getTags().get(0),
                appState.getMeaningOutInfo().get("자연상점").getLogoRef(),
                appState.getMeaningOutInfo().get("자연상점").getUrl(),
                gsReference_onlyeco));

        // 톤28
        StorageReference gsReference_toun28 = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("톤28").getLogoRef());
        adapter_ani.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("톤28").getName(),
                appState.getMeaningOutInfo().get("톤28").getTags().get(0),
                appState.getMeaningOutInfo().get("톤28").getLogoRef(),
                appState.getMeaningOutInfo().get("톤28").getUrl(),
                gsReference_toun28));

        // 헬로네이처
        StorageReference gsReference_hellonature = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("헬로네이처").getLogoRef());
        adapter_ani.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("헬로네이처").getName(),
                appState.getMeaningOutInfo().get("헬로네이처").getTags().get(0),
                appState.getMeaningOutInfo().get("헬로네이처").getLogoRef(),
                appState.getMeaningOutInfo().get("헬로네이처").getUrl(),
                gsReference_hellonature));




        // ---------------------------------------------------------------------------------------
        // 기부, 후원
        // ---------------------------------------------------------------------------------------
        SiteAdapter adapter_do = new SiteAdapter();

        // 4OCEAN
        StorageReference gsReference_4OCEAN = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("4OCEAN").getLogoRef());
        adapter_do.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("4OCEAN").getName(),
                appState.getMeaningOutInfo().get("4OCEAN").getTags().get(0),
                appState.getMeaningOutInfo().get("4OCEAN").getLogoRef(),
                appState.getMeaningOutInfo().get("4OCEAN").getUrl(),
                gsReference_4OCEAN));

        // 녹챠방
        StorageReference gsReference_nokchabang = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("녹챠방").getLogoRef());
        adapter_do.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("녹챠방").getName(),
                appState.getMeaningOutInfo().get("녹챠방").getTags().get(0),
                appState.getMeaningOutInfo().get("녹챠방").getLogoRef(),
                appState.getMeaningOutInfo().get("녹챠방").getUrl(),
                gsReference_nokchabang));

        // 데일리유니크
        StorageReference gsReference_dailyUnique = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("데일리유니크").getLogoRef());
        adapter_do.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("데일리유니크").getName(),
                appState.getMeaningOutInfo().get("데일리유니크").getTags().get(0),
                appState.getMeaningOutInfo().get("데일리유니크").getLogoRef(),
                appState.getMeaningOutInfo().get("데일리유니크").getUrl(),
                gsReference_dailyUnique));

        // 메리디아니
        StorageReference gsReference_meridiani = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("메리디아니").getLogoRef());
        adapter_do.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("메리디아니").getName(),
                appState.getMeaningOutInfo().get("메리디아니").getTags().get(0),
                appState.getMeaningOutInfo().get("메리디아니").getLogoRef(),
                appState.getMeaningOutInfo().get("메리디아니").getUrl(),
                gsReference_meridiani));

        // 모레상점
        StorageReference gsReference_morestore2 = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("모레상점").getLogoRef());
        adapter_do.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("모레상점").getName(),
                appState.getMeaningOutInfo().get("모레상점").getTags().get(0),
                appState.getMeaningOutInfo().get("모레상점").getLogoRef(),
                appState.getMeaningOutInfo().get("모레상점").getUrl(),
                gsReference_morestore2));

        // 비코
        StorageReference gsReference_bcoe = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("비코").getLogoRef());
        adapter_do.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("비코").getName(),
                appState.getMeaningOutInfo().get("비코").getTags().get(0),
                appState.getMeaningOutInfo().get("비코").getLogoRef(),
                appState.getMeaningOutInfo().get("비코").getUrl(),
                gsReference_bcoe));

        // 비프렌드마켓
        StorageReference gsReference_befriendmarket = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("비프렌드마켓").getLogoRef());
        adapter_do.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("비프렌드마켓").getName(),
                appState.getMeaningOutInfo().get("비프렌드마켓").getTags().get(0),
                appState.getMeaningOutInfo().get("비프렌드마켓").getLogoRef(),
                appState.getMeaningOutInfo().get("비프렌드마켓").getUrl(),
                gsReference_befriendmarket));

        // 아틀리에리케
        StorageReference gsReference_atelierlykke = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("아틀리에리케").getLogoRef());
        adapter_do.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("아틀리에리케").getName(),
                appState.getMeaningOutInfo().get("아틀리에리케").getTags().get(0),
                appState.getMeaningOutInfo().get("아틀리에리케").getLogoRef(),
                appState.getMeaningOutInfo().get("아틀리에리케").getUrl(),
                gsReference_atelierlykke));

        // 윙블링
        StorageReference gsReference_wingbling = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("윙블링").getLogoRef());
        adapter_do.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("윙블링").getName(),
                appState.getMeaningOutInfo().get("윙블링").getTags().get(0),
                appState.getMeaningOutInfo().get("윙블링").getLogoRef(),
                appState.getMeaningOutInfo().get("윙블링").getUrl(),
                gsReference_wingbling));

        // 팅클유
        StorageReference gsReference_tingklu = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("팅클유").getLogoRef());
        adapter_do.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("팅클유").getName(),
                appState.getMeaningOutInfo().get("팅클유").getTags().get(0),
                appState.getMeaningOutInfo().get("팅클유").getLogoRef(),
                appState.getMeaningOutInfo().get("팅클유").getUrl(),
                gsReference_tingklu));





        // ---------------------------------------------------------------------------------------
        // 공정무역
        // ---------------------------------------------------------------------------------------
        SiteAdapter adapter_fair = new SiteAdapter();

        // FairTradeKorea
        StorageReference gsReference_FairTradeKorea = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("FairTradeKorea").getLogoRef());
        adapter_fair.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("FairTradeKorea").getName(),
                appState.getMeaningOutInfo().get("FairTradeKorea").getTags().get(0),
                appState.getMeaningOutInfo().get("FairTradeKorea").getLogoRef(),
                appState.getMeaningOutInfo().get("FairTradeKorea").getUrl(),
                gsReference_FairTradeKorea));

        // fynbo
        StorageReference gsReference_fynbo = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("fynbo").getLogoRef());
        adapter_fair.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("fynbo").getName(),
                appState.getMeaningOutInfo().get("fynbo").getTags().get(0),
                appState.getMeaningOutInfo().get("fynbo").getLogoRef(),
                appState.getMeaningOutInfo().get("fynbo").getUrl(),
                gsReference_fynbo));

        // 공기
        StorageReference gsReference_gonggi = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("공기").getLogoRef());
        adapter_fair.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("공기").getName(),
                appState.getMeaningOutInfo().get("공기").getTags().get(0),
                appState.getMeaningOutInfo().get("공기").getLogoRef(),
                appState.getMeaningOutInfo().get("공기").getUrl(),
                gsReference_gonggi));

        // 아름다운커피
        StorageReference gsReference_beautifulcoffee = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("아름다운커피").getLogoRef());
        adapter_fair.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("아름다운커피").getName(),
                appState.getMeaningOutInfo().get("아름다운커피").getTags().get(0),
                appState.getMeaningOutInfo().get("아름다운커피").getLogoRef(),
                appState.getMeaningOutInfo().get("아름다운커피").getUrl(),
                gsReference_beautifulcoffee));

        // 어스맨
        StorageReference gsReference_earthman = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("어스맨").getLogoRef());
        adapter_fair.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("어스맨").getName(),
                appState.getMeaningOutInfo().get("어스맨").getTags().get(0),
                appState.getMeaningOutInfo().get("어스맨").getLogoRef(),
                appState.getMeaningOutInfo().get("어스맨").getUrl(),
                gsReference_earthman));

        // 에코몽
        StorageReference gsReference_ecomont = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("에코몽").getLogoRef());
        adapter_fair.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("에코몽").getName(),
                appState.getMeaningOutInfo().get("에코몽").getTags().get(0),
                appState.getMeaningOutInfo().get("에코몽").getLogoRef(),
                appState.getMeaningOutInfo().get("에코몽").getUrl(),
                gsReference_ecomont));

        // 옥스팜
        StorageReference gsReference_oxfam = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("옥스팜").getLogoRef());
        adapter_fair.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("옥스팜").getName(),
                appState.getMeaningOutInfo().get("옥스팜").getTags().get(0),
                appState.getMeaningOutInfo().get("옥스팜").getLogoRef(),
                appState.getMeaningOutInfo().get("옥스팜").getUrl(),
                gsReference_oxfam));

        // 울림
        StorageReference gsReference_ullim = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("울림").getLogoRef());
        adapter_fair.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("울림").getName(),
                appState.getMeaningOutInfo().get("울림").getTags().get(0),
                appState.getMeaningOutInfo().get("울림").getLogoRef(),
                appState.getMeaningOutInfo().get("울림").getUrl(),
                gsReference_ullim));

        // 트립티
        StorageReference gsReference_tripti = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("트립티").getLogoRef());
        adapter_fair.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("트립티").getName(),
                appState.getMeaningOutInfo().get("트립티").getTags().get(0),
                appState.getMeaningOutInfo().get("트립티").getLogoRef(),
                appState.getMeaningOutInfo().get("트립티").getUrl(),
                gsReference_tripti));





        // ---------------------------------------------------------------------------------------
        // 플라스틱프리
        // ---------------------------------------------------------------------------------------
        SiteAdapter adapter_pla = new SiteAdapter();

        // 내추럴팁스
        StorageReference gsReference_naturaltips = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("내추럴팁스").getLogoRef());
        adapter_pla.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("내추럴팁스").getName(),
                appState.getMeaningOutInfo().get("내추럴팁스").getTags().get(0),
                appState.getMeaningOutInfo().get("내추럴팁스").getLogoRef(),
                appState.getMeaningOutInfo().get("내추럴팁스").getUrl(),
                gsReference_naturaltips));

        // 더피커
        StorageReference gsReference_thepicker = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("더피커").getLogoRef());
        adapter_pla.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("더피커").getName(),
                appState.getMeaningOutInfo().get("더피커").getTags().get(0),
                appState.getMeaningOutInfo().get("더피커").getLogoRef(),
                appState.getMeaningOutInfo().get("더피커").getUrl(),
                gsReference_thepicker));

        // 더험블
        StorageReference gsReference_thehumble = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("더험블").getLogoRef());
        adapter_pla.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("더험블").getName(),
                appState.getMeaningOutInfo().get("더험블").getTags().get(0),
                appState.getMeaningOutInfo().get("더험블").getLogoRef(),
                appState.getMeaningOutInfo().get("더험블").getUrl(),
                gsReference_thehumble));

        // 덕분애
        StorageReference gsReference_thanksto = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("덕분애").getLogoRef());
        adapter_pla.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("덕분애").getName(),
                appState.getMeaningOutInfo().get("덕분애").getTags().get(0),
                appState.getMeaningOutInfo().get("덕분애").getLogoRef(),
                appState.getMeaningOutInfo().get("덕분애").getUrl(),
                gsReference_thanksto));

        // 루나컵
        StorageReference gsReference_lunacup = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("루나컵").getLogoRef());
        adapter_pla.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("루나컵").getName(),
                appState.getMeaningOutInfo().get("루나컵").getTags().get(0),
                appState.getMeaningOutInfo().get("루나컵").getLogoRef(),
                appState.getMeaningOutInfo().get("루나컵").getUrl(),
                gsReference_lunacup));

        // 쎄비보타닉
        StorageReference gsReference_savvybotanic = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("쎄비보타닉").getLogoRef());
        adapter_pla.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("쎄비보타닉").getName(),
                appState.getMeaningOutInfo().get("쎄비보타닉").getTags().get(0),
                appState.getMeaningOutInfo().get("쎄비보타닉").getLogoRef(),
                appState.getMeaningOutInfo().get("쎄비보타닉").getUrl(),
                gsReference_savvybotanic));

        // 아로마티카
        StorageReference gsReference_aromatica = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("아로마티카").getLogoRef());
        adapter_pla.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("아로마티카").getName(),
                appState.getMeaningOutInfo().get("아로마티카").getTags().get(0),
                appState.getMeaningOutInfo().get("아로마티카").getLogoRef(),
                appState.getMeaningOutInfo().get("아로마티카").getUrl(),
                gsReference_aromatica));

        // 아이엠그리너
        StorageReference gsReference_iamgreener = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("아이엠그리너").getLogoRef());
        adapter_pla.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("아이엠그리너").getName(),
                appState.getMeaningOutInfo().get("아이엠그리너").getTags().get(0),
                appState.getMeaningOutInfo().get("아이엠그리너").getLogoRef(),
                appState.getMeaningOutInfo().get("아이엠그리너").getUrl(),
                gsReference_iamgreener));

        // 오랜
        StorageReference gsReference_oren = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("오랜").getLogoRef());
        adapter_pla.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("오랜").getName(),
                appState.getMeaningOutInfo().get("오랜").getTags().get(0),
                appState.getMeaningOutInfo().get("오랜").getLogoRef(),
                appState.getMeaningOutInfo().get("오랜").getUrl(),
                gsReference_oren));

        // 플라스틱제로
        StorageReference gsReference_plasticzero = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("플라스틱제로").getLogoRef());
        adapter_pla.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("플라스틱제로").getName(),
                appState.getMeaningOutInfo().get("플라스틱제로").getTags().get(0),
                appState.getMeaningOutInfo().get("플라스틱제로").getLogoRef(),
                appState.getMeaningOutInfo().get("플라스틱제로").getUrl(),
                gsReference_plasticzero));






        // ---------------------------------------------------------------------------------------
        // 업사이클링
        // ---------------------------------------------------------------------------------------
        SiteAdapter adapter_up = new SiteAdapter();

        // 119레오
        StorageReference gsReference_119reo = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("119레오").getLogoRef());
        adapter_up.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("119레오").getName(),
                appState.getMeaningOutInfo().get("119레오").getTags().get(0),
                appState.getMeaningOutInfo().get("119레오").getLogoRef(),
                appState.getMeaningOutInfo().get("119레오").getUrl(),
                gsReference_119reo));

        // 누깍
        StorageReference gsReference_nukak = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("누깍").getLogoRef());
        adapter_up.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("누깍").getName(),
                appState.getMeaningOutInfo().get("누깍").getTags().get(0),
                appState.getMeaningOutInfo().get("누깍").getLogoRef(),
                appState.getMeaningOutInfo().get("누깍").getUrl(),
                gsReference_nukak));

        // 메리우드
        StorageReference gsReference_merrywood = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("메리우드").getLogoRef());
        adapter_up.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("메리우드").getName(),
                appState.getMeaningOutInfo().get("메리우드").getTags().get(0),
                appState.getMeaningOutInfo().get("메리우드").getLogoRef(),
                appState.getMeaningOutInfo().get("메리우드").getUrl(),
                gsReference_merrywood));

        // 밀키프로젝트
        StorageReference gsReference_milkyproject = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("밀키프로젝트").getLogoRef());
        adapter_up.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("밀키프로젝트").getName(),
                appState.getMeaningOutInfo().get("밀키프로젝트").getTags().get(0),
                appState.getMeaningOutInfo().get("밀키프로젝트").getLogoRef(),
                appState.getMeaningOutInfo().get("밀키프로젝트").getUrl(),
                gsReference_milkyproject));

        // 얼킨
        StorageReference gsReference_ulkin = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("얼킨").getLogoRef());
        adapter_up.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("얼킨").getName(),
                appState.getMeaningOutInfo().get("얼킨").getTags().get(0),
                appState.getMeaningOutInfo().get("얼킨").getLogoRef(),
                appState.getMeaningOutInfo().get("얼킨").getUrl(),
                gsReference_ulkin));

        // 에코파티메아리
        StorageReference gsReference_ecopartymearry = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("에코파티메아리").getLogoRef());
        adapter_up.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("에코파티메아리").getName(),
                appState.getMeaningOutInfo().get("에코파티메아리").getTags().get(0),
                appState.getMeaningOutInfo().get("에코파티메아리").getLogoRef(),
                appState.getMeaningOutInfo().get("에코파티메아리").getUrl(),
                gsReference_ecopartymearry));

        // 오버랩
        StorageReference gsReference_overlab = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("오버랩").getLogoRef());
        adapter_up.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("오버랩").getName(),
                appState.getMeaningOutInfo().get("오버랩").getTags().get(0),
                appState.getMeaningOutInfo().get("오버랩").getLogoRef(),
                appState.getMeaningOutInfo().get("오버랩").getUrl(),
                gsReference_overlab));

        // 오운유
        StorageReference gsReference_ownu = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("오운유").getLogoRef());
        adapter_up.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("오운유").getName(),
                appState.getMeaningOutInfo().get("오운유").getTags().get(0),
                appState.getMeaningOutInfo().get("오운유").getLogoRef(),
                appState.getMeaningOutInfo().get("오운유").getUrl(),
                gsReference_ownu));

        // 컷더트래쉬
        StorageReference gsReference_cutthetrash = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("컷더트래쉬").getLogoRef());
        adapter_up.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("컷더트래쉬").getName(),
                appState.getMeaningOutInfo().get("컷더트래쉬").getTags().get(0),
                appState.getMeaningOutInfo().get("컷더트래쉬").getLogoRef(),
                appState.getMeaningOutInfo().get("컷더트래쉬").getUrl(),
                gsReference_cutthetrash));

        // 큐클리프
        StorageReference gsReference_cueclyp = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("큐클리프").getLogoRef());
        adapter_up.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("큐클리프").getName(),
                appState.getMeaningOutInfo().get("큐클리프").getTags().get(0),
                appState.getMeaningOutInfo().get("큐클리프").getLogoRef(),
                appState.getMeaningOutInfo().get("큐클리프").getUrl(),
                gsReference_cueclyp));



        // ---------------------------------------------------------------------------------------
        // 비건
        // ---------------------------------------------------------------------------------------
        SiteAdapter adapter_ve = new SiteAdapter();

        // 러빙헛
        StorageReference gsReference_lovinghut = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("러빙헛").getLogoRef());
        adapter_ve.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("러빙헛").getName(),
                appState.getMeaningOutInfo().get("러빙헛").getTags().get(0),
                appState.getMeaningOutInfo().get("러빙헛").getLogoRef(),
                appState.getMeaningOutInfo().get("러빙헛").getUrl(),
                gsReference_lovinghut));

        // 멜릭서
        StorageReference gsReference_melixir = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("멜릭서").getLogoRef());
        adapter_ve.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("멜릭서").getName(),
                appState.getMeaningOutInfo().get("멜릭서").getTags().get(0),
                appState.getMeaningOutInfo().get("멜릭서").getLogoRef(),
                appState.getMeaningOutInfo().get("멜릭서").getUrl(),
                gsReference_melixir));

        // 베지맘
        StorageReference gsReference_vegemom = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("베지맘").getLogoRef());
        adapter_ve.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("베지맘").getName(),
                appState.getMeaningOutInfo().get("베지맘").getTags().get(0),
                appState.getMeaningOutInfo().get("베지맘").getLogoRef(),
                appState.getMeaningOutInfo().get("베지맘").getUrl(),
                gsReference_vegemom));

        // 베지푸드
        StorageReference gsReference_vegefood = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("베지푸드").getLogoRef());
        adapter_ve.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("베지푸드").getName(),
                appState.getMeaningOutInfo().get("베지푸드").getTags().get(0),
                appState.getMeaningOutInfo().get("베지푸드").getLogoRef(),
                appState.getMeaningOutInfo().get("베지푸드").getUrl(),
                gsReference_vegefood));

        // 보나쥬르
        StorageReference gsReference_bonajour = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("보나쥬르").getLogoRef());
        adapter_ve.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("보나쥬르").getName(),
                appState.getMeaningOutInfo().get("보나쥬르").getTags().get(0),
                appState.getMeaningOutInfo().get("보나쥬르").getLogoRef(),
                appState.getMeaningOutInfo().get("보나쥬르").getUrl(),
                gsReference_bonajour));

        // 비건스페이스
        StorageReference gsReference_veganspace = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("비건스페이스").getLogoRef());
        adapter_ve.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("비건스페이스").getName(),
                appState.getMeaningOutInfo().get("비건스페이스").getTags().get(0),
                appState.getMeaningOutInfo().get("비건스페이스").getLogoRef(),
                appState.getMeaningOutInfo().get("비건스페이스").getUrl(),
                gsReference_veganspace));

        // 비건푸드
        StorageReference gsReference_veganfood = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("비건푸드").getLogoRef());
        adapter_ve.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("비건푸드").getName(),
                appState.getMeaningOutInfo().get("비건푸드").getTags().get(0),
                appState.getMeaningOutInfo().get("비건푸드").getLogoRef(),
                appState.getMeaningOutInfo().get("비건푸드").getUrl(),
                gsReference_veganfood));

        // 진선푸드
        StorageReference gsReference_jinsunfood = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("진선푸드").getLogoRef());
        adapter_ve.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("진선푸드").getName(),
                appState.getMeaningOutInfo().get("진선푸드").getTags().get(0),
                appState.getMeaningOutInfo().get("진선푸드").getLogoRef(),
                appState.getMeaningOutInfo().get("진선푸드").getUrl(),
                gsReference_jinsunfood));

        // 채식나라
        StorageReference gsReference_chaesiknara = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("채식나라").getLogoRef());
        adapter_ve.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("채식나라").getName(),
                appState.getMeaningOutInfo().get("채식나라").getTags().get(0),
                appState.getMeaningOutInfo().get("채식나라").getLogoRef(),
                appState.getMeaningOutInfo().get("채식나라").getUrl(),
                gsReference_chaesiknara));

        // 채식한끼몰
        StorageReference gsReference_hanggi = storage_root.getReferenceFromUrl(baseUrl + appState.getMeaningOutInfo().get("채식한끼몰").getLogoRef());
        adapter_ve.addItem(new SiteItem(
                appState.getMeaningOutInfo().get("채식한끼몰").getName(),
                appState.getMeaningOutInfo().get("채식한끼몰").getTags().get(0),
                appState.getMeaningOutInfo().get("채식한끼몰").getLogoRef(),
                appState.getMeaningOutInfo().get("채식한끼몰").getUrl(),
                gsReference_hanggi));



        listView_ani.setAdapter(adapter_ani);
        listView_do.setAdapter(adapter_do);
        listView_fair.setAdapter(adapter_fair);
        listView_pla.setAdapter(adapter_pla);
        listView_up.setAdapter(adapter_up);
        listView_ve.setAdapter(adapter_ve);



        setListViewHeight(listView_ani);
        setListViewHeight(listView_do);
        setListViewHeight(listView_fair);
        setListViewHeight(listView_pla);
        setListViewHeight(listView_up);
        setListViewHeight(listView_ve);


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
                    Intent intent = new Intent(SiteActivity.this, MypageActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }else{
                    // No user is signed in
                    Intent intent = new Intent(SiteActivity.this, AboutGoogleLogin.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
            }
        });

        //HashTag 키워드 클릭 시 이벤트
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
                Intent intent = new Intent(SiteActivity.this, HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        cate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount();
                Intent intent = new Intent(SiteActivity.this, CategoryActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        prod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount();
                Intent intent = new Intent(SiteActivity.this, ProductActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        stor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount();
                Intent intent = new Intent(SiteActivity.this, SiteActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }

    public void setListViewHeight(ListView listView){
        ListAdapter listAdapter = listView.getAdapter();

        int totalHeight = 0;

        for (int i = 0; i < listAdapter.getCount(); i++){
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
        return;
    }

    class SiteAdapter extends BaseAdapter {
        //데이터가 들어가있지 않고, 어떻게 담을지만 정의해뒀다.
        ArrayList<SiteItem> items = new ArrayList<SiteItem>();
        private WebView webView;

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(SiteItem item){
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        // 어댑터가 데이터를 관리하고 뷰도 만듬
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            SiteItemView siteItemView = null;
            // 코드를 재사용할 수 있도록
            if(convertView == null) {
                siteItemView = new SiteItemView(getApplicationContext());
            } else {
                siteItemView = (SiteItemView)convertView;
            }

            SiteItem item = items.get(position);
            siteItemView.setSite(item.getSite());
            siteItemView.setCategory(item.getCategory());
            siteItemView.setImage(item.getRef());
            View.OnClickListener gotosite;

            gotosite = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickCount();
                    Intent intent = new Intent(SiteActivity.this, Webview.class);
                    intent.putExtra("url", item.getUrl());
                    startActivity(intent);
                }
            };

            siteItemView.setButton(gotosite);
            return siteItemView;
        }
    }
}
