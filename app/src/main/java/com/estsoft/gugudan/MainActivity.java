package com.estsoft.gugudan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_gameStart).setOnClickListener(this);
        findViewById(R.id.btn_record).setOnClickListener(this);
        findViewById(R.id.btn_help).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId =  v.getId();
        Intent intent;

        switch (viewId){
            case R.id.btn_gameStart:
                intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_record:
                intent = new Intent(MainActivity.this, RecordActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_help:
                intent = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(intent);
                break;
            default:
                Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG);
        }


    }
}
