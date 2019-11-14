package com.example.finalapp;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class RequestacceptFragment extends Fragment {

    private View layoutview;
    private FirebaseAuth auth;
    private DatabaseReference friendrequestsent,typeref;
    private DatabaseReference persionacount;
    private String curentuserid;
    private String listuserid;
    private RecyclerView recyclerView;



    public RequestacceptFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        layoutview=inflater.inflate(R.layout.fragment_requestaccept, container, false);

        auth=FirebaseAuth.getInstance();

        persionacount= FirebaseDatabase.getInstance().getReference().child("persionacount");
        friendrequestsent=FirebaseDatabase.getInstance().getReference().child("friendrequestsent");
        curentuserid=auth.getCurrentUser().getUid();
        recyclerView=layoutview.findViewById(R.id.requestrecycularlayoutid);



        // Inflate the layout for this fragment

        return  layoutview;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Databaseprofileclass>options=new FirebaseRecyclerOptions.Builder<Databaseprofileclass>()
                .setQuery(friendrequestsent.child(curentuserid),Databaseprofileclass.class)
                .build();

        FirebaseRecyclerAdapter<Databaseprofileclass,Requestviewholder>adapter=new FirebaseRecyclerAdapter<Databaseprofileclass, Requestviewholder>(options)
        {
            @Override
            protected void onBindViewHolder(@NonNull final Requestviewholder requestviewholder, int i, @NonNull Databaseprofileclass databaseprofileclass) {

                listuserid=getRef(i).getKey().toString();

//                friendrequestsent.child(curentuserid).child(listuserid).child("request_type").addValueEventListener(new ValueEventListener()
//                {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if (dataSnapshot.hasChild("request_type"))
//                        {
//                            String type=dataSnapshot.child("request_type").getValue().toString();
//                            if (type.equals("recived"))
//                            {
//
//                                persionacount.child(listuserid).addValueEventListener(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                                        if (dataSnapshot.hasChild("imageurl"))
//                                        {
//                                            String profileimage=dataSnapshot.child("imageurl").getValue().toString();
//                                            String username=dataSnapshot.child("username").getValue().toString();
//
//                                            requestviewholder.username.setText(username);
//                                            Picasso.with(getActivity().getApplicationContext()).load(profileimage).into(requestviewholder.circleImageView);
//
//
//
//                                        }
//                                        else {
//
//                                            String username=dataSnapshot.child("username").getValue().toString();
//
//                                            requestviewholder.username.setText(username);
//
//                                        }
//
//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                    }
//                                });
//
//                            }
//                        }
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });







               // typeref=curentuserid.child("reqest_type").getRef();
//                friendrequestsent.child(curentuserid).addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                        if (dataSnapshot.exists())
//                        {
//                            String type=dataSnapshot.child("request_type").getValue().toString();
//                            if (type.equals("recived"))
//                            {
//                                persionacount.child(listuserid).addValueEventListener(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                        if (dataSnapshot.hasChild("imageurl"))
//                                        {
//                                            String profileimage=dataSnapshot.child("imageurl").getValue().toString();
//                                            String username=dataSnapshot.child("username").getValue().toString();
//
//                                            requestviewholder.username.setText(username);
//                                            Picasso.with(getActivity().getApplicationContext()).load(profileimage).into(requestviewholder.circleImageView);
//
//
//
//                                        }
//                                        else {
//
//                                            String username=dataSnapshot.child("username").getValue().toString();
//
//                                            requestviewholder.username.setText(username);
//
//                                        }
//
//
//
//
//
//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                    }
//                                });
//
//                            }
//                        }
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });

//                persionacount.child(listuserid).addValueEventListener(new ValueEventListener()
//                {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if (dataSnapshot.hasChild("imageurl"))
//                        {
//                            String profileimage=dataSnapshot.child("imageurl").getValue().toString();
//                            String username=dataSnapshot.child("username").getValue().toString();
//
//                            requestviewholder.username.setText(username);
//                            Picasso.with(getActivity().getApplicationContext()).load(profileimage).into(requestviewholder.circleImageView);
//
//
//
//                        }
//                        else {
//
//                            String username=dataSnapshot.child("username").getValue().toString();
//
//                            requestviewholder.username.setText(username);
//
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });

                requestviewholder.username.setText("ruhul");
               // Picasso.with(getContext().getApplicationContext()).load()
               // Picasso.with(getActivity().getApplicationContext()).load(profileimage).into(requestviewholder.circleImageView);



            }

            @NonNull
            @Override
            public Requestviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view=LayoutInflater.from(getContext()).inflate(R.layout.requestfriend,parent,false);
                Requestviewholder requestviewholder=new Requestviewholder(view);
                return requestviewholder;

            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();








    }

    public class Requestviewholder extends RecyclerView.ViewHolder{

        private CircleImageView circleImageView;
        private Button accept,cancile;
        private TextView username;


        public Requestviewholder(@NonNull View itemView) {
            super(itemView);
            circleImageView=itemView.findViewById(R.id.requestcircleimageid);
            accept=itemView.findViewById(R.id.acceptid);
            cancile=itemView.findViewById(R.id.cancileid);
            username=itemView.findViewById(R.id.useernameid);

        }
    }


}




