package com.arun.assignment.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.arun.assignment.R;
import com.arun.assignment.util.Caesar_util;
import com.arun.assignment.util.Global;

public class Caesar extends Fragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (root == null){
            root = inflater.inflate(R.layout.caesar, container, false);
        }
        init();
        return root;
    }

    //initialize different types of variables here
    private void init(){
        tf_insertTest   = root.findViewById(R.id.tf_insertTest);
        tf_key          = root.findViewById(R.id.tf_key);
        btn_encrypt     = root.findViewById(R.id.btn_encrypt);
        btn_decrypt     = root.findViewById(R.id.btn_decrypt);
        l_output        = root.findViewById(R.id.l_output);

        util            = new Caesar_util(getActivity());
        global          = new Global();

        btn_encrypt.setOnClickListener(this);
        btn_decrypt.setOnClickListener(this);
    }

    //onclick actions
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_encrypt:
                if (!util.valid(tf_key, tf_insertTest)) return;
                String s = util.encrypt(tf_insertTest.getText().toString().toUpperCase().replaceAll(" ", ""), Integer.parseInt(tf_key.getText().toString()));
                l_output.setText(s);
                global.hideKeypad(getActivity(), tf_insertTest);
                break;
            case R.id.btn_decrypt:
                if (!util.valid(tf_key, tf_insertTest)) return;
                String s1 = util.decrypt(tf_insertTest.getText().toString().toUpperCase().replaceAll(" ", ""), Integer.parseInt(tf_key.getText().toString()));
                l_output.setText(s1);
                global.hideKeypad(getActivity(), tf_insertTest);
                break;
        }
    }



    //-----------------------------------------
    private View root;
    private EditText tf_insertTest, tf_key;
    private Button btn_encrypt, btn_decrypt;
    private TextView l_output;

    //-----------------------------------------
    private Caesar_util util;
    private Global global;

}
