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
 * Images used are sourced from Public Domain Day 2019.
 * by Duke Law School's Center for the Study of the Public Domain
 * is licensed under a Creative Commons Attribution-ShareAlike 3.0 Unported License.
 * A simple {@link Fragment} subclass.
 * @author Chris Coughlan
 */
public class BookList extends Fragment {

    public BookList() {
        // Required empty public constructor
    }
    //Widgets
    RecyclerView recyclerView;
    //Firebase
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
        //Firebase
        myRef = FirebaseDatabase.getInstance().getReference();
        //Arraylist
        booksList = new ArrayList<>();

        //CLear arrayList
        ClearAll();

        //Getting the data message
        getDataFromFirebase();
return root;
    }



    private void getDataFromFirebase() {
        Query query = myRef.child("books");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ClearAll();
                for (DataSnapshot snapshots : dataSnapshot.getChildren()) {
                    books Books = new books();
                    Books.setImageUrl(snapshots.child("image").getValue().toString());
                    Log.d("help", "Value is: " + Books);
                    Books.setAuthor(snapshots.child("author").getValue().toString());
                    Books.setTitle(snapshots.child("title").getValue().toString());

                    booksList.add(Books);
                }
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

       /*
        ArrayList<String> mAuthor = new ArrayList<>();
        ArrayList<String> mTitle = new ArrayList<>();
        ArrayList<Integer> mImageID = new ArrayList<>();

        //simple loop here to add the images to the array without typing each one
        for(int i=1;i<=14;i++) {
            int id = getResources().getIdentifier("sku1000" + i, "drawable",
                    root.getContext().getPackageName());
            mImageID.add(id);
        }

        //adding author and title.
        mAuthor.add("Edgar Rice Burroughs"); mTitle.add("Tarzan and the Golden Lion");
        mAuthor.add("Agatha Christie"); mTitle.add("The Murder on the Links");
        mAuthor.add("Winston S. Churchill"); mTitle.add("The World Crisis");
        mAuthor.add("E.e. cummings"); mTitle.add("Tulips and Chimneys");
        mAuthor.add("Robert Frost"); mTitle.add("New Hampshire");
        mAuthor.add("Kahlil Gibran"); mTitle.add("The Prophet");
        mAuthor.add("Aldous Huxley"); mTitle.add("Antic Hay");
        mAuthor.add("D.H. Lawrence"); mTitle.add("Kangaroo");
        mAuthor.add("Bertrand and Dora Russell"); mTitle.add("The Prospects of Industrial Civilization");
        mAuthor.add("Carl Sandberg"); mTitle.add("Rootabaga Pigeons");
        mAuthor.add("Edith Wharton"); mTitle.add("A Son at the Front");
        mAuthor.add("P.G. Wodehouse"); mTitle.add("The Inimitable Jeeves");
        mAuthor.add("P.G. Wodehouse"); mTitle.add("Leave it to Psmith");
        mAuthor.add("Viginia Woolf"); mTitle.add("Jacob's Room");


        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));*/
