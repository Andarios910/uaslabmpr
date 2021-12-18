package com.example.uaslabmpr;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private Button vaksinbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        vaksinbutton = (Button)  findViewById(R.id.vaksinbutton);
        vaksinbutton.setOnClickListener((View.OnClickListener) this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.vaksinbutton:
                startActivity(new Intent(this, Form.class));
                break;



        }
    }
}