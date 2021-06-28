package com.arun.assignment.util;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Global {

    public int getPostition(String l){
        int pos = 0;
        for (int i=0; i<letters.length; i++){
            if (l.equals(letters[i])){
                pos = i;
                break;
            }
        }
        return pos;
    }

    public String getLetter(int pos){
        String s = "";
        for (int i=0; i<letters.length; i++){
            if (pos == i){
                s = letters[i];
                break;
            }
        }
        return s;
    }

    public void openFragment(FragmentManager fm, int container, Fragment fragment){
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(container, fragment);
        transaction.commit();
    }

    public void hideKeypad(Context context, EditText editText){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    //---------------------------------------
    private String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
}
