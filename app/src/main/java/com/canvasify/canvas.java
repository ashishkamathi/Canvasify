package com.canvasify;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.android.graphics.CanvasView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class canvas extends AppCompatActivity {
    CanvasView canvas = null;
    SeekBar sb;
    LinearLayout pallet;
    FirebaseFirestore fStore;
    StorageReference storageRef;
    FirebaseStorage storage;
    Button post;
    int postid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);
        fStore = FirebaseFirestore.getInstance();
         storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        post =findViewById(R.id.button);

        pallet=findViewById(R.id.pallet);
        this.canvas = (CanvasView)this.findViewById(R.id.canvas);
        sb=findViewById(R.id.seekBar);
        this.canvas.setMode(CanvasView.Mode.DRAW);
        this.canvas.setPaintStyle(Paint.Style.STROKE);
        this.canvas.setPaintStrokeColor(Color.BLACK);
        this.canvas.setPaintStrokeWidth(2);

        DocumentReference documentReference = fStore.collection("posts").document("count");
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                postid=value.getLong("no").intValue();


            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post();

            }
        });


        sb.setMax(100);

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setsize(i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                sb.setVisibility(android.view.View.GONE);

            }
        });

    }
    public void setsize(int a){
        this.canvas.setPaintStrokeWidth(a);

    }
    public void size(View View){
        this.canvas.setPaintStrokeColor(Color.BLACK);

        sb.setVisibility(android.view.View.VISIBLE);

    }
    public void erase(View View){
        this.canvas.setMode(CanvasView.Mode.DRAW);
        this.canvas.setPaintStrokeColor(Color.WHITE);


    }

    public void colour(View View){
        pallet.setVisibility(android.view.View.VISIBLE);

    }

    public void undo(View View){

        this.canvas.undo();

    }
    public void redo(View View){
        this.canvas.redo();

    }
    public void red(View View){
        this.canvas.setPaintStrokeColor(Color.RED);
        pallet.setVisibility(android.view.View.GONE);

    }
    public void blue(View View){
        this.canvas.setPaintStrokeColor(Color.BLUE);
        pallet.setVisibility(android.view.View.GONE);

    }
    public void yellow(View View){
        this.canvas.setPaintStrokeColor(Color.YELLOW);
        pallet.setVisibility(android.view.View.GONE);

    }
    public void green(View View){
        this.canvas.setPaintStrokeColor(Color.GREEN);
        pallet.setVisibility(android.view.View.GONE);

    }

    public void post(){
        byte[] bytes = this.canvas.getBitmapAsByteArray();
        DocumentReference documentReference = fStore.collection("posts").document("count");

        StorageReference postref = storageRef.child("images/"+postid);
        postref.putBytes(bytes);

        Map<String, Object> user = new HashMap<>();
        user.put("no", postid+1);
        documentReference.set(user);

        startActivity(new Intent(getApplicationContext(), Feed.class));



    }


}