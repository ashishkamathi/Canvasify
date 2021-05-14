package com.canvasify;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class FeedAdapter extends ArrayAdapter<Integer> {
    Context context;
    FirebaseStorage storage;
    StorageReference storageRef;
    private ArrayList<Integer> postid=new ArrayList<Integer>();
    public FeedAdapter(Context c, ArrayList<Integer> oid) {
        super(c, R.layout.row,R.id.textView2,oid);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        this.context = c;
        this.postid = oid;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.row, parent, false);
        StorageReference postref = storageRef.child("images/"+postid.get(position));
        ImageView imageView=row.findViewById(R.id.imageView4);

        final long ONE_MEGABYTE = 1024 * 1024;
        postref.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageView.setImageBitmap(bmp);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        return row;
    }

}
