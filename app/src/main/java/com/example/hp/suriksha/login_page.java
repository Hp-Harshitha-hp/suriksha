package com.example.hp.suriksha;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLogTags;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login_page extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth mAuth;
    EditText editTextEmail,editTextpass;
    ProgressBar progressBar;
    private TextView forgotpassword;

    public final static String MESSAGE_KEY ="hey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        mAuth=FirebaseAuth.getInstance();
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextpass = (EditText) findViewById(R.id.editTextpass);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        forgotpassword=(TextView)findViewById(R.id.forgot);
        findViewById(R.id.newuser).setOnClickListener(this);
        findViewById(R.id.buttonSave).setOnClickListener(this);
        findViewById(R.id.forgot).setOnClickListener(this);
    }

    public void userlogin()
    {
        String username = getIntent().getStringExtra("USERNAME");
         String email=editTextEmail.getText().toString().trim();
        String password=editTextpass.getText().toString().trim();
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
            editTextpass.setError("Minimum lenght of password should be 6");
            editTextpass.requestFocus();

            return;

        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful())
                {String idd=editTextEmail.getText().toString().trim();
                    //finish();
                    Intent intent =new Intent(login_page.this,page.class);
                    intent.putExtra("NAME",idd);
                    FirebaseUser firebaseUser = mAuth.getInstance().getCurrentUser();
                    Boolean emailflag = firebaseUser.isEmailVerified();
                    if(emailflag){
                        finish();
                        startActivity(intent);
                    }
             /**iojgerknljieijoegetgetionh**/
                  //  checkEmailVerification();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                } }
        });
            }

    @Override
  /**  protected void onStart(){
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, page.class));
        }}
    @Override
    **/public void onClick(View view)
    {

        switch(view.getId()){
            case R.id.newuser:
                finish();
                startActivity(new Intent(this,register.class));
                break;
            case R.id.forgot:
                finish();
                startActivity(new Intent(this,PasswordActivity.class));

            case R.id.buttonSave:
                userlogin();
                break;
        }
    }
    private void checkEmailVerification() {

        FirebaseUser firebaseUser = mAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();
        if(emailflag){
            finish();
            startActivity(new Intent(login_page.this, page.class));
        }else{
            Toast.makeText(this, "Verify your email", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
        }
    }

}