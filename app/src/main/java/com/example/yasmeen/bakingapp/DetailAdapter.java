package com.example.yasmeen.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.RecyclerViewHolder> {
    List<Step> steps;
    String recipeName;
    final private ListItemClickListener listItemClickListener;
    public DetailAdapter(ListItemClickListener listItemClickListener) {
        this.listItemClickListener = listItemClickListener;
    }
    public void setDeatailData(List<Step>s, String n)
    {
        this.steps=s;
        this.recipeName=n;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context= parent.getContext();
        View rootview = (LayoutInflater.from(context)).inflate(R.layout.item_card_details,parent,false);
        DetailAdapter.RecyclerViewHolder recyclerViewHolder= new DetailAdapter.RecyclerViewHolder(rootview);

        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.stepDis.setText(steps.get(position).getShortDescription());

    }

    @Override
    public int getItemCount() {
        if(steps.size()==0||steps==null)
            return 0;
        else
            return steps.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView stepDis;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            stepDis =(TextView)itemView.findViewById(R.id.stepDes);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            listItemClickListener.onListItemClick(steps,getAdapterPosition(),recipeName);

        }
    }
}
