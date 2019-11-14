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
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class Regester extends AppCompatActivity {

    //private EditText email,password;
    private Button create;
    private Button phoneacountcreate;
    private ProgressDialog progressDialog;
    private DatabaseReference rotref;
    private FirebaseAuth auth;
    private EditText inputphonenumber,inputveryficode;
    private Button sendveryfication,veryfi;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallback;
    private String verificationid;
    private String phonenumber;
    private String curentuserid;


    private PhoneAuthProvider.ForceResendingToken mforceResendingToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regester);

        auth=FirebaseAuth.getInstance();
        //rotref=FirebaseDatabase.getInstance().getReference().child("persionacount");

        inputphonenumber=findViewById(R.id.inputphonenumberid);
        inputveryficode=findViewById(R.id.inputveryficode);

        sendveryfication=findViewById(R.id.sendveryficationid);
        veryfi=findViewById(R.id.veryfibuttonid);
        progressDialog=new ProgressDialog(Regester.this);

//
//        email=findViewById(R.id.emailid);
//        password=findViewById(R.id.password);
//        create=findViewById(R.id.myacountcreateid);
//        phoneacountcreate=findViewById(R.id.phoneacountcreateid);

//        phoneacountcreate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//
//            {
//                Intent intent=new Intent(Regester.this,Phoneveryvifyacount.class);
//                startActivity(intent);
//            }
//        });

//        create.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v) {
//
//
//                String persionemailid=email.getText().toString();
//                String persiopasswordid=password.getText().toString();
//
//
//
//
//                auth.createUserWithEmailAndPassword(persionemailid,persiopasswordid)
//                        .addOnCompleteListener(new OnCompleteListener<AuthResult>(){
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful())
//                                {
//
//                                   // progressDialog.dismiss();
//                                    Toast.makeText(Regester.this,"sing in successfully",Toast.LENGTH_SHORT).show();
//                                    Intent intent=new Intent(Regester.this,MainActivity.class);
//                                    startActivity(intent);
//
//                                }
//                                else {
//                                    String error=task.getException().toString();
//                                    progressDialog.dismiss();
//                                    Toast.makeText(Regester.this,"error"+error,Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//
//            }
//        });

        sendveryfication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String countrycode="+88";

               phonenumber=countrycode+inputphonenumber.getText().toString();
                if (TextUtils.isEmpty(phonenumber))
                {
                    Toast.makeText(Regester.this, "plese enter phone ", Toast.LENGTH_SHORT).show();

                }

                else
                    {
                    progressDialog.setTitle("verification");
                    progressDialog.setMessage("your number verification");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            phonenumber,
                            60,
                            TimeUnit.SECONDS,
                            Regester.this,
                            mcallback);
                }

            }
        });

        mcallback=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {


                sininwithphoneauthenticationcradential(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e)
            {

                Toast.makeText(Regester.this, "vailed country code and phone re entre", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                inputphonenumber.setVisibility(View.VISIBLE);
                sendveryfication.setVisibility(View.VISIBLE);

                inputveryficode.setVisibility(View.INVISIBLE);
                veryfi.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken)
            {
                super.onCodeSent(s, forceResendingToken);



                verificationid=s;
                mforceResendingToken=forceResendingToken;
                progressDialog.dismiss();
                Toast.makeText(Regester.this, "code has been sent plese cheack verifi code", Toast.LENGTH_SHORT).show();
                inputphonenumber.setVisibility(View.INVISIBLE);
                sendveryfication.setVisibility(View.INVISIBLE);

                inputveryficode.setVisibility(View.VISIBLE);
                veryfi.setVisibility(View.VISIBLE);

            }
        };

        veryfi.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {


//                sendveryfication.setVisibility(View.INVISIBLE);
//                inputphonenumber.setVisibility(View.INVISIBLE);

                String inputveryfinumber=inputveryficode.getText().toString();

                if (TextUtils.isEmpty(inputveryfinumber))
                {
                    Toast.makeText(Regester.this, "plese enter code ", Toast.LENGTH_SHORT).show();
                }
                else {

                    progressDialog.setTitle("verification");
                    progressDialog.setMessage("your code verifi");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationid,inputveryfinumber);
                    sininwithphoneauthenticationcradential(credential);
                }



            }
        }    );


        }

    private void sininwithphoneauthenticationcradential(PhoneAuthCredential phoneAuthCredential)
    {

        auth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                           progressDialog.dismiss();
                           curentuserid=auth.getCurrentUser().getUid();
                            Toast.makeText(Regester.this, "succesfull ", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(Regester.this,Setpasswordphoneactivity.class);
                            intent.putExtra("phonenumber",phonenumber);
                            startActivity(intent);
                            finish();




                        }
                        else {
                            progressDialog.dismiss();
                            String error=task.getException().toString();
                            Toast.makeText(Regester.this, "error: "+error, Toast.LENGTH_SHORT).show();
                        }

                    }
                });


    }


}
