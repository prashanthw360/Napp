package com.example.napp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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
  /*  ImageView ig;
    Integer REQUEST_CAMERA=1, SELECT_FILE=0;*/

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

        BottomNavigationView navigationView = findViewById(R.id.nav);

        final HomeFragment homeFragment= new HomeFragment();
        final AddPostFragment addPostFragment = new AddPostFragment();
        final MyPostsFragment myPostsFragment = new MyPostsFragment();


      /*  ig=(ImageView)findViewById(R.id.newPost);

        private void SelectImage(){
            final CharSequence[] items= {"Camera","Gallery","Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Add Image");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(items[i].equals("Camera")){
                        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent,REQUEST_CAMERA);
                    }else if(items[i].equals("Gallery")){
                        Intent intent=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        startActivityForResult(intent.createChooser(intent,"Select File"),SELECT_FILE);

                    }else if(items[i].equals("Cancel")){
                        dialogInterface.dismiss();
                    }
                }
            });
        }

        */

            navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                   int id = menuItem.getItemId();
                   if(id == R.id.Home){
                       setFragment(homeFragment);
                       return true;
                   }
                    else if(id == R.id.AddPost){
                        setFragment(addPostFragment);
                        return true;
                    }
                    else if(id == R.id.MyPosts){
                        setFragment(myPostsFragment);
                        return true;
                    }
                    return false;
                }
            });

            navigationView.setSelectedItemId(R.id.Home);
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();
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
