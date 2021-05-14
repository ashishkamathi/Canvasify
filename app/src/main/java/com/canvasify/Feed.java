package com.canvasify;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Feed extends AppCompatActivity {
    FeedAdapter feedAdapter;
    RecyclerView mrecyclerView;
    LinearLayoutManager layoutManager;
    Adapter adapter;
    ListView listView;
    int id;
    FirebaseFirestore fStore;
    ArrayList<Integer> postid=new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        fStore = FirebaseFirestore.getInstance();



        DocumentReference documentReference = fStore.collection("posts").document("count");
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                id=value.getLong("no").intValue();
                for(int k=0;k<id;k++){
                    postid.add(k);
                    setadapter();


                }


            }
        });




    }

    public void post (View view){
        startActivity(new Intent(getApplicationContext(), canvas.class));

    }

    public void setadapter (){
        mrecyclerView=findViewById(R.id.recycle);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mrecyclerView.setLayoutManager(layoutManager);
        adapter=new Adapter(postid);
        mrecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}