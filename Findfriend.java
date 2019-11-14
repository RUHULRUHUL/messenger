package com.example.finalapp;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
public class Findfriend extends Fragment {
    private View view;
    private CircleImageView circleImageView;
    private DatabaseReference rotref,friendrequestsent;
    private RecyclerView recyclerView;
    private String curentuserid, reciverid,senderid;
    private FirebaseAuth auth;
    private String curent_state="new";



    public Findfriend() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_findfriend, container, false);
        auth=FirebaseAuth.getInstance();
        curentuserid=auth.getCurrentUser().getUid();

        senderid=auth.getCurrentUser().getUid();

        recyclerView=view.findViewById(R.id.findfriendrecycleviewid);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        rotref=FirebaseDatabase.getInstance().getReference().child("persionacount");
        friendrequestsent=FirebaseDatabase.getInstance().getReference().child("friendrequestsent");


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Findfriendaccesssclass>options= new FirebaseRecyclerOptions.Builder<Findfriendaccesssclass>()
                .setQuery(rotref,Findfriendaccesssclass.class)
                .build();

        FirebaseRecyclerAdapter<Findfriendaccesssclass,Viewholder>adapter=new FirebaseRecyclerAdapter<Findfriendaccesssclass, Viewholder>(options)
        {
            @Override
            protected void onBindViewHolder(@NonNull final Viewholder viewholder, final int i, @NonNull Findfriendaccesssclass findfriendaccesssclass)
            {



                viewholder.name.setText(findfriendaccesssclass.getUsername());
                Picasso.with(getActivity().getApplicationContext()).load(findfriendaccesssclass.getImageurl()).into(viewholder.circleImageView);






                viewholder.circleImageView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {


                        reciverid=getRef(i).getKey().toString();



                        Intent intent=new Intent(getActivity().getApplicationContext(), Profile.class);
                        intent.putExtra("reciverid",reciverid);
                        Toast.makeText(getActivity().getApplicationContext(), "id: "+reciverid, Toast.LENGTH_SHORT).show();
                        startActivity(intent);





                    }
                });

                viewholder.addfriend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent=new Intent(getActivity().getApplicationContext(),Messegecontactactivity.class);
                        startActivity(intent);




                    }
                });







//                viewholder.addfriend.setOnClickListener(new View.OnClickListener()
//                {
//
//                    String recive=getRef(i).getKey().toString();
//
//
//                    @Override
//                    public void onClick(View view) {
//
//                        if (curent_state.equals("new"))
//                        {
//
//                            friendrequestsent.child(senderid).child(recive).child("request_type").setValue("sent").addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//
//                                    if (task.isSuccessful())
//                                    {
//
//                                        friendrequestsent.child(recive).child(senderid).child("request_type").setValue("recived").addOnCompleteListener(new OnCompleteListener<Void>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<Void> task) {
//
//                                                if (task.isSuccessful())
//                                                {
//
//                                                    curent_state="request_sent";
//                                                    viewholder.addfriend.setEnabled(true);
//                                                    viewholder.addfriend.setText("cancile request");
//                                                    viewholder.addfriend.setBackgroundColor(getResources().getColor(R.color.colorAccent));
//
//
//
//
//                                                }
//
//                                            }
//                                        });
//
//
//
//                                    }
//
//                                }
//                            });
//
//
//
//                        }
//
//                        if (curent_state.equals("request_sent"))
//                        {
//                            friendrequestsent.child(senderid).child(recive).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//
//                                    friendrequestsent.child(recive).child(senderid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//
//                                            if (task.isSuccessful())
//                                            {
//                                                curent_state="new";
//                                                viewholder.addfriend.setEnabled(true);
//                                                viewholder.addfriend.setBackgroundColor(getResources().getColor(R.color.colorrequestsentbutton));
//                                                viewholder.addfriend.setText("requestsent");
//
//                                            }
//
//                                        }
//                                    });
//
//                                }
//                            });
//
//                        }
//
//
//
//                    }
//                });














            }



            @NonNull
            @Override
            public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

               View view= LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.findfriendlayout,parent,false);

                Viewholder viewholder=new Viewholder(view);

                return viewholder;

            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();



    }

//    private void managechatreq() {
//
//
//        viewholder.addfriend.setOnClickListener(new View.OnClickListener()
//        {
//
//            String recive=getRef(i).getKey().toString();
//
//
//            @Override
//            public void onClick(View view) {
//
//                if (curent_state.equals("new"))
//                {
//
//                    friendrequestsent.child(senderid).child(recive).child("request_type").setValue("sent").addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//
//                            if (task.isSuccessful())
//                            {
//
//                                friendrequestsent.child(recive).child(senderid).child("request_type").setValue("recived").addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//
//                                        if (task.isSuccessful())
//                                        {
//
//                                            curent_state="request_sent";
//                                            viewholder.addfriend.setEnabled(true);
//                                            viewholder.addfriend.setText("cancile request");
//                                            viewholder.addfriend.setBackgroundColor(getResources().getColor(R.color.colorAccent));
//
//
//
//
//                                        }
//
//                                    }
//                                });
//
//
//
//                            }
//
//                        }
//                    });
//
//
//
//                }
//
//                if (curent_state.equals("request_sent"))
//                {
//                    friendrequestsent.child(senderid).child(recive).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//
//                            friendrequestsent.child(recive).child(senderid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//
//                                    if (task.isSuccessful())
//                                    {
//                                        curent_state="new";
//                                        viewholder.addfriend.setEnabled(true);
//                                        viewholder.addfriend.setText("requestsent");
//
//                                    }
//
//                                }
//                            });
//
//                        }
//                    });
//
//                }
//
//
//
//            }
//        });
//
//    }


//    private void cancilefriendreq(final Viewholder viewholder) {
//
//        chatrequestref.child(curentuserid).child(reciverid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//
//                if (task.isSuccessful())
//                {
//                    chatrequestref.child(reciverid).child(curentuserid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//
//                            if (task.isSuccessful())
//                            {
//
//                                //viewholder.addfriend.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//                                //viewholder.addfriend.setVisibility(View.VISIBLE);
//                                curent_state="new";
//                                viewholder.addfriend.setText("add friend");
//
//                            }
//
//                        }
//                    });
//                }
//
//            }
//        });
//
//    }

//    private void senfriendrequest(final Viewholder viewholder) {
//
//       chatrequestref.child(curentuserid).child(reciverid).child("request_type").setValue("sent").addOnCompleteListener(new OnCompleteListener<Void>() {
//           @Override
//           public void onComplete(@NonNull Task<Void> task) {
//
//               if (task.isSuccessful())
//               {
//                   chatrequestref.child(reciverid).child(curentuserid).child("request_type").setValue("recived").addOnCompleteListener(new OnCompleteListener<Void>() {
//                       @Override
//                       public void onComplete(@NonNull Task<Void> task) {
//
//                           if (task.isSuccessful())
//                           {
//
//
//                               curent_state="request_sent";
//                               viewholder.addfriend.setText("cancile request");
//                               //viewholder.addfriend.setBackgroundColor(getResources().getColor(R.color.colorAccent));
//
//
//
//
//                           }
//
//                       }
//                   });
//               }
//
//
//
//           }
//       });
//    }

    class Viewholder extends RecyclerView.ViewHolder {

        TextView name;
        CircleImageView circleImageView;
       private Button addfriend;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.useername);
            //status=itemView.findViewById(R.id.userstatus);
            circleImageView=itemView.findViewById(R.id.circleimageid);
            addfriend=itemView.findViewById(R.id.requestsentid);
           // cancilefriend=itemView.findViewById(R.id.cancileid);






        }
    }

}
