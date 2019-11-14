package com.example.finalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Setpasswordphoneactivity extends AppCompatActivity {
    private EditText inputpassword;
    private Button passwordsave;
    private FirebaseAuth auth;
    private DatabaseReference rotref;
    private ProgressDialog progressDialog;
    private String userphonenumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setpasswordphoneactivity);
        auth=FirebaseAuth.getInstance();
        rotref= FirebaseDatabase.getInstance().getReference().child("persionacount");

        inputpassword=findViewById(R.id.inputpasswordid);
        passwordsave=findViewById(R.id.savepasswordid);

        Bundle bundle=getIntent().getExtras();
        if (bundle !=null)
        {
           userphonenumber= bundle.getString("phonenumber").toString();


        }

        progressDialog=new ProgressDialog(Setpasswordphoneactivity.this);


        passwordsave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                final String curentuserid=auth.getCurrentUser().getUid();
                String  password=inputpassword.getText().toString();
                if (TextUtils.isEmpty(password))
                {
                    Toast.makeText(Setpasswordphoneactivity.this, "plese enter password", Toast.LENGTH_SHORT).show();
                }
                else{

                    progressDialog.setTitle("save password");
                    progressDialog.setMessage("please wait save your password");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

//                    HashMap<String,Object>hashMap=new HashMap<>();
//                    hashMap.put("password",password);
                    rotref.child(curentuserid).child("acountpassword").setValue(password).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if (task.isSuccessful())
                            {

                                rotref.child(curentuserid).child("userphonenumber").setValue(userphonenumber).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        progressDialog.dismiss();

                                        Toast.makeText(Setpasswordphoneactivity.this, "password save next step", Toast.LENGTH_SHORT).show();

                                        Intent intent=new Intent(Setpasswordphoneactivity.this,Setimageactivity.class);
                                        intent.putExtra("phonenumber: ",userphonenumber);
                                        startActivity(intent);

                                    }
                                });


                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(Setpasswordphoneactivity.this, "error"+task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


                }

            }
        });


    }
}
