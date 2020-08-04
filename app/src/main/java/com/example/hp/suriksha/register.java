package com.example.hp.suriksha;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class register extends AppCompatActivity implements View.OnClickListener{
    ProgressBar progressBar;
    ImageView imageView;
    EditText editTextEmail,editTextpass,reg;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextpass = (EditText) findViewById(R.id.editTextpass);
        reg=(EditText)  findViewById(R.id.reg);
                progressBar = (ProgressBar) findViewById(R.id.progressbar);

        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.buttonSave).setOnClickListener(this);
        findViewById(R.id.already).setOnClickListener(this);
    }

    private void registerUser()
    {
        String email=editTextEmail.getText().toString().trim();
        String password=editTextpass.getText().toString().trim();
        String reg3=reg.getText().toString().trim();
        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextpass.setError("Password is required");
            editTextpass.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editTextpass.setError("Minimum length of password should be 6");
            editTextpass.requestFocus();
            return;
        }progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override

            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    finish();
                    sendEmailVerification();
                }
                else
                {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.buttonSave:
                registerUser();
                break;
            case R.id.already:
                finish();
                startActivity(new Intent(this,login_page.class));
                break;
        }

    }
    private void checkEmailVerification() {

        FirebaseUser firebaseUser = mAuth.getInstance().getCurrentUser();

        Boolean emailflag = firebaseUser.isEmailVerified();
        //startActivity(new Intent(register.this, page.class));


        if(emailflag){
            finish();
            //startActivity(new Intent(register.this, page.class));
        }else{
            Toast.makeText(this, "Verify your email", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
        }
    }
    private void sendEmailVerification(){
        FirebaseUser firebaseUser=mAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(register.this,"Successfully registered , Verification mail sent, Please verify and sign in again",Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                        finish();
                        startActivity(new Intent(register.this, login_page.class));
                    }
                    else{
                        Toast.makeText(register.this, "Verification mail hasn't been sent", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }}}