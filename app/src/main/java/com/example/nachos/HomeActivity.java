package com.example.nachos;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnCompleteListener;
import java.io.File;
import java.util.ArrayList;


import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Pattern;

import static android.view.inputmethod.EditorInfo.*;


public class HomeActivity extends AppCompatActivity {
    private ArrayList<Integer> imageList;
    private static final int DP = 24; //수치가 높을수록 옆에 그림이 조금씩 더 많이보임
    private RecyclerView listview;
    private MyAdapter adapter;

    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://nacho-da37d-default-rtdb.asia-southeast1.firebasedatabase.app");
    private DatabaseReference databaseReference = database.getReference("keyword");

    EditText keyword;
    Button add_bt;
    // meaningOut Keyword List
    private ArrayList<String> meaningOutKeywordList = new ArrayList<String>();

    private ApplicationState appState;

    // Realtime DB Event Listener
    private ChildEventListener mChild;
    private int count = 0;
    // should parse to json
    private StoreManager storeManager;
    private String siteInfo;
    //MyListDecoration decoration = new MyListDecoration();
    MyListDecoration decoration;

    private ImageView testImgView;

    //count
    private int counter;
    private int counter_cate;
    private int counter_h;
    private String score_h;
    private int score_h1;

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
        databaseReference.removeEventListener(mChild);
    }

    private void clickCount(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            System.out.println(user.getEmail());
            appState.setScore(appState.getScore() + 1);
        }
    }

    // 데이터 저장
    // SharedPreferences는 해당 프로세스(어플리케이션)내에 File 형태로 Data를 저장해
    // 해당 어플리케이션이 삭제되기 전까지 Data를 보관해 주는 기능
    // SharedPreferences 사용한 어플리케이션을 지우면 내용이 모두 삭제 됩니다. File이 삭제되는 것이지요.
    private void savescore(){
        SharedPreferences pref = getSharedPreferences("gostop", Activity.MODE_PRIVATE); //"gostop"은 SharedPreferences 이름. 여러개가 있을 수 있음
        SharedPreferences.Editor edit = pref.edit(); //만들어서 저장
        edit.putString("score_h", String.valueOf(counter_h)); //종료해도 저장됨
        edit.commit(); //저장은 필수
    }

    @Override
    protected void onResume() {//3
        //Toast.makeText(getApplicationContext(),"onResume 호출됨",Toast.LENGTH_SHORT).show();
        super.onResume();
    }

    //데이터 호출
    private void loadscore(){
        SharedPreferences pref = getSharedPreferences("gostop", Activity.MODE_PRIVATE); //불러올때 설정한 이름을 불러와야해
        score_h = pref.getString("score_h","0"); //값이 없을 때 0을 기본값으로 넣겠다.(기본값)
        Toast.makeText(getApplicationContext(),"점수 : "+score_h ,Toast.LENGTH_SHORT).show();
        //tv_count.setText(String.valueOf(score) +"도");
        System.out.println(score_h + "도");
        savescore();
    }

    @Override
    protected void onStart() {//2
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.initializeData();

        Intent intent = getIntent();
        counter = intent.getIntExtra("counter",0);
        counter_cate = intent.getIntExtra("counter_cate",0);

        SharedPreferences prefs = getSharedPreferences("counter_alone", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        counter_h = prefs.getInt("counter_h", 0);

        /*
        Button btn = findViewById(R.id.testBtn);
        ImageView testImgView = (ImageView) findViewById(R.id.testImgView);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference().child("apple.jpg");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseStorage storage = FirebaseStorage.getInstance(); // FirebaseStorage 인스턴스 생성
                StorageReference storageRef = storage.getReference("apple.jpg"); // 스토리지 공간을 참조해서 이미지를 가져옴
                Glide.with(view).load(storageRef).into(testImgView); // Glide를 사용하여 이미지 로드
            }
        });*/

        // custom title bar
        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);
        testImgView = (ImageView)findViewById(R.id.testImgView);

        // 처음 시작하면 키워드 사이 간격 null - decoration 호출하여 간격 5 추가
        // 처음만 호출하면
        if(decoration == null){
            decoration = new MyListDecoration();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    listview.addItemDecoration(decoration);
                }
            }, 50);


        }

        // first setting
        dataSetting();
//        storeManager.init();
        storeManager.getData();

        // initialize DB
        initDatabase();

        // load keyword data
        loadKeyword();
        init(meaningOutKeywordList);

        // Load Global Data
        appState = (ApplicationState) getApplication();

        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setClipToPadding(false);

        keyword = findViewById(R.id.text71);
        add_bt = findViewById(R.id.insert_key);

        Button title_back, title_prof; // 상단 타이틀
        Button home, cate, prod, stor; // 상단 탑뷰
        Button aboutpf, aboutve; // 대표 키워드 두개
        Button hash_up, hash_ve, hash_ft, hash_do, hash_aw, hash_pf; // hashtag들

        title_back = findViewById(R.id.btn_Back);
        title_prof = findViewById(R.id.btn_Profile);
        home = findViewById(R.id.button_home);
        cate = findViewById(R.id.buttom_cate);
        prod = findViewById(R.id.button_prod);
        stor = findViewById(R.id.button_stor);
        aboutpf = findViewById(R.id.goto_pl);
        aboutve = findViewById(R.id.goto_ve);
        hash_up = findViewById(R.id.hash11);
        hash_ve = findViewById(R.id.hash21);
        hash_ft = findViewById(R.id.hash31);
        hash_do = findViewById(R.id.hash41);
        hash_aw = findViewById(R.id.hash51);
        hash_pf = findViewById(R.id.hash61);


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
                    Intent intent = new Intent(HomeActivity.this, MypageActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }else{
                    // No user is signed in
                    Intent intent = new Intent(HomeActivity.this, AboutGoogleLogin.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
            }
        });


        // 상단 탑뷰 클릭 시 이벤트
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount();
                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        cate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount();
                Intent intent = new Intent(HomeActivity.this, CategoryActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        prod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount();
                Intent intent = new Intent(HomeActivity.this, ProductActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        stor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount();
                Intent intent = new Intent(HomeActivity.this, SiteActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        //HashTag 키워드 클릭 시 이벤트
        hash_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount();
                Intent intent = new Intent(HomeActivity.this, AboutUpActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        hash_ve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount();
                Intent intent = new Intent(HomeActivity.this, AboutVeActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        hash_ft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount();
                Intent intent = new Intent(HomeActivity.this, AboutFtActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        hash_do.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount();
                Intent intent = new Intent(HomeActivity.this, AboutDoActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        hash_aw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount();
                Intent intent = new Intent(HomeActivity.this, AboutAnActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        hash_pf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount();
                Intent intent = new Intent(HomeActivity.this, AboutPfActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        // 대표 키워드 두 개 : 플라스틱프리, 비건
        aboutpf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount();
                Intent intent = new Intent(HomeActivity.this, AboutPfActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });



        // About 비건 페이지 이동
        aboutve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount();
                Intent intent = new Intent(HomeActivity.this, AboutVeActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });


        float density = getResources().getDisplayMetrics().density;
        int margin = (int) (DP * density);
        viewPager.setPadding(margin, 0, margin, 0);
        viewPager.setPageMargin(margin/2);

        viewPager.setAdapter(new ViewPagerAdapter(this, imageList));


        /*
        viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = imageList.get(0);
                int pos1 = imageList.get(1);
                if (position == 0){
                    Intent intent = new Intent(HomeActivity.this, AboutPfActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
                else if (pos1 == 1){
                    Intent intent = new Intent(HomeActivity.this, AboutVeActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
                System.out.println("img0:"+imageList.get(0));
                System.out.println("img1:"+imageList.get(1));


            }
        }); */

        //keyword.setImeOptions(IME_ACTION_GO);
        keyword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                switch (actionId) {
                    case IME_ACTION_GO:

                        insertKeyword();
                        init(meaningOutKeywordList); // 버튼 누르면 init()호출
                        hideKeyboard();
                        // 검색 동작
                        break;
                    default:
                        // 기본 엔터키 동작
                        return false;
                }
                return true;
            }
        });

        add_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCount();
                count+=1;
                insertKeyword();
                init(meaningOutKeywordList); // 버튼 누르면 init()호출
                hideKeyboard();

            }
        });

//        getImageFromStorage();
    }
    /**
     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
     ActionBar actionBar = getSupportActionBar();

     // Custom Actionbar
     actionBar.setDisplayShowCustomEnabled(true);
     //actionBar.setDisplayHomeAsUpEnabled(false); //액션바 아이콘을 업 네비게이션 형태로 표시
     //actionBar.setDisplayShowTitleEnabled(false); //액션바에 표시되는 제목 표시유무
     //actionBar.setDisplayShowHomeEnabled(false); //홈 아이콘을 숨김처리

     LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
     View actionbar = inflater.inflate(R.layout.custom_title, null);


     actionBar.setCustomView(actionbar);
     return true;
     }

     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
     switch (item.getItemId()) {
     case R.id.btn_Back :
     onBackPressed();
     return true ;
     case R.id.btn_Profile:
     // 프로필
     Toast.makeText(getApplicationContext(), "프로필", Toast.LENGTH_SHORT).show();
     default :
     return super.onOptionsItemSelected(item) ;
     }
     }
     **/

    private void init(ArrayList<String> itemList) {

        listview = findViewById(R.id.recycler1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        listview.setLayoutManager(layoutManager);

        adapter = new MyAdapter(this, itemList, onClickItem);
        listview.setAdapter(adapter);

        MyListDecoration decoration = new MyListDecoration();
        listview.addItemDecoration(decoration);
    }

    // meaningout keywords
    private View.OnClickListener onClickItem = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            clickCount();
            String str = (String) v.getTag();
            Toast.makeText(HomeActivity.this, str, Toast.LENGTH_SHORT).show();

        }
    };


    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        keyword.setText(null);

    }

//    @Override
//    public void onBackPressed()
//    {
//        //super.onBackPressed();
//
//    }

    public void initializeData() //이미지를 저장함 배열에
    {
        imageList = new ArrayList();

        imageList.add(R.drawable.home_meaning);
        imageList.add(R.drawable.main_vegan);
        imageList.add(R.drawable.main_fairtrade);
        imageList.add(R.drawable.main_plasticfree);
        imageList.add(R.drawable.main_animal);
        imageList.add(R.drawable.main_donation);
        imageList.add(R.drawable.main_upcycling);

    }


    private void dataSetting(){
        siteInfo = getJsonString();
        storeManager = new StoreManager(siteInfo);
    }


    private void initDatabase() {
        mChild = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // Toast.makeText(HomeActivity.this, keyword.getText().toString() + "추가 완료", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                // Toast.makeText(HomeActivity.this,  "데이터가 변경되었습니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                // Toast.makeText(HomeActivity.this,  "데이터가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        databaseReference.addChildEventListener(mChild);
    }


    private void loadKeyword() {
//        ArrayList<String> itemList = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                meaningOutKeywordList.clear();
                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    // 전체 데이터 load
                    meaningOutKeywordList.add("#" + messageData.getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        return itemList;
    }

    private void insertKeyword(){
        String word = keyword.getText().toString();
        // 정규식 기반 예외처리 필요 (한, 영 숫자? 정도)됨
        if(word.length() == 0){
            Toast.makeText(HomeActivity.this, "값을 입력해주세요!", Toast.LENGTH_SHORT).show();
            return;
        }
        String regex = "^[a-zA-Z0-9가-힣]*$";
        if (Pattern.matches(regex, word)){
            // 공백은 예외적으로 자동 처리되어야하나?
            // 인스타 태그처럼 _ 는 어떨까
            databaseReference.child(word).setValue(word); // (key, value)
            meaningOutKeywordList.add("#" + word);

        }else {
            // 잘못된 문자열을 넣었을 때 아래 태그 간격이 계속 벌어지는 버그가 있음
            Toast.makeText(HomeActivity.this, "한글, 영문, 숫자로만 구성된 문자를 입력해주세요", Toast.LENGTH_SHORT).show();
        }
    }




    private String getJsonString()
    {
        String json = "";
        try {
            InputStream is = getAssets().open("siteInfo.json");
            int fileSize = is.available();

            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        return json;
    }

    // 여기  storage에서 이미지 가져오는 코드


//    private void getImageFromStorage(){
//
//
//
//        Button btn = findViewById(R.id.testBtn);
//        testImgView = (ImageView) findViewById(R.id.testImgView);
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference storageReference = storage.getReference();
//        StorageReference pathReference = storageReference.child("공정무역");
//        StorageReference submitProfile = storageReference.child("공정무역/공기_로고.png");
//        submitProfile.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                Glide.with(HomeActivity.this).load(uri).into(testImgView);
//                System.out.println("uri"+uri);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                System.out.println("uri");
//            }
//        });
//
//
//
//
//        System.out.println(appState.getMeaningOutInfo().get("119레오").getLogoRef());
//
//        StorageReference storageRef = storage.getReference(); // 스토리지 공간을 참조해서 이미지를 가져옴 (로고)
//
//        StorageReference gsReference = storage.getReferenceFromUrl("gs://nacho-da37d.appspot.com/공정무역/공기_로고.png");
//
//        StorageReference productRef = storage.getReference(
//                appState.getMeaningOutInfo().get("119레오").getProducts().get(0).getStorageRef()); // 스토리지 공간을 참조해서 이미지를 가져옴 (상품)
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Glide.with(view).load(gsReference).override(1000).into(testImgView); // Glide를 사용하여 이미지 로드
//
////                storageRef.getDownloadUrl().addOnCompleteListener(task -> {
////                    if (task.isSuccessful()){
////                        Glide.with(getApplicationContext() /* context */)
////                                .load(storageRef)
////                                .into(testImgView);
////                    }else{
////                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
////                    }
////                });
//            }
//        });
//    }
}