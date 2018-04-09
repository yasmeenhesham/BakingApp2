package com.example.yasmeen.bakingapp;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecyclerViewHolder> {
    ArrayList<Recipe> mRecipes;
    Context mContext;
    final private ListItemClickListener mlistItemClickListener;

    public RecipeAdapter(ListItemClickListener mlistItemClickListener) {
        this.mlistItemClickListener = mlistItemClickListener;
    }

    public void setRecipeData(ArrayList<Recipe> recipesIn, Context context) {
        mRecipes = recipesIn;
        mContext = context;
        notifyDataSetChanged();
    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        View rootview = (LayoutInflater.from(context)).inflate(R.layout.items_cardview,parent,false);
        RecyclerViewHolder recyclerViewHolder= new RecyclerViewHolder(rootview);

        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.clickedTxt.setText(mRecipes.get(position).getName());
        String imgUrl= mRecipes.get(position).getImage();
        if(imgUrl!=null&&imgUrl!="")
        {
            Uri uri=Uri.parse(imgUrl).buildUpon().build();
            Picasso.with(mContext).load(uri).into(holder.clickedImg);
        }
    }

    @Override
    public int getItemCount() {
        if(mRecipes ==null)
           return 0;
        else
            return mRecipes.size();
}


    class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView clickedImg;
        TextView clickedTxt;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            clickedImg = (ImageView) itemView.findViewById(R.id.img);
            clickedTxt = (TextView) itemView.findViewById(R.id.name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mlistItemClickListener.onListItemClick(mRecipes.get(getAdapterPosition()));
        }

    }
}


