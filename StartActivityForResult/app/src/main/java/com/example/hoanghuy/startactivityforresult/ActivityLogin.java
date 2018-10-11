package com.example.hoanghuy.startactivityforresult;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityLogin extends AppCompatActivity implements View.OnClickListener {
    EditText edtUser;
    EditText edtPass;
    Button btnLogin2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtPass = (EditText)findViewById(R.id.edtPass);
        edtUser = (EditText)findViewById(R.id.edtUser);
        btnLogin2 = findViewById(R.id.btnLogin2);
        btnLogin2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String str1 = edtUser.getText().toString();
        String str2 = edtPass.getText().toString();
        if("".equals(str1) || "".equals(str2)){
            Toast.makeText(ActivityLogin.this,"Không được để trống ",Toast.LENGTH_SHORT).show();
            return;
        }
        System.out.println("username: "+str1);
        System.out.println("password: "+str2);
        if("Hoanghuy".equals(str1) && "123".equals(str2)){
            if ("Hoanghuy".equals(str1)){
                System.out.println("username: "+str1);
            }
            if("123".equals(str2)){
                System.out.println("pass:"+str2);
            }
            Intent intent = new Intent(this,MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("username",str1);
            bundle.putString("password",str2);
            intent.putExtra("user",bundle);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }else{
            Intent intent = new Intent();
            setResult(Activity.RESULT_CANCELED,intent);
            finish();
        }
    }
}
