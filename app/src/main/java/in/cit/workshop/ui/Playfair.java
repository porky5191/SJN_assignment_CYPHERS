package com.arun.assignment.ui;

import android.graphics.Point;
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
import com.arun.assignment.util.Global;

public class Playfair extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (root == null){
            root = inflater.inflate(R.layout.playfair, container, false);
        }
        init();
        return root;
    }

    private void init(){
        tf_key      = root.findViewById(R.id.tf_key);
        tf_text     = root.findViewById(R.id.tf_text);
        btn_encrypt = root.findViewById(R.id.btn_encrypt);
        btn_decrypt = root.findViewById(R.id.btn_decrypt);
        l_output    = root.findViewById(R.id.l_output);

        btn_encrypt.setOnClickListener(this);
        btn_decrypt.setOnClickListener(this);

        global      = new Global();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_encrypt:
                loadData();
                String enc = encode(prepareText(txt, changeJtoI));
                l_output.setText(enc);
                global.hideKeypad(getActivity(), tf_text);
                break;
            case R.id.btn_decrypt:
                loadData();
                if (txt.length() %2 == 0){
                    String dec = decode(txt);
                    l_output.setText(dec);
                    global.hideKeypad(getActivity(), tf_text);
                }else {
                    Toast.makeText(getActivity(), "cypher text can't have odd no of character", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
        }
    }

    private void loadData(){
        key         = tf_key.getText().toString().toUpperCase();
        txt         = tf_text.getText().toString().toUpperCase();
        changeJtoI  = true;//cb_yes.isSelected();
        createTable(key, changeJtoI);
    }

    private static void createTable(String key, boolean changeJtoI) {
        charTable = new char[5][5];
        positions = new Point[26];

        String s = prepareText(key + "ABCDEFGHIJKLMNOPQRSTUVWXYZ", changeJtoI);

        int len = s.length();
        for (int i = 0, k = 0; i < len; i++) {
            char c = s.charAt(i);
            if (positions[c - 'A'] == null) {
                charTable[k / 5][k % 5] = c;
                positions[c - 'A'] = new Point(k % 5, k / 5);
                k++;
            }
        }
    }

    private static String prepareText(String s, boolean changeJtoI) {
        s = s.toUpperCase().replaceAll("[^A-Z]", "");
        return changeJtoI ? s.replace("J", "I") : s.replace("Q", "");
    }

    private static String encode(String s) {
        StringBuilder sb = new StringBuilder(s);

        for (int i = 0; i < sb.length(); i += 2) {

            if (i == sb.length() - 1)
                sb.append(sb.length() % 2 == 1 ? 'X' : "");

            else if (sb.charAt(i) == sb.charAt(i + 1))
                sb.insert(i + 1, 'X');
        }
        return codec(sb, 1);
    }

    private static String decode(String s) {
        return codec(new StringBuilder(s), 4);
    }

    private static String codec(StringBuilder text, int direction) {
        int len = text.length();
        for (int i = 0; i < len; i += 2) {
            char a = text.charAt(i);
            char b = text.charAt(i + 1);

            int row1 = positions[a - 'A'].y;
            int row2 = positions[b - 'A'].y;
            int col1 = positions[a - 'A'].x;
            int col2 = positions[b - 'A'].x;

            if (row1 == row2) {
                col1 = (col1 + direction) % 5;
                col2 = (col2 + direction) % 5;

            } else if (col1 == col2) {
                row1 = (row1 + direction) % 5;
                row2 = (row2 + direction) % 5;

            } else {
                int tmp = col1;
                col1 = col2;
                col2 = tmp;
            }

            text.setCharAt(i, charTable[row1][col1]);
            text.setCharAt(i + 1, charTable[row2][col2]);
        }
        return text.toString();
    }

    //----------------------
    private View root;
    private EditText tf_key, tf_text;
    private Button btn_encrypt, btn_decrypt;
    private TextView l_output;

    private Global global;

    //-----------------------------
    private static char[][] charTable;
    private static Point[] positions;
    private String key, txt;
    private boolean changeJtoI;

}
