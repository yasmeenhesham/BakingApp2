package com.example.yasmeen.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

public class BakingWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new BakingRemoteView(this.getApplicationContext(),intent);
    }
}
class BakingRemoteView implements RemoteViewsService.RemoteViewsFactory
{
    Context mContext;
    private ArrayList<String> mIngredientsArrayList;


    public BakingRemoteView(Context context,Intent intent)
    {
        this.mContext=context;
//        if(BakingWidgetProvider.mRecipeSelected != null) {
//            mIngredientsArrayList = BakingWidgetProvider.ingredientsList;
//        }
    }
    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        //if(BakingWidgetProvider.mRecipeSelected != null) {
            mIngredientsArrayList = BakingWidgetProvider.ingredientsList;
      //  }
//        else
//        {
//            mIngredientsArrayList=null;
//        }

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (mIngredientsArrayList==null)
            return 0;
        else
            return mIngredientsArrayList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views= new RemoteViews(mContext.getPackageName(),R.layout.baking_widget);
        views.setTextViewText(R.id.widget_grid_view_item,mIngredientsArrayList.get(position));
        Intent fillInIntent = new Intent();
        views.setOnClickFillInIntent(R.id.widget_grid_view_item, fillInIntent);
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {



        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
