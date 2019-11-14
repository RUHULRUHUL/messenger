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

public class Nameset extends AppCompatActivity {
    private EditText inputname;
    private Button savebutton;
    private FirebaseAuth auth;
    private DatabaseReference rotref;
    private String curentuserid;
    private ProgressDialog progressDialog;
    private String phonenumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nameset);
        auth=FirebaseAuth.getInstance();
        rotref= FirebaseDatabase.getInstance().getReference().child("persionacount");
        curentuserid=auth.getCurrentUser().getUid();

        inputname=findViewById(R.id.userinputid);
        savebutton=findViewById(R.id.savebuttonid);

        Bundle bundle=getIntent().getExtras();
        if (bundle!=null)
        {
            phonenumber=bundle.getString("").toString();
        }

        progressDialog=new ProgressDialog(Nameset.this);

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String curentuserid=auth.getCurrentUser().getUid();

                String username=inputname.getText().toString();
                if (TextUtils.isEmpty(username))
                {
                    Toast.makeText(Nameset.this, "plese input name", Toast.LENGTH_SHORT).show();
                }
                else {
//                    HashMap<String,Object> namehashmap=new HashMap<>();
//                    namehashmap.put("persionname",username);

                    progressDialog.setTitle("user name save");
                    progressDialog.setMessage("please wait your name save...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    rotref.child(curentuserid).child("username").setValue(username).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful())
                            {
                                progressDialog.dismiss();
                                Toast.makeText(Nameset.this, "ok congratulation your information is done", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(Nameset.this,MainActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(Nameset.this, "error"+task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }

            }
        });


    }
}
