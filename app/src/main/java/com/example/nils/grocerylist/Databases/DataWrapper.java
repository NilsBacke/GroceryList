package com.example.nils.grocerylist.Databases;

import android.util.Log;

import com.example.nils.grocerylist.Item;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Nils on 5/8/17.
 */

public class DataWrapper {

    final FirebaseDatabase database;

    public DataWrapper() {
        database = FirebaseDatabase.getInstance();
    }

    public void getItem(){
        Log.d("DATA", "called");
        DatabaseReference itemRef = database.getReference("grocerydata-11b07/Items/6638");
        itemRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Item item= dataSnapshot.getValue(Item.class);
                Log.d("DATA",item.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
