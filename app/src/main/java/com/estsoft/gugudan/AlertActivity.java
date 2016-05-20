package com.estsoft.gugudan;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AlertActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        final GameActivity gameActivity = (GameActivity)GameActivity.GameActivity;
        final Intent intent = getIntent();
        Integer correctNum = intent.getIntExtra("correctNum", -1);

        TextView txtCorrect = (TextView)findViewById(R.id.txt_CorrectNum);
        txtCorrect.setText(correctNum+"");


        Button btnYes = (Button)findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        Button btnNo = (Button)findViewById(R.id.btnNo);
        if (btnNo != null) {
            btnNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(AlertActivity.this, MainActivity.class);
                    startActivity(i);

                    gameActivity.finish();              // 뒤로가기 버튼 시 ...
                    finish();
                }
            });
        }
    }

}
