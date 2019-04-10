package com.example.napp;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddPostFragment extends Fragment {


    String Storage_Path = "All_Image_Uploads/";

    String Database_Path = "All_Image_Uploads_Database";

    // Creating button.
    Button ChooseButton, UploadButton;

    // Creating EditText of ImageDescription and ImageTitle
    EditText ImageDesc,ImageTitle ;

    // Creating ImageView.
    ImageView SelectImage;

    // Creating URI.
    Uri FilePathUri;

    // Creating StorageReference and DatabaseReference object.
    StorageReference storageReference;
    DatabaseReference databaseReference;

    // Image request code for onActivityResult() .
    int Image_Request_Code = 7;

    ProgressDialog progressDialog ;

    Spinner spin;
    String receivedDept,uid;

    String[] departments = { "Select Department", "Water", "Infrastructure", "Management", "Cleaning",};

    public AddPostFragment() {
        // Required empty public constructor
    }


     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_add_post, container, false);


         FirebaseApp.initializeApp(getActivity());

         // Assign FirebaseStorage instance to storageReference.
         storageReference = FirebaseStorage.getInstance().getReference();

         // Assign FirebaseDatabase instance with root database name.
         databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);

         //Assign ID'S to button.
         ChooseButton = (Button)v.findViewById(R.id.ButtonChooseImage);
         UploadButton = (Button)v.findViewById(R.id.ButtonUploadImage);

         //Assigning ID to ImageTitle
         ImageTitle = (EditText)v.findViewById(R.id.imageTitle);

         // Assign ID's to ImageDescription
         ImageDesc = (EditText)v.findViewById(R.id.imageDescription);

         // Assign ID'S to image view.
         SelectImage = (ImageView)v.findViewById(R.id.ShowImageView);

         // Assigning Id to ProgressDialog.
         progressDialog = new ProgressDialog(getActivity());


         spin = v.findViewById(R.id.spinnerSelectDept);

         spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

             @Override
             public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                 receivedDept = departments[position];
                 Toast.makeText(getActivity().getApplicationContext(),departments[position] , Toast.LENGTH_LONG).show();
             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {
                 Toast.makeText(getActivity(), "No item is selected", Toast.LENGTH_SHORT).show();

             }
         });


         //Creating the ArrayAdapter instance having the country list
         ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,departments);
         aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         //Setting the ArrayAdapter data on the Spinner
         spin.setAdapter(aa);

         // Adding click listener to Choose image button.
         ChooseButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 // Creating intent.
                 Intent intent = new Intent();

                 // Setting intent type as image to select image from phone storage.
                 intent.setType("image/*");
                 intent.setAction(Intent.ACTION_GET_CONTENT);
                 startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);

             }
         });


         // Adding click listener to Upload image button.
         UploadButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 // Calling method to upload selected image on Firebase storage.
                 UploadImageFileToFirebaseStorage();

             }
         });





         return v;
    }

    @Override
    public
    void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {

                // Getting selected image into Bitmap.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), FilePathUri);

                // Setting up bitmap selected image into ImageView.
                SelectImage.setImageBitmap(bitmap);

                // After selecting image change choose button above text.
                ChooseButton.setText("Image Selected");

            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    // Creating Method to get the selected image file Extension from File Path URI.
    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getActivity().getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }

    // Creating UploadImageFileToFirebaseStorage method to upload image on storage.
    public void UploadImageFileToFirebaseStorage() {

        // Checking whether FilePathUri Is empty or not.
        if (FilePathUri != null) {

            // Setting progressDialog Title.
            progressDialog.setTitle("Image is Uploading...");

            // Showing progressDialog.
            progressDialog.show();

            // Creating second StorageReference.
            StorageReference storageReference2nd = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));

            // Adding addOnSuccessListener to second StorageReference.
            storageReference2nd.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            // Getting image description from EditText and store into string variable.
                            String TempImageTitle = ImageTitle.getText().toString().trim();
                            String TempImageDesc = ImageDesc.getText().toString().trim();
                            String TempDept = receivedDept.trim();
                            String TempUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                            // Hiding the progressDialog after done uploading.
                            progressDialog.dismiss();

                            // Showing toast message after done uploading.
                            Toast.makeText(getActivity().getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();



                            // Getting image upload ID.
                            String ImageUploadId = databaseReference.push().getKey();
                            ImageUploadInfo imageUploadInfo = new ImageUploadInfo(TempUid,TempImageTitle,TempDept,TempImageDesc, taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());

                            // Adding image upload id s child element into databaseReference.
                            databaseReference.child(ImageUploadId).setValue(imageUploadInfo);
                        }
                    })
                    // If something goes wrong .
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                            // Hiding the progressDialog.
                            progressDialog.dismiss();

                            // Showing exception erro message.
                            Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })

                    // On progress change upload time.
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            // Setting progressDialog Title.
                            progressDialog.setTitle("Image is Uploading...");

                        }
                    });
        }
        else {

            Toast.makeText(getActivity(), "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
    }







}
