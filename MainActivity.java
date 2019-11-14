package com.example.finalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
   private TabLayout tabLayout;
   private ViewPager viewPager;
   private FirebaseAuth auth;
   private FirebaseUser curentuserid;
   private DatabaseReference rotref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout=findViewById(R.id.maintabid);
        viewPager=findViewById(R.id.Mainviewpagerid);

        auth=FirebaseAuth.getInstance();
        curentuserid=auth.getCurrentUser();


        rotref=FirebaseDatabase.getInstance().getReference().child("persionacount");




        Fragmentaccessserclass fragmentaccesser=new Fragmentaccessserclass(getSupportFragmentManager());
        viewPager.setAdapter(fragmentaccesser);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.mesesege);
        tabLayout.getTabAt(1).setIcon(R.drawable.onlineactice);
        tabLayout.getTabAt(2).setIcon(R.drawable.findfriend);
        tabLayout.getTabAt(3).setIcon(R.drawable.settings);
        tabLayout.getTabAt(4).setIcon(R.drawable.profile);



    }

    @Override
    protected void onStart() {

        super.onStart();


        if (curentuserid==null)
        {

            gotologingactivity();











        }
        else {
           verifiuserinformationavailable();



        }


    }

    private void verifiuserinformationavailable() {

        String userid=auth.getCurrentUser().getUid();

        rotref.child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if ((dataSnapshot.child("username").exists()))
                {
                    Toast.makeText(MainActivity.this,"informationd is done",Toast.LENGTH_SHORT).show();




                }
                else {
                    gotoprofileedit();

                    //gotologingactivity();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void gotoprofileedit() {


        Intent intent=new Intent(MainActivity.this,Setimageactivity.class);
        startActivity(intent);
    }

    private void gotologingactivity() {

        Intent intent=new Intent(MainActivity.this,Loging.class);
        startActivity(intent);
    }
}
