package com.example.napp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

//import com.theartofdev.edmodo.cropper.CropImage;
//import com.theartofdev.edmodo.cropper.CropImageView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SetupActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    TextView tv1, tv2;
    private String TAG="SetupActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        FirebaseApp.initializeApp(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        tv1 = findViewById(R.id.textView2);
        tv2 = findViewById(R.id.textView3);


        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final Query usrdetails = databaseReference.child("Users");
        Log.e(TAG, uid);
        usrdetails.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()) {

                    userData usr = new userData();
                    tv1.setText("Name: "+usr.getUsn(uid)[0]);
                    tv2.setText("USN: "+usr.getUsn(uid)[1]);
                    if(ds.getKey().toString().equals("dgckahckuajh873jhgb")){
                        tv2.setText("Hurray");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}



























class userData {
    private String name;
    private String usn;
    private String uid;

    userData() {
    }

    public userData(String name, String usn, String uid) {
        this.name = name;
        this.usn = usn;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }
    String []val = new String[2];

    public String[] getUsn(String uid) {
        if(uid.equals("OMB2amezyyVEE1bkEFMuiz178dy1")){

            val[0] = new String("Smriti Mandhana");
            val[1] = new String("1NT16IS876");
            return val;
        }else if(uid.equals("n0YifYTrjaZDFRkz9EuhKTNCKUc2")){
            val[0] = new String("Shikhar Dhawan");
            val[1] = new String("1NT16IS979");
            return val;
        }
            val[0] = new String("Unable to Fetch Data");
            val[1] = new String("Unable to Fetch data");
            return val;


    }

    public String getUID() {
        return uid;
    }

}

