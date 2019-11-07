package com.example.agalix;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class DisplayActivity extends AppCompatActivity {


    EditText editPDFName;
    Button btn_upload,mShowUploads;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    Button mButtonChooseImage;
    final static int PICK_IMAGE_REQUEST=1;
    private Uri mImageUri;
    int proof;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        editPDFName=(EditText)findViewById(R.id.txt_pdfName);
        btn_upload=(Button)findViewById(R.id.btn_upload);
        mButtonChooseImage=findViewById(R.id.button_choose_image);

        mShowUploads=findViewById(R.id.showUploads);


        storageReference= FirebaseStorage.getInstance().getReference("uploads");
        databaseReference= FirebaseDatabase.getInstance().getReference("");
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPDFFile();
                proof=0;
            }
        });
        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openFileChooser();
                proof=1;

            }
        });
        mShowUploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    public void openFileChooser(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);

    }
    public void selectPDFFile(){
        Intent intent=new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select PDF file"),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            if(proof==0){
                uploadPDFFile(data.getData());
            }
            else{

                uploadImage(data.getData());
            }


        }


    }
    public void uploadPDFFile(Uri data){

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("uploading...");
        progressDialog.show();
        StorageReference reference=storageReference.child("uploads/"+System.currentTimeMillis()+".mp4");




        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uri=taskSnapshot.getStorage().getDownloadUrl();
                while(!uri.isComplete());
                Uri url=uri.getResult();
                UploadPDF uploadPDF =new UploadPDF(editPDFName.getText().toString(),url.toString());
                databaseReference.child(databaseReference.push().getKey()).setValue(uploadPDF);
                Toast.makeText(DisplayActivity.this,"file uploaded",Toast.LENGTH_LONG).show();
                progressDialog.dismiss();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress=(100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                progressDialog.setMessage("Uploaded :"+(int)progress+"%");

            }
        });




    }
    public void uploadImage(Uri data){

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("uploading...");
        progressDialog.show();

        StorageReference reference2=storageReference.child("uploads/"+System.currentTimeMillis()+".jpg");
        reference2.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uri=taskSnapshot.getStorage().getDownloadUrl();
                while(!uri.isComplete());
                Uri url=uri.getResult();
                UploadImage uploadImage=new UploadImage(url.toString());
                databaseReference.child(databaseReference.push().getKey()).setValue(uploadImage);
                Toast.makeText(DisplayActivity.this,"file uploaded",Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress=(100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                progressDialog.setMessage("Uploaded :"+(int)progress+"%");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(DisplayActivity.this,"failed",Toast.LENGTH_LONG).show();
            }
        });
    }


    public void btn_action(View view){
        startActivity(new Intent(getApplicationContext(),View_PDF_Files.class));
    }
}
