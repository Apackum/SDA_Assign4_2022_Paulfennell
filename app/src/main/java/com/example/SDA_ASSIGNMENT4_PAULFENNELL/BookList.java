package com.example.SDA_ASSIGNMENT4_PAULFENNELL;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * @author Paul Fennell
 */

public class BookList extends Fragment {

    public BookList() {
        // Required empty public constructor
    }
    //Widgets
    RecyclerView recyclerView;
    //Firebase reference variable
    public DatabaseReference myRef;
    //My variables
    public ArrayList<books> booksList;
    public LibraryViewAdapter recyclerAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_book_list, container, false);

        recyclerView = root.findViewById(R.id.bookView_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        //Firebase reference
        myRef = FirebaseDatabase.getInstance().getReference();
        //Arraylist
        booksList = new ArrayList<>();

        //CLear arrayList
        ClearAll();

        //Getting the data message method
        getDataFromFirebase();
        return root;
    }

    //Methods

    private void getDataFromFirebase() {
        //Creating a query for firebase
        Query query = myRef.child("books");

        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Calling clearall method
                ClearAll();
                //for loop to add to the booklist from database
                for (DataSnapshot snapshots : dataSnapshot.getChildren()) {

                    books Books = new books();
                    Books.setImageUrl(snapshots.child("image").getValue().toString());
                    Log.d("help", "Value is: " + Books);
                    Books.setAuthor(snapshots.child("author").getValue().toString());
                    Books.setTitle(snapshots.child("title").getValue().toString());
                    booksList.add(Books);
                }
                //Setting up recycler
                recyclerAdapter = new LibraryViewAdapter(getContext(), booksList);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("HELP", "Failed to read value.", error.toException());

            }
        });
    }

    //This is my clearALL method
    private void ClearAll() {
        if (booksList != null) {
            booksList.clear();
            if(recyclerAdapter != null){
                recyclerAdapter.notifyDataSetChanged();
            }
        } else {
            booksList = new ArrayList<>();
        }
    }
}

