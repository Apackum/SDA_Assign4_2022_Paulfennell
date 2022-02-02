package com.example.SDA_ASSIGNMENT4_PAULFENNELL;
/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/*
 * @author Paul Fennell 2022
 */
public class LibraryViewAdapter extends RecyclerView.Adapter<LibraryViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageItem;
        TextView authorText;
        TextView titleText;
        Button checkOut;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            //grab the image, the text and the layout id's
            imageItem = itemView.findViewById(R.id.bookImage);
            authorText = itemView.findViewById(R.id.authorText);
            titleText = itemView.findViewById(R.id.bookTitle);
            checkOut = itemView.findViewById(R.id.out_button);
        }
    }

    private final Context mNewContext;
    private final ArrayList<books> bookList;

    public LibraryViewAdapter(Context mNewContext, ArrayList<books> bookList) {
        this.mNewContext = mNewContext;
        this.bookList = bookList;
    }

    //declare methods
    @NonNull
    @Override
    public LibraryViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        Log.d(TAG, "onBindViewHolder: was called");
        //TextViews
        viewHolder.authorText.setText(bookList.get(position).getAuthor());
        viewHolder.titleText.setText(bookList.get(position).getTitle());
        //Using glide to store images
        Glide.with(mNewContext)
                .load(bookList.get(position).getImageUrl())
                .into(viewHolder.imageItem);

        //should check here to see if the book is available.
        viewHolder.checkOut.setOnClickListener(v -> {
            Intent myOrder = new Intent(mNewContext, CheckOut.class);
            mNewContext.startActivity(myOrder);
        });
    }
    //Set item count to the number of booklist items
    @Override
    public int getItemCount() {
        return bookList.size();
    }
}
