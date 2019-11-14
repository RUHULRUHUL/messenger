package com.example.finalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.Login;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

public class Loging extends AppCompatActivity {

    private  EditText inputphonenumber,inputpassword;
    private Button userlogingbutton;
    private SignInButton signInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private String useremailaccount;
    private String phone,password;
    private LoginButton loginButton;
    private CallbackManager callbackManager;



    private FirebaseAuth auth;

    private DatabaseReference rotref;
    private ProgressDialog progressDialog;
   // private FirebaseUser currentUser;
    private String curentuserid;
    private String inputphone,inputpassworduser;
    private CallbackManager mCallbackManager;
    private Button joingmail;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loging);

        auth=FirebaseAuth.getInstance();


        //signInButton=findViewById(R.id.singinbuttonid);
        progressDialog=new ProgressDialog(Loging.this);



        joingmail=findViewById(R.id.gmailaccountjoinid);

        rotref=FirebaseDatabase.getInstance().getReference().child("persionacount");

        joingmail.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Loging.this,Gmailaccountactivity.class);
                startActivity(intent);



            }
        });



        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.facebookloggingid);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                progressDialog.setTitle("facebook account logging");
                progressDialog.setMessage("please wait logging.. ");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                progressDialog.dismiss();


            }

            @Override
            public void onError(FacebookException error) {
                progressDialog.dismiss();
                Toast.makeText(Loging.this, "error: "+error.toString(), Toast.LENGTH_SHORT).show();

            }
        });








//        userlogingbutton.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view) {
//                 phone ="+88"+inputphonenumber.getText().toString().trim();
//                 password = inputpassword.getText().toString();
//
//              if (TextUtils.isEmpty(phone))
//              {
//                  Toast.makeText(Loging.this, "pleae input phone", Toast.LENGTH_SHORT).show();
//              }
//              else if (TextUtils.isEmpty(password))
//              {
//                  Toast.makeText(Loging.this, "please input password", Toast.LENGTH_SHORT).show();
//              }
//              else {
//
//
//                  Toast.makeText(Loging.this, "ok let do work process : ", Toast.LENGTH_SHORT).show();
//
//                  rotref.child(phone).addValueEventListener(new ValueEventListener() {
//                      @Override
//                      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                          if (dataSnapshot.exists())
//                          {
//                              if (dataSnapshot.hasChild("userphonenumber") && dataSnapshot.hasChild("acountpassword"))
//                              {
//                                  String userphone=dataSnapshot.child("userphonenumber").getValue().toString();
//                                  String userpassword=dataSnapshot.child("acountpassword").getValue().toString();
//
//                                  if (phone.equals(userphone))
//                                  {
//                                      if (password.equals(userpassword))
//                                      {
//                                          Toast.makeText(Loging.this, "successfully logging ", Toast.LENGTH_SHORT).show();
//                                          Intent intent=new Intent(Loging.this,MainActivity.class);
//                                          startActivity(intent);
//                                      }
//                                  }
//                              }
//                          }
//
//                      }
//
//                      @Override
//                      public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                      }
//                  });
//
//
//
//
//
//
//              }
//            }
//        });



//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();

//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

//        signInButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                signIn();
//            }
//        });

//        createacount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent=new Intent(Loging.this, Regester.class);
//                startActivity(intent);
//
//            }
//        });








    }




    private void handleFacebookAccessToken(AccessToken token) {
        //Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            progressDialog.dismiss();

                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = auth.getCurrentUser();
                            String uid=auth.getCurrentUser().getUid().toString();

                            String name=user.getDisplayName().toString();
                            String imageurl=user.getPhotoUrl().toString();

                            HashMap<String,Object>facebookinfo=new HashMap<>();
                            facebookinfo.put("username",name);
                            facebookinfo.put("imageurl",imageurl);

                            rotref.child(uid).setValue(facebookinfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful())
                                    {
                                        progressDialog.dismiss();
                                        Toast.makeText(Loging.this, "logging completed", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(Loging.this,MainActivity.class);
                                        startActivity(intent);
                                    }

                                }
                            });













                            //updateUI(user);
                        }
                        else {
                            // If sign in fails, display a message to the user.
                           // Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(Loging.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }










//    private void signIn() {
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data)
//    {
//
//
//        super.onActivityResult(requestCode, resultCode, data);
//
//
//
//
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        progressDialog.setTitle("google singin...");
//
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();
//
//        if (requestCode == RC_SIGN_IN) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                 useremailaccount=account.getEmail().toString();
//
//                progressDialog.setMessage(" "+useremailaccount);
//
//                firebaseAuthWithGoogle(account);
//            } catch (ApiException e) {
//
//            }
//        }
//        else {
//
//        }
//    }








//  private void firebaseAuthWithGoogle(final GoogleSignInAccount acct)
//   {
//
//        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
//        auth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful())
//                        {
//
//
//
//
//                             //curentuserid=auth.getCurrentUser().getUid();
//                             progressDialog.dismiss();
//
//                            Toast.makeText(Loging.this, "successfull", Toast.LENGTH_SHORT).show();
//                            Intent intent=new Intent(Loging.this,MainActivity.class);
//                            startActivity(intent);
//
//
//
//                        } else {
//                            progressDialog.dismiss();
//
//                        }
//
//
//                    }
//                });
//    }











}
