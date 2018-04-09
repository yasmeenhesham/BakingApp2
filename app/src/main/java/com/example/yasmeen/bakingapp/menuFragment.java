package com.example.yasmeen.bakingapp;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class menuFragment extends Fragment {
    static String ALL_RECIPES="All_Recipes";
    public menuFragment()
    {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView mRecyclerView;
        View root = inflater.inflate(R.layout.menu_fragment,container,false);
        mRecyclerView=(RecyclerView)root.findViewById(R.id.recycler);
        final RecipeAdapter mAdapter = new RecipeAdapter((MenuActivity)getActivity());
        mRecyclerView.setAdapter(mAdapter);
        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),numberOfColumns());
        mRecyclerView.setLayoutManager(layoutManager);
        RetrofitInterface retrofitInterface=RetrofitBuilder.Retrive();
        Call<ArrayList<Recipe>> mRecips= retrofitInterface.getRecipes();
        mRecips.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                ArrayList<Recipe> recipes = response.body();
                Bundle recipesBundle = new Bundle();
                recipesBundle.putParcelableArrayList(ALL_RECIPES, recipes);
                mAdapter.setRecipeData(recipes,getContext());

            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                Log.v("http fail: ", t.getMessage());

            }
        });
        return root;
    }

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((MenuActivity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
       // if (nColumns < 2) return 1;
        return nColumns;
    }
}
