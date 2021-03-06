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
    private static final int DP = 24; //????????? ???????????? ?????? ????????? ????????? ??? ????????????
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

    // ????????? ??????
    // SharedPreferences??? ?????? ????????????(??????????????????)?????? File ????????? Data??? ?????????
    // ?????? ????????????????????? ???????????? ????????? Data??? ????????? ?????? ??????
    // SharedPreferences ????????? ????????????????????? ????????? ????????? ?????? ?????? ?????????. File??? ???????????? ????????????.
    private void savescore(){
        SharedPreferences pref = getSharedPreferences("gostop", Activity.MODE_PRIVATE); //"gostop"??? SharedPreferences ??????. ???????????? ?????? ??? ??????
        SharedPreferences.Editor edit = pref.edit(); //???????????? ??????
        edit.putString("score_h", String.valueOf(counter_h)); //???????????? ?????????
        edit.commit(); //????????? ??????
    }

    @Override
    protected void onResume() {//3
        //Toast.makeText(getApplicationContext(),"onResume ?????????",Toast.LENGTH_SHORT).show();
        super.onResume();
    }

    //????????? ??????
    private void loadscore(){
        SharedPreferences pref = getSharedPreferences("gostop", Activity.MODE_PRIVATE); //???????????? ????????? ????????? ???????????????
        score_h = pref.getString("score_h","0"); //?????? ?????? ??? 0??? ??????????????? ?????????.(?????????)
        Toast.makeText(getApplicationContext(),"?????? : "+score_h ,Toast.LENGTH_SHORT).show();
        //tv_count.setText(String.valueOf(score) +"???");
        System.out.println(score_h + "???");
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
                FirebaseStorage storage = FirebaseStorage.getInstance(); // FirebaseStorage ???????????? ??????
                StorageReference storageRef = storage.getReference("apple.jpg"); // ???????????? ????????? ???????????? ???????????? ?????????
                Glide.with(view).load(storageRef).into(testImgView); // Glide??? ???????????? ????????? ??????
            }
        });*/

        // custom title bar
        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);
        testImgView = (ImageView)findViewById(R.id.testImgView);

        // ?????? ???????????? ????????? ?????? ?????? null - decoration ???????????? ?????? 5 ??????
        // ????????? ????????????
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

        Button title_back, title_prof; // ?????? ?????????
        Button home, cate, prod, stor; // ?????? ??????
        Button aboutpf, aboutve; // ?????? ????????? ??????
        Button hash_up, hash_ve, hash_ft, hash_do, hash_aw, hash_pf; // hashtag???

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


        // ?????? ????????? ?????? ?????? ??? ?????????
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


        // ?????? ?????? ?????? ??? ?????????
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

        //HashTag ????????? ?????? ??? ?????????
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

        // ?????? ????????? ??? ??? : ??????????????????, ??????
        aboutpf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount();
                Intent intent = new Intent(HomeActivity.this, AboutPfActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });



        // About ?????? ????????? ??????
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
                        init(meaningOutKeywordList); // ?????? ????????? init()??????
                        hideKeyboard();
                        // ?????? ??????
                        break;
                    default:
                        // ?????? ????????? ??????
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
                init(meaningOutKeywordList); // ?????? ????????? init()??????
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
     //actionBar.setDisplayHomeAsUpEnabled(false); //????????? ???????????? ??? ??????????????? ????????? ??????
     //actionBar.setDisplayShowTitleEnabled(false); //???????????? ???????????? ?????? ????????????
     //actionBar.setDisplayShowHomeEnabled(false); //??? ???????????? ????????????

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
     // ?????????
     Toast.makeText(getApplicationContext(), "?????????", Toast.LENGTH_SHORT).show();
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

    public void initializeData() //???????????? ????????? ?????????
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
                // Toast.makeText(HomeActivity.this, keyword.getText().toString() + "?????? ??????", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                // Toast.makeText(HomeActivity.this,  "???????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                // Toast.makeText(HomeActivity.this,  "???????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
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
                    // ?????? ????????? load
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
        // ????????? ?????? ???????????? ?????? (???, ??? ??????? ??????)???
        if(word.length() == 0){
            Toast.makeText(HomeActivity.this, "?????? ??????????????????!", Toast.LENGTH_SHORT).show();
            return;
        }
        String regex = "^[a-zA-Z0-9???-???]*$";
        if (Pattern.matches(regex, word)){
            // ????????? ??????????????? ?????? ??????????????????????
            // ????????? ???????????? _ ??? ?????????
            databaseReference.child(word).setValue(word); // (key, value)
            meaningOutKeywordList.add("#" + word);

        }else {
            // ????????? ???????????? ????????? ??? ?????? ?????? ????????? ?????? ???????????? ????????? ??????
            Toast.makeText(HomeActivity.this, "??????, ??????, ???????????? ????????? ????????? ??????????????????", Toast.LENGTH_SHORT).show();
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

    // ??????  storage?????? ????????? ???????????? ??????


//    private void getImageFromStorage(){
//
//
//
//        Button btn = findViewById(R.id.testBtn);
//        testImgView = (ImageView) findViewById(R.id.testImgView);
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference storageReference = storage.getReference();
//        StorageReference pathReference = storageReference.child("????????????");
//        StorageReference submitProfile = storageReference.child("????????????/??????_??????.png");
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
//        System.out.println(appState.getMeaningOutInfo().get("119??????").getLogoRef());
//
//        StorageReference storageRef = storage.getReference(); // ???????????? ????????? ???????????? ???????????? ????????? (??????)
//
//        StorageReference gsReference = storage.getReferenceFromUrl("gs://nacho-da37d.appspot.com/????????????/??????_??????.png");
//
//        StorageReference productRef = storage.getReference(
//                appState.getMeaningOutInfo().get("119??????").getProducts().get(0).getStorageRef()); // ???????????? ????????? ???????????? ???????????? ????????? (??????)
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Glide.with(view).load(gsReference).override(1000).into(testImgView); // Glide??? ???????????? ????????? ??????
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