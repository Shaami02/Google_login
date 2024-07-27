package com.example.musicapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;


public class MainActivity extends AppCompatActivity {
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
 TextView username,password;
 Button login;
 ImageView g_btn,fb_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (TextView) findViewById(R.id.user);
        password = (TextView) findViewById(R.id.pass);
        login = (Button) findViewById(R.id.btn);
        g_btn = (ImageView) findViewById(R.id.G_btn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().equals("sami") && password.getText().toString().equals("12345")) {
                    Intent intent=new Intent(MainActivity.this,secondActivity.class);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "Login Sucssfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();

                }
            }
        });
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null){
            navigatetosecondActivity();
        }
        g_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singIn();
            }
        });
    }
    void singIn(){
      Intent singInIntent=gsc.getSignInIntent();
      startActivityForResult(singInIntent,1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000){
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                task.getResult(ApiException.class);
                navigatetosecondActivity();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    void navigatetosecondActivity(){
        finish();
        Intent intent=new Intent(MainActivity.this , secondActivity.class);
        startActivity(intent);
    }

}