package com.example.finalapp;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class Settings extends Fragment {

    private TextView profile;
    private TextView logout;
    private FirebaseAuth auth;
    private View view;
    private TextView myprofileview;




    public Settings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment




          view= inflater.inflate(R.layout.fragment_settings, container, false);

          auth=FirebaseAuth.getInstance();

          profile=view.findViewById(R.id.persionprofileid);
          logout=view.findViewById(R.id.logoutid);
          myprofileview=view.findViewById(R.id.myprofile);



//          myprofileview.setOnClickListener(new View.OnClickListener() {
//              @Override
//              public void onClick(View view) {
//                  Intent intent=new Intent(Objects.requireNonNull(getActivity()).getApplicationContext(),Profile.class);
//                  startActivity(intent);
//              }
//          });

//          profile.setOnClickListener(new View.OnClickListener()
//          {
//              @Override
//              public void onClick(View view) {
//                  Intent intent=new Intent(getContext(),Profile.class);
//                  startActivity(intent);
//              }
//          });

          logout.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                  auth.signOut();
                  Intent intent=new Intent(getActivity().getApplicationContext(),Loging.class);
                  startActivity(intent);

              }
          });

          return view;



    }

}
