package com.example.hp.suriksha;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.hp.suriksha.login_page;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.google.firebase.auth.AuthResult;
public class page extends AppCompatActivity {
    private String email;
    EditText editText,phone,editText3;
    Spinner spinner, spinner2;
    TextView mailid;
    Button button;
    Toolbar toolbar;
    FirebaseAuth mAuth;
    FirebaseUser mail;
    DatabaseReference databaseArtists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        mAuth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.toolbar);
        editText = (EditText) findViewById(R.id.editText);
        phone=(EditText)findViewById(R.id.phone);
        mailid =(TextView)findViewById(R.id.mailid);
        editText3 =(EditText)findViewById(R.id.editText3);
        // username = (EditText) findViewById(R.id.username);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
    /**  barre**/  mailid.setText(getIntent().getStringExtra("NAME"));

        button = (Button) findViewById(R.id.button);

        mail = mAuth.getCurrentUser();
        databaseArtists = FirebaseDatabase.getInstance().getReference("artists");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { addArtist(); }
        });
    }
 /** protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, login_page.class));

        }
    }**/

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuLogout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, login_page.class));
                break;
        }
        return true;

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    private void addArtist() {
        String name = editText.getText().toString().trim();
        int name1=Integer.parseInt(name);
        String genre = spinner.getSelectedItem().toString();
        String genre2 = spinner2.getSelectedItem().toString();
        String time = editText3.getText().toString().trim();
        String no1=phone.getText().toString().trim();
        int flag=0;
        String gmail=mailid.getText().toString().trim();
        if (!TextUtils.isEmpty(name)) {
            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            String id = databaseArtists.push().getKey();
            String got=getIntent().getStringExtra("NAME");
            //creating an Artist Object
            Artist artist = new Artist(name, genre, genre2, time,no1,got);
            //Saving the Artist
            databaseArtists.child(id).setValue(artist);
            //setting edittext to blank again
            editText.setText("");

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(gmail).matches()) {
                mailid.setError("Please enter a valid email");
                mailid.requestFocus();
                return;
            }

            if (no1.length() != 10) {
                phone.setError("check your phone number!!!");
                phone.requestFocus();
                return;
            }
            if ( name1>7) {
                editText.setError("Maximum 7 persons per auto !");
                editText.requestFocus();
                return;
            }
           /** if (gmail.isEmpty()) {

                mailid.setError("Email is required");
                mailid.requestFocus();
                return;
            }**/
            if (no1.isEmpty()) {
                phone.setError("Phone number required is required");
                phone.requestFocus();
                return;
            }
            if (name.isEmpty()) {
                editText.setError("Enter the number of persons");
                editText.requestFocus();
                return;
            }
            //displaying a success toast
            Toast.makeText(this, "Auto Booked", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter the details", Toast.LENGTH_LONG).show();
        }}}