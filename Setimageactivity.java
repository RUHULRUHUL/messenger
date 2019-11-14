package com.example.finalapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Setimageactivity extends AppCompatActivity {
    private CircleImageView circleImageView;
    private Button skip,saveinfo;
    private EditText name;
    private StorageReference imageref;
    private FirebaseAuth auth;
   private DatabaseReference rotref;
   private String curentuserid;
   private ProgressDialog progressDialog;
   private String phonnumber;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setimageactivity);

        auth=FirebaseAuth.getInstance();
        imageref= FirebaseStorage.getInstance().getReference().child("images_file");

        rotref=FirebaseDatabase.getInstance().getReference().child("persionacount");

        circleImageView=findViewById(R.id.profileid);
        skip=findViewById(R.id.skipbuttonid);

        progressDialog=new ProgressDialog(Setimageactivity.this);

        curentuserid=auth.getCurrentUser().getUid();

        Bundle bundle=getIntent().getExtras();
        if (bundle!=null)
        {
            phonnumber=bundle.getString("phonenumber").toString();
        }








        skip.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

              Intent intent=new Intent(Setimageactivity.this,Nameset.class);
              startActivity(intent);

            }
        });

//        saveinfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String curentuserid=auth.getCurrentUser().getUid();
//
//                String username=name.getText().toString();
//                if (TextUtils.isEmpty(username))
//                {
//                    Toast.makeText(Setimageactivity.this, "plese input name", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    HashMap<String,Object>namehashmap=new HashMap<>();
//                    namehashmap.put("persionname",username);
//
//                    rotref.child(curentuserid).setValue(namehashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//
//                            if (task.isSuccessful())
//                            {
//                                Toast.makeText(Setimageactivity.this, "ok congratulation your information is done", Toast.LENGTH_SHORT).show();
//                                Intent intent=new Intent(Setimageactivity.this,MainActivity.class);
//                                startActivity(intent);
//                            }
//
//                        }
//                    });
//                }
//
//
//
//            }
//        });

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,1);

            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);


        progressDialog.setTitle("upload image");
        progressDialog.setMessage("please wait image uploaded.....");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        if (requestCode==1 && resultCode==RESULT_OK && data!=null)
        {


            final Uri imageuri=data.getData();
            Picasso.with(Setimageactivity.this).load(imageuri).into(circleImageView);

            final StorageReference filepath=imageref.child(curentuserid+".jpg");

            filepath.putFile(imageuri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {


                        filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                String imageurl=uri.toString();
//                                HashMap<String,Object>hashMap=new HashMap<>();
//                                hashMap.put("imageurl",imageurl);


                                rotref.child(curentuserid).child("imageurl").setValue(imageurl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful())
                                        {
                                            progressDialog.dismiss();
                                            Toast.makeText(Setimageactivity.this, "image upload successfully", Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(Setimageactivity.this,Nameset.class);
                                            //intent.putExtra("phonenumber",phonnumber);
                                            startActivity(intent);
                                        }
                                        else {
                                            Toast.makeText(Setimageactivity.this, "error: "+task.getException().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });



                            }
                        });


                }
            });
        }
    }
}
