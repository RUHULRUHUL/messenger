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

import java.util.concurrent.TimeUnit;

public class Phoneveryvifyacount extends AppCompatActivity {

    private Button sendveryfication,veryfi;
    private EditText inputphonenumber,inputveryficode;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private String phoneNumber;

    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private String curentuserid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phoneveryvifyacount);
        sendveryfication=findViewById(R.id.sendveryfiid);
        veryfi=findViewById(R.id.sendveryfiid);
        mAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(Phoneveryvifyacount.this);

        inputphonenumber=findViewById(R.id.phonenumberid);
        inputveryficode=findViewById(R.id.veryficodebackid);

        sendveryfication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.setMessage("wait sent code");
                progressDialog.setTitle("Phone verification");
               progressDialog.setCanceledOnTouchOutside(false);
               progressDialog.show();

                 phoneNumber=inputphonenumber.getText().toString();


                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        phoneNumber,        // Phone number to verify
                        60,                 // Timeout duration
                        TimeUnit.SECONDS,   // Unit of timeout
                        Phoneveryvifyacount.this,

                        // Activity (for callback binding)
                        callbacks
                         );        // OnVerificationStateChangedCallbacks



            }
        });

        veryfi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                String veryficode=inputveryficode.getText().toString();

                if (!TextUtils.isEmpty(veryficode))
                {
                    progressDialog.setMessage("varifaying...");
                    progressDialog.setTitle("number verification");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, veryficode);
                    signInWithPhoneAuthCredential(credential);

                }


            }
        });

        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                signInWithPhoneAuthCredential(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e)
            {

                inputphonenumber.setVisibility(View.VISIBLE);
                sendveryfication.setVisibility(View.VISIBLE);

                inputveryficode.setVisibility(View.INVISIBLE);
                veryfi.setVisibility(View.INVISIBLE);




            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {



                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
                Toast.makeText(Phoneveryvifyacount.this, "sent code chack", Toast.LENGTH_SHORT).show();
                inputphonenumber.setVisibility(View.INVISIBLE);
                sendveryfication.setVisibility(View.INVISIBLE);

                inputveryficode.setVisibility(View.VISIBLE);
                veryfi.setVisibility(View.VISIBLE);

                // ...
            }
        };
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {

                            progressDialog.dismiss();

                            curentuserid=mAuth.getCurrentUser().getUid();

                            Toast.makeText(Phoneveryvifyacount.this, "seccessfully", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(Phoneveryvifyacount.this,Setpasswordphoneactivity.class);
                            //intent.putExtra("phonenumber",phoneNumber);
                            startActivity(intent);
                            finish();




                            // ...
                        } else {

                            Toast.makeText(Phoneveryvifyacount.this, "error", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}
