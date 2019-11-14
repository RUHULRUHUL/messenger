package com.example.finalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {
    private CircleImageView circleImageView;
    private TextView name,abount;
    private Button button,sendrequest,declinecancilebutton;
    private  String senderid,curentstate;

    private FirebaseAuth auth;
    private DatabaseReference rotref,chatrequestref,contact;

    private String reciverid;
   // private Toolbar toolbar;
    private TabLayout tabLayout;


    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        auth=FirebaseAuth.getInstance();
        //toolbar=findViewById(R.id.toolbarid);



        tabLayout=findViewById(R.id.tabid);
        viewPager=findViewById(R.id.viewpagerid);


        Profiletabaccesser profiletabaccesser=new Profiletabaccesser(getSupportFragmentManager());

        viewPager.setAdapter(profiletabaccesser);
        tabLayout.setupWithViewPager(viewPager);
















        senderid=auth.getCurrentUser().getUid();
        try {

            reciverid=getIntent().getExtras().get("reciverid").toString();

        }catch (Exception e)
        {
            Toast.makeText(this, "error: "+e.toString(), Toast.LENGTH_SHORT).show();
        }

        curentstate="new";

        chatrequestref= FirebaseDatabase.getInstance().getReference().child("friendrequestsent");
        contact= FirebaseDatabase.getInstance().getReference().child("contact_list");
        rotref= FirebaseDatabase.getInstance().getReference().child("persionacount");





        button=findViewById(R.id.editprofilebuttonid);
        sendrequest=findViewById(R.id.sendrequestid);
        declinecancilebutton=findViewById(R.id.declinebuttonid);
        circleImageView=findViewById(R.id.circleimageid);
        name=findViewById(R.id.profilenameid);
        //abount=findViewById(R.id.statusid);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Profile.this,Profileedit.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.profilemenu,menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==R.id.joinmessege)
        {
            Intent intent=new Intent(Profile.this,Messegecontactactivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        rotref.child(reciverid).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists() && (dataSnapshot.hasChild("imageurl")))
                {
                    String username=dataSnapshot.child("username").getValue().toString();
                    String userimage=dataSnapshot.child("imageurl").getValue().toString();

                    name.setText(username);
                    Picasso.with(Profile.this).load(userimage).into(circleImageView);
                    manageacount();


                }
                else{

                    String username=dataSnapshot.child("username").getValue().toString();
                    name.setText(username);
                    manageacount();


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void manageacount()
    {

        chatrequestref.child(senderid).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(reciverid))

                {
                    String state=dataSnapshot.child(reciverid).child("request_type").getValue().toString();
                    if (state.equals("sent"))
                    {

                        curentstate="request_sent";
                        sendrequest.setText("cancile request");
                    }
                    else if(state.equals("recived"))
                    {
                        curentstate="request_recived";
                        sendrequest.setText("acept request");
                        declinecancilebutton.setVisibility(View.VISIBLE);

                        declinecancilebutton.setEnabled(true);

                        declinecancilebutton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cancilechatrequest();
                            }
                        });

                    }


                }
                else {
                    contact.child(senderid)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.hasChild(reciverid))
                                    {
                                        curentstate="friend";
                                        sendrequest.setText("removed contacts");

                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        if (!(senderid.equals(reciverid)))
        {

            //create database
            sendrequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    sendrequest.setEnabled(false);

                    if (curentstate.equals("new"))
                    {
                        sendrequestmethode();

                    }
                    if (curentstate.equals("request_sent"))
                    {
                        cancilechatrequest();

                    }
                    if(curentstate.equals("request_recived"))
                    {
                        aceptrequesst();

                    }
                    if(curentstate.equals("friend"))
                    {
                        removedfriend();

                    }





                }
            });



        }
        else {
            sendrequest.setVisibility(View.INVISIBLE);
        }






    }

    private void removedfriend() {

        contact.child(senderid).child(reciverid)
                .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    contact.child(reciverid).child(senderid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if (task.isSuccessful())
                            {
                                sendrequest.setEnabled(true);


                                curentstate="new";
                                sendrequest.setText("send request");


                                declinecancilebutton.setVisibility(View.INVISIBLE);
                                declinecancilebutton.setEnabled(false);

                            }

                        }
                    });
                }

            }
        });
    }

        private void aceptrequesst() {

        contact.child(senderid).child(reciverid)
                .child("contacts").setValue("saved").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                contact.child(reciverid).child(senderid).child("contacts")
                        .setValue("saved").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful())
                        {
                            chatrequestref.child(senderid).child(reciverid).removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful())
                                            {
                                                chatrequestref.child(reciverid).child(senderid).removeValue()
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                sendrequest.setEnabled(true);
                                                                curentstate="friend";
                                                                sendrequest.setText("removed");
                                                                declinecancilebutton.setVisibility(View.INVISIBLE);
                                                                declinecancilebutton.setEnabled(false);


                                                            }
                                                        });
                                            }

                                        }
                                    });
                        }

                    }
                });



            }
        });


    }

    private void cancilechatrequest()
    {

        chatrequestref.child(senderid).child(reciverid)
                .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    chatrequestref.child(reciverid).child(senderid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if (task.isSuccessful())
                            {
                                sendrequest.setEnabled(true);


                                curentstate="new";
                                sendrequest.setText("send request");

//                                declinebutton.setVisibility(View.INVISIBLE);
//                                declinebutton.setEnabled(false);







                            }

                        }
                    });
                }

            }
        });



    }

    private void sendrequestmethode() {

        chatrequestref.child(senderid).child(reciverid).child("request_type").setValue("sent").addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful())
                {

                    chatrequestref.child(reciverid).child(senderid).child("request_type").setValue("recived").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful())
                            {
                                sendrequest.setEnabled(true);
                                curentstate="request_sent";
                                sendrequest.setText("cancile request");

                            }

                        }
                    });

                }

            }
        });


    }
}
