package com.example.nachos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class CountActivity extends AppCompatActivity {
    private TextView tvCount;
    private int result;
    private String score;
    private int counter;
    private int counter_cate;
    private int counter_h;
    private int score1;
    //private int add;

    @Override
    protected void onStop() {//5
        //Toast.makeText(getApplicationContext(),"onStop 호출됨",Toast.LENGTH_SHORT).show();
        super.onStop();
    }

    @Override
    protected void onDestroy() {//6
        //Toast.makeText(getApplicationContext(),"onDestroy 호출됨",Toast.LENGTH_SHORT).show();
        savescore();

        super.onDestroy();
    }

    @Override
    protected void onPause() {//4
        //Toast.makeText(getApplicationContext(),"onPause 호출됨",Toast.LENGTH_SHORT).show();
        savescore(); //앱이 도중에 잘못되어도 저장됨
        super.onPause();
    }

    //데이터 저장
//SharedPreferences는 해당 프로세스(어플리케이션)내에 File 형태로 Data를 저장해
//해당 어플리케이션이 삭제되기 전까지 Data를 보관해 주는 기능
//SharedPreferences 사용한 어플리케이션을 지우면 내용이 모두 삭제 됩니다. File이 삭제되는 것이지요.
    private void savescore(){
        SharedPreferences pref = getSharedPreferences("gostop", Activity.MODE_PRIVATE); //"gostop"은 SharedPreferences 이름. 여러개가 있을 수 있음
        SharedPreferences.Editor edit = pref.edit(); //만들어서 저장
        edit.putString("score", String.valueOf(result)); //종료해도 저장됨
        edit.commit(); //저장은 필수
    }

    @Override
    protected void onResume() {//3
        //Toast.makeText(getApplicationContext(),"onResume 호출됨",Toast.LENGTH_SHORT).show();
        loadscore();
        super.onResume();
    }

    //데이터 호출
    private void loadscore(){
        SharedPreferences pref = getSharedPreferences("gostop", Activity.MODE_PRIVATE); //불러올때 설정한 이름을 불러와야해
        score = pref.getString("score","0"); //값이 없을 때 0을 기본값으로 넣겠다.(기본값)
        Toast.makeText(getApplicationContext(),"점수 : "+score ,Toast.LENGTH_SHORT).show();
        tvCount.setText(String.valueOf(score) +"도");
        savescore();
    }

    @Override
    protected void onStart() {//2
        //Toast.makeText(getApplicationContext(),"onStart 호출됨",Toast.LENGTH_SHORT).show();
        loadscore();
        super.onStart();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);


        if (score == null) {
            score1 = 0; //score int형 값
        }
        else {
            score1 =  Integer.valueOf(score);
        }
        counter = intent.getIntExtra("counter",score1); //about_an 터치 횟수
        counter_cate = intent.getIntExtra("counter_cate",score1); //category
        counter_h = intent.getIntExtra("counter_h",score1); //category

        SharedPreferences prefs = getSharedPreferences("counter_alone", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        int add = prefs.getInt("counter_cate", 0); //category 터치 횟수 내부 저장
        int add2 = prefs.getInt("counter", 0); // about_ac
        int add3 = prefs.getInt("counter_h", 0); // home
        result = add + add2 + add3; //터치 횟수 합산

        if (result == 0){
            editor.putInt("result", score1);
            editor.apply();
        }
        else {
            editor.putInt("result", result);
            editor.apply();
        }



        System.out.println("counter: "+ counter);
        System.out.println("counter: "+ counter_cate);
        System.out.println("result: "+ result);
        System.out.println("score: "+ score1);




        tvCount = findViewById(R.id.tv_count);
       // tvCount.setText(result +"도");


        savescore();

    }





}