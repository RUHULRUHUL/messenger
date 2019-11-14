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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.widget.Toast.LENGTH_SHORT;

public class Profileedit extends AppCompatActivity {
    private CircleImageView circleImageView;
    private EditText name,abount,phone;
    private Button button;
    private int gallaryintent=1;
    private FirebaseAuth auth;
    private DatabaseReference rotref;
    private StorageReference profileimageref;
    private String curentuserid;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileedit);
        circleImageView=findViewById(R.id.profileimagesettings);
        name=findViewById(R.id.nameedittextid);
        abount=findViewById(R.id.aboutedittextid);
        phone=findViewById(R.id.phoneedittextid);
        button=findViewById(R.id.updatebuttonid);

        progressDialog=new ProgressDialog(this);


        auth=FirebaseAuth.getInstance();

        rotref=FirebaseDatabase.getInstance().getReference().child("persionacount");
        profileimageref= FirebaseStorage.getInstance().getReference().child("images_file");

        curentuserid=auth.getCurrentUser().getUid();

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadimageprofile();


            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateinformation();



            }
        });

        retriveuserinformation();
    }

    private void updateinformation() {

        progressDialog.setTitle("information update");
        progressDialog.setMessage("waite sometime");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();

        String persionanme=name.getText().toString();
        String persionabout=abount.getText().toString();
        String persionphone=phone.getText().toString();



        if (TextUtils.isEmpty(persionanme) && TextUtils.isEmpty(persionabout) && TextUtils.isEmpty(persionphone))
        {
            Toast.makeText(Profileedit.this, "plese input field", LENGTH_SHORT).show();
            progressDialog.dismiss();

        }



        HashMap<String,Object>information=new HashMap<>();

        information.put("persionname",persionanme);
        information.put("persionphone",persionphone);
        information.put("persionabount",persionabout);



       // Persionprofileinformationdata persionprofileinformationdata=new Persionprofileinformationdata(persionanme,persionabout,persionphone);

        rotref.child(curentuserid).setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful())
                {
                    Toast.makeText(Profileedit.this, "data saved", LENGTH_SHORT).show();
                    Intent intent=new Intent();
                    progressDialog.dismiss();
                    startActivity(intent);




                }
                else {
                    String error=task.getException().toString();
                    progressDialog.dismiss();
                    Toast.makeText(Profileedit.this, " "+error, LENGTH_SHORT).show();
                }

            }
        });
    }

    private void uploadimageprofile() {

        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,gallaryintent);
    }

    private void retriveuserinformation() {

        rotref.child(curentuserid).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if ((dataSnapshot.exists())&& (dataSnapshot.hasChild("persionname")) && ( dataSnapshot.hasChild("imageurl")))

                {

                    String retrivename=dataSnapshot.child("persionname").getValue().toString();
                    String retrivephone=dataSnapshot.child("persionphone").getValue().toString();
                    String retriveabout=dataSnapshot.child("persionabount").getValue().toString();
                    String retriveimage=dataSnapshot.child("imageurl").getValue().toString();

                    name.setText(retrivename);
                    abount.setText(retriveabout);
                    phone.setText(retrivephone);
                    Picasso.with(Profileedit.this).load(retriveimage).into(circleImageView);

//                    Intent intent=new Intent(Settings.this, MainActivity.class);
//                    startActivity(intent);

                    Toast.makeText(Profileedit.this,"seccessfully update",Toast.LENGTH_SHORT).show();



                }
                else if(dataSnapshot.hasChild("persionname") && dataSnapshot.hasChild("persionabount"))
                {
                    //setting update information
                    String retrivename=dataSnapshot.child("persionname").getValue().toString();
                    String retrivephone=dataSnapshot.child("persionphone").getValue().toString();
                    String retriveabout=dataSnapshot.child("persionabount").getValue().toString();
                    //String retriveimage=dataSnapshot.child("imageurl").getValue().toString();

                    name.setText(retrivename);
                    abount.setText(retriveabout);
                    phone.setText(retrivephone);


                }
                else {
                    Toast.makeText(Profileedit.this,"your data is not exist",Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode==gallaryintent && resultCode==RESULT_OK && data!=null)
        {
            Uri uri=data.getData();
            Picasso.with(Profileedit.this).load(uri).into(circleImageView);
            final StorageReference filepath=profileimageref.child(curentuserid+ ".jpg");


            filepath.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            String imageurl=uri.toString();

                            rotref.child(curentuserid).child("imageurl").setValue(imageurl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(Profileedit.this, "successfully ", LENGTH_SHORT).show();
                                    }
                                    else {
                                        String error=task.getException().toString();
                                        Toast.makeText(Profileedit.this, ""+error, LENGTH_SHORT).show();
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
