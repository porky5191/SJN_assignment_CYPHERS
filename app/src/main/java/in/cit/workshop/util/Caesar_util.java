package com.arun.assignment.util;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

public class Caesar_util {
    public Caesar_util(Context context) {
        global = new Global();
        this.context = context;
    }

    public String encrypt(String text, int key){
        StringBuilder cypher = new StringBuilder();
        for (int i=0; i<text.length(); i++){
            int pos = global.getPostition(""+text.charAt(i)) + key;
            cypher.append(global.getLetter(pos > 25 ? pos -26 : pos));
        }
        return cypher.toString();
    }

    public String decrypt(String text, int key){
        StringBuilder plainText = new StringBuilder();
        for (int i=0; i<text.length(); i++){
            int pos = global.getPostition(""+text.charAt(i)) - key;
            plainText.append(global.getLetter(pos < 0 ? pos + 26 : pos));
        }
        return plainText.toString();
    }

    public boolean valid(EditText tf_key, EditText tf_insertTest){
        String key = tf_key.getText().toString();
        if (key.isEmpty()){
            Toast.makeText(context, "KEY can't be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Integer.parseInt(key) > 25){
            Toast.makeText(context, "KEY can't be larger than 25", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (tf_insertTest.getText().toString().isEmpty()){
            Toast.makeText(context, "TEXT can't be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //-----------------------------
    private Global global;
    private Context context;

}
