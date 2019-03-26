package com.example.napp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private Toolbar mainToolBar;
    private String uid;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String TAG="MainActivity";
        Log.e(TAG, "After Setcontent view");
        mAuth=FirebaseAuth.getInstance();
        Log.e(TAG, "After Mauth view");
       // getSupportActionBar().setTitle("Feeds");
        mainToolBar=(Toolbar)findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolBar);
        getSupportActionBar().setTitle("Napp Portal");
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentuser = mAuth.getCurrentUser();//to get the current user logged in
        Log.e("onStart", "Onstart started");
//        Log.e("Useremail",currentuser.getEmail());
        if(currentuser==null){
            sendtologin();

        }else{
            uid=mAuth.getUid();
            Log.e("Mainactivity", uid);
            mDatabase=FirebaseDatabase.getInstance().getReference("Users").child(uid).child("USN");
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String usn = dataSnapshot.getValue(String.class);
                    Log.e("MainAcitvity", usn);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });








        }

        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //When Menu is pressed then this will be called with menu passed as an object
        getMenuInflater().inflate(R.menu.main_menu,menu); //Inflating the menu
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Called when menu item is clicked
        switch(item.getItemId()){
            case R.id.action_logout: //When Logout is clicked
                logout();
                break;
            case R.id.action_ac_sett:
                Intent profPic = new Intent(this,SetupActivity.class);
                startActivity(profPic);
        }



        return super.onOptionsItemSelected(item);  //retuns bool
    }

    private void logout() {
     mAuth.signOut();
     sendtologin();
    }


    private void sendtologin() {

        Intent LoginIntent=new Intent(MainActivity.this,LoginActivity.class);
        startActivity(LoginIntent);
        finish();
    }


    }

   class UserData{
        String USN;

        public String getUSN() {
            return USN;
        }
    }
