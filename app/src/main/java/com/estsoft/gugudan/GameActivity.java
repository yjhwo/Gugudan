package com.estsoft.gugudan;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    public static Activity GameActivity;

    private Timer timer;
    private static final int TIME_LIMIT = 5;
    private Button[] btnList = new Button[9];
    private TextView txtNum1, txtNum2, txtAnswer, txtTime;
    private static int ansPosition;                 // 정답 위치
    private static int ansNum = 0;                  // 정답 개수


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        GameActivity = GameActivity.this;                   // AlertActivity에서 써먹음

        txtNum1 = (TextView)findViewById(R.id.txt_num1);
        txtNum2 = (TextView)findViewById(R.id.txt_num2);
        txtAnswer = (TextView)findViewById(R.id.txt_answer);

        int id = R.id.btn1;
        for(int i=0; i<9; i++){
            btnList[i] = (Button)findViewById(id+i);
            btnList[i].setOnClickListener(this);
        }


    }

    @Override
    protected void onStart() {
        super.onStart();

        txtAnswer.setText("0");             // 초기화
        ansNum = 0;

        guguTest();
        timer = new Timer();
        timer.schedule(new MyTimerTask(), 1000, 1000);  //  1초 후에 1초마다 시작하라.
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0){
            if(resultCode == Activity.RESULT_OK){
                txtAnswer.setText("0");             // 초기화
                ansNum = 0;
            }
        }
    }

    @Override
    public void onClick(View v) {

        Button btn = (Button)v;                                         // 클릭된 뷰를 버튼으로 받아옴

        for(Button b : btnList){
            if(btnList[ansPosition] == btn){                         // 클릭된 버튼
                Toast.makeText(getApplicationContext(),"정답입니다",Toast.LENGTH_SHORT).show();
                txtAnswer.setText(++ansNum+"/10");

                if(ansNum == 10){
                    timer.cancel();

                    Intent intent = new Intent(GameActivity.this, AlertActivity.class);
                    intent.putExtra("correctNum",ansNum);
                    startActivityForResult(intent, 0);

                    txtAnswer.setText("0");                 // 초기화
                    ansNum = 0;
                }

                guguTest();
                return;
            }
        }
        //오답 클릭한 경우
        Toast.makeText(getApplicationContext(),"틀렸습니다.",Toast.LENGTH_SHORT).show();
        guguTest();
    }

    public void guguTest(){

        Random r = new Random();

        int random1 = r.nextInt(7)+2;           // 2~9 , (예외 1,1 나올 경우 방지하기 위해)
        Log.e("random1",random1+"");
        int random2 = r.nextInt(8)+1;
        Log.e("random2",random2+"");
        int ans = random1 * random2;
        Log.e("ans:",ans+"");

        txtNum1.setText(random1+"");
        txtNum2.setText(random2+"");

        ansPosition = r.nextInt(8)+1;
        Log.e("ansPosition:",ansPosition+"");

        // 버튼에 놓을 수 list 생성
        List<Integer> wrongList = new ArrayList<Integer>(18);
        for(int i=1; i<=9; i++){
            boolean chk = true;

            int n1 = random1*i;
            Log.e("n1",n1+"");
            int n2 = ( random1==random2 ? (random1+10)*i: random2*i );
            Log.e("n2",n2+"");

            if(i==1) {
                wrongList.add(n1);
                wrongList.add(n2);
                continue;
            }

            for(int c=0; c<wrongList.size(); c++){            // 중복 제거
                if( (n1 == wrongList.get(c)) || n1 == ans) {
                    Log.e("if1",n1+"");
                    chk = false;
                    continue;
                }

                if((n2 == wrongList.get(c)) || (n2 == n1) || n2 == ans) {
                    Log.e("if2",n2+"");
                    chk = false;
                    continue;
                }
            }

            if(chk) {
                wrongList.add(n1);
                wrongList.add(n2);
            }

        }
        Collections.shuffle(wrongList);

        for (int i=0; i<wrongList.size(); i++){
            Log.e(i+"",wrongList.get(i)+"");
        }

        // 버튼에 배치
        for(int i=0; i<btnList.length; i++){
            if(ansPosition == i){
                btnList[i].setText(ans+"");                         // 정답
                continue;
            }

            btnList[i].setText(wrongList.get(i)+"");
        }

    }


    private class MyTimerTask extends TimerTask {
        private int seconds = 0;

        @Override
        public void run() {
            if(++seconds > TIME_LIMIT){
                timer.cancel();

                Intent intent = new Intent(GameActivity.this, AlertActivity.class);
                intent.putExtra("correctNum",ansNum);
                startActivityForResult(intent, 0);
            }

            // UI 변경
            // 메인 스레드에서 돌려준다.
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView  tv = (TextView)findViewById(R.id.txt_remainingTime);
                    tv.setText(seconds+"");
                }
            });

        }
    }

}
