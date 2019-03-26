package com.example.napp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

//import com.theartofdev.edmodo.cropper.CropImage;
//import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Set;

public class SetupActivity extends AppCompatActivity {
    private ImageButton profimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
//        profimage=findViewById(R.id.imgbtn_prof);
//        profimage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
//                    if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
//                        Toast.makeText(SetupActivity.this,"Permission is Granted", Toast.LENGTH_LONG).show();
//
//                    }else
//                    {
//                        Toast.makeText(SetupActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
//                        finish(); //If no permission, call finish and destry the intent
//                    }
//
//
//                }else{
//                    Toast.makeText(SetupActivity.this,"Already Have permission",Toast.LENGTH_LONG);
//                }
//
//                BringImagePicker();
//
//
//            }
//        });


//
    }

   // private void BringImagePicker() {
//        CropImage.activity()
//                .setGuidelines(CropImageView.Guidelines.ON)
//                .start(this);
}

    //@Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//                Uri resultUri = result.getUri();
//                profimage.setImageURI(resultUri);
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Exception error = result.getError();
//            }
//        }
//    }

