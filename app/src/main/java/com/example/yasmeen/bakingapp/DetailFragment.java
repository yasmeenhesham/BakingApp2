package com.example.yasmeen.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DetailFragment extends Fragment {
    Recipe selectedRecipe ;
    String recipeName;
    public DetailFragment()
    {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView;
        TextView textView;
        if(savedInstanceState!=null)
        {
            selectedRecipe=savedInstanceState.getParcelable("Recipe");
        }
        else
            selectedRecipe=getArguments().getParcelable("Recipe");
        ((RecipeDetailActivity) getActivity()).getSupportActionBar().show();

        List<Ingredient> selectedIngredients = selectedRecipe.getIngredients();
        recipeName=selectedRecipe.getName();
        View root = inflater.inflate(R.layout.detail_fragment,container,false);
        textView= (TextView)root.findViewById(R.id.detail_text);
        ArrayList<String> recipeIngredientsForWidgets= new ArrayList<>();

        for (int i=0;i<selectedIngredients.size();i++)
        {
            textView.append("\u2022 "+selectedIngredients.get(i).getIngredient()+"\n");
            textView.append("\t\t Quantity : "+selectedIngredients.get(i).getQuantity()+"\n");
            textView.append("\t\t Measure : "+selectedIngredients.get(i).getMeasure()+"\n\n");

            recipeIngredientsForWidgets.add(selectedIngredients.get(i).getIngredient()+"\n"+
                    "Quantity: "+selectedIngredients.get(i).getQuantity().toString()+"\n"+
                    "Measure: "+selectedIngredients.get(i).getMeasure()+"\n");
        }
        recyclerView = (RecyclerView)root.findViewById(R.id.steps_recycler);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        DetailAdapter detailAdapter= new DetailAdapter((RecipeDetailActivity)getActivity());
        recyclerView.setAdapter(detailAdapter);
        detailAdapter.setDeatailData(selectedRecipe.getSteps(),recipeName);
        UpdateBakingService.startBakingService(getContext(),recipeIngredientsForWidgets);

        return root;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("Recipe",selectedRecipe);
        outState.putString("Name",recipeName);
    }
}
