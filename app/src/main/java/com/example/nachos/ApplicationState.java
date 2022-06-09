package com.example.nachos;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ApplicationState extends Application {

    private HashMap<String, SiteInfo> meaningOutInfo;
    private long score = 0;

    private StoreManager storeManager;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseFirestore db;

    @Override
    public void onCreate() {
        //변수 초기화
        db = FirebaseFirestore.getInstance();
        meaningOutInfo = new HashMap<>();
        String temp = getJsonString();
        System.out.println(temp);
        storeManager = new StoreManager(temp);
        meaningOutInfo = new HashMap<>(storeManager.getMeaningOutInfo());
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    System.out.println("현재 로그인 한 상태");
                }else{
                    System.out.println("로그인 해주세요");
                }
            }
        };

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            DocumentReference productRef = db.collection("Users").document(user.getEmail());
            productRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            score = (long) document.get("score");
                            Log.d("123", "DocumentSnapshot data: " + document.getData());
                        } else {
                            Log.d("123", "No such document");
                        }
                    } else {
                        Log.d("123", "get failed with ", task.getException());
                    }
                }
            });
        }

        System.out.println("어플리케이션 생성됨");
        super.onCreate();
    }

    public void saveScoreToFirebase(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Map<String, Object> docData = new HashMap<>();
        docData.put("name", user.getDisplayName());
        docData.put("email", user.getEmail());
        docData.put("score", score);
        db.collection("Users")
                .document(user.getEmail())
                .set(docData);
    }


    private void hardPushToDB(){
        // 데이터 없어서 갱신해야할 때
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        Map<String, Object> docData = new HashMap<>();
//        docData.put("name", user.getDisplayName());
//        docData.put("email", user.getEmail());
//        docData.put("score", 0);
//        db.collection("Users")
//                .document(user.getEmail())
//                .set(docData);
    }

    // 1. local test
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

    @Override
    public void onTerminate() {
        //프로세스 소멸 시
        super.onTerminate();
    }

    public HashMap<String, SiteInfo> getMeaningOutInfo() {
        return meaningOutInfo;
    }

    public void setMeaningOutInfo() {
        this.meaningOutInfo = storeManager.getMeaningOutInfo();
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

}