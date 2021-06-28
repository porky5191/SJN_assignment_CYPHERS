package com.arun.assignment.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.arun.assignment.R;
import com.arun.assignment.util.Global;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        l_caesar    = findViewById(R.id.l_caesar);
        l_playfair  = findViewById(R.id.l_playfair);
        l_des       = findViewById(R.id.l_des);
        l_header    = findViewById( R.id.l_header);

        l_caesar    .setOnClickListener(this);
        l_playfair  .setOnClickListener(this);
        l_des       .setOnClickListener(this);

        global      = new Global();
        global.openFragment(getSupportFragmentManager(), R.id.frame_layout, new Caesar());
        setBackgrouond(l_caesar, l_playfair, l_des);
        l_header.setText("Caesar Cypher");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.l_caesar:
                l_header.setText("Caesar Cypher");
                setBackgrouond(l_caesar, l_playfair, l_des);
                global.openFragment(getSupportFragmentManager(), R.id.frame_layout, new Caesar());
                break;
            case R.id.l_playfair:
                l_header.setText("Playfair Cypher");
                setBackgrouond(l_playfair, l_caesar, l_des);
                global.openFragment(getSupportFragmentManager(), R.id.frame_layout, new Playfair());
                break;
            case R.id.l_des:
                l_header.setText("DES Cypher");
                setBackgrouond(l_des, l_caesar, l_playfair);
                global.openFragment(getSupportFragmentManager(), R.id.frame_layout, new Des());
                break;
        }
    }

    private void setBackgrouond(TextView l1, TextView l2, TextView l3){
        l1.setBackgroundColor(getResources().getColor(R.color.colorHeader));
        l1.setTextColor(getResources().getColor(R.color.colorWhite));
        l2.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        l2.setTextColor(getResources().getColor(R.color.colorText));
        l3.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        l3.setTextColor(getResources().getColor(R.color.colorText));
    }



    //----------------------------------
    TextView l_caesar, l_playfair, l_des, l_header;

    //------------------------------
    Global global;

}
