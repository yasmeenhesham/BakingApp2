package com.example.yasmeen.bakingapp;

import java.util.List;

public interface ListItemClickListener {
    void onListItemClick(List<Step> stepsOut, int clickedItemIndex, String recipeName);
    void onListItemClick(Recipe clickedItemIndex);
}
