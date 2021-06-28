package com.arun.assignment.ui;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.arun.assignment.R;
import com.arun.assignment.util.Global;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class Des extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (root == null){
            root = inflater.inflate(R.layout.des, container, false);
        }
        init();
        return root;
    }

    private void init(){
        tf_text     = root.findViewById(R.id.tf_text);
        tf_key      = root.findViewById(R.id.tf_key);
        btn_encrypt = root.findViewById(R.id.btn_encrypt);
        btn_decrypt = root.findViewById(R.id.btn_decrypt);
        l_output    = root.findViewById(R.id.l_output);

        btn_encrypt .setOnClickListener(this);
        btn_decrypt .setOnClickListener(this);

        global      = new Global();

        /*try {
            // generate secret key using DES algorithm
            key = KeyGenerator.getInstance("DES").generateKey();
            tf_key.setText(Arrays.toString(key.getEncoded()));

            ecipher = Cipher.getInstance("DES");
            dcipher = Cipher.getInstance("DES");

            // initialize the ciphers with the given key
            ecipher.init(Cipher.ENCRYPT_MODE, key);
            dcipher.init(Cipher.DECRYPT_MODE, key);

        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.d("OUTPUT_", "No Such Algorithm:" + e.getMessage());
            return;
        }
        catch (NoSuchPaddingException e) {
            e.printStackTrace();
            Log.d("OUTPUT_", "No Such Padding:" + e.getMessage());
            return;
        }
        catch (InvalidKeyException e) {
            e.printStackTrace();
            Log.d("OUTPUT_", "Invalid Key:" + e.getMessage());
            return;
        }*/
    }


    @Override
    public void onClick(View v) {
        //System.out.println("Decrypted: " + decrypted);

        switch (v.getId()){
            case R.id.btn_encrypt:
                generateKey();
                String encrypted = encrypt(tf_text.getText().toString());
                l_output.setText(encrypted);
                assert getActivity()!=null;
                global.hideKeypad(getActivity(), tf_text);
                break;
            case R.id.btn_decrypt:
                generateKey();
                String decrypted = decrypt(tf_text.getText().toString());
                l_output.setText(decrypted);
                assert getActivity()!=null;
                global.hideKeypad(getActivity(), tf_text);
                break;
        }
    }

    private void generateKey(){
        String passPhrase = tf_key.getText().toString();//"My Secret Password";

        try {
            // create a user-chosen password that can be used with password-based encryption (PBE)
            // provide password, salt, iteration count for generating PBEKey of fixed-key-size PBE ciphers
            KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount);

            // create a secret (symmetric) key using PBE with MD5 and DES
            key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);

            // construct a parameter set for password-based encryption as defined in the PKCS #5 standard
            AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

            ecipher = Cipher.getInstance(key.getAlgorithm());
            dcipher = Cipher.getInstance(key.getAlgorithm());

            // initialize the ciphers with the given key
            ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);

        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    private static String encrypt(String str) {
        try {
            // encode the string into a sequence of bytes using the named charset
            // storing the result into a new byte array.
            byte[] utf8 = str.getBytes("UTF8");
            byte[] enc = ecipher.doFinal(utf8);

            // encode to base64
            enc = Base64.encode(enc, 0);
            return new String(enc);
        }

        catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    private static String decrypt(String str) {
        try {
            // decode with base64 to get bytes
            byte[] dec = Base64.decode(str.getBytes(), 0);
            byte[] utf8 = dcipher.doFinal(dec);
            // create new string based on the specified charset
            return new String(utf8, "UTF8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }



    //-------------------------
    private View root;
    private EditText tf_text, tf_key;
    private Button btn_encrypt, btn_decrypt;
    private TextView l_output;

    private Global global;

    //--------------------------
    private static Cipher ecipher;
    private static Cipher dcipher;

    private static SecretKey key;

    private static final int iterationCount = 10;

    // 8-byte Salt
    private static byte[] salt = {

            (byte)0xB2, (byte)0x12, (byte)0xD5, (byte)0xB2,

            (byte)0x44, (byte)0x21, (byte)0xC3, (byte)0xC3
    };
}
