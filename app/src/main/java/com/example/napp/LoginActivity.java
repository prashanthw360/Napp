package com.example.napp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    //declaring variables of login page
    private EditText LoginEmailText;
    private EditText LoginPassText;
    private Button LoginBtn;

    //mAuth is an object of class FirebaseAuth
    private FirebaseAuth mAuth;

    private ProgressBar loginProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance();

        //fetching contents of widgets used in .xml file to .java file of LoginActivity
        LoginEmailText = (EditText)findViewById(R.id.login_email);
        LoginPassText = (EditText)findViewById(R.id.login_password);
        LoginBtn = (Button)findViewById(R.id.login_btn);
        loginProgress = (ProgressBar)findViewById(R.id.login_progress);

        //if login button is pressed then Onclick function is called

    }

    @Override
    protected void onStart() {
        Log.e("LoginActivity","In Here");
        super.onStart();

        //fetching the current user using getCurrentUser() function
        FirebaseUser currentUser= mAuth.getCurrentUser();
       // Log.e("Login", currentUser.getEmail());

         if(currentUser != null){ //pro: Not equal to actually
        //if a user is logged in then Main Activity should be loaded
         sendToMain();
           }else{
             LoginBtn.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {


                     //copying email and password to string type variables
                     String loginEmail = LoginEmailText.getText().toString();
                     String loginPass = LoginPassText.getText().toString();

                     //if users doesn't enter data and directly press login button then this part of code checks it
                     if(!TextUtils.isEmpty(loginEmail) && !TextUtils.isEmpty(loginPass)){
                         loginProgress.setVisibility(View.VISIBLE);//initially Progress bar should be invisible but once we get to know that details has been entered then it should become visible

                         //here we check whether details entered by users are correct or not
                         //addOnCompleteListener checks whether the process is complete or not
                         mAuth.signInWithEmailAndPassword(loginEmail,loginPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                             @Override
                             public void onComplete(@NonNull Task<AuthResult> task) {

                                 if(task.isSuccessful()){

                                     sendToMain();//if entered details are correct then call sendToMain function to direct the user to MainActivity
                                 }
                                 else{

                                     String errorMessage = task.getException().getMessage();//this statement fetches what kind of error is generated
                                     Toast.makeText(LoginActivity.this, "Error : " +errorMessage, Toast.LENGTH_SHORT).show();
                                 }

                                 loginProgress.setVisibility(View.INVISIBLE);//after the task is complete the progress bar should be invisible
                             }
                         });
                     }
                 }
             });
         }

    }

    private void sendToMain() {
        Intent MainIntent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(MainIntent);
        finish();
    }
}
