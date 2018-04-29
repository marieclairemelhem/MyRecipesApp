package com.example.myrecipes.screens;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
        import android.widget.TextView;

        import com.example.myrecipes.Backend.authenticated.AuthenticatedApiManager;

        import com.example.myrecipes.R;
        import com.example.myrecipes.localstorage.LocalStorageManager;

        import com.example.myrecipes.models.Ingredients;
        import com.example.myrecipes.models.Recipe;
        import com.example.myrecipes.models.RecipesItem;
        import com.squareup.picasso.Picasso;

        import java.io.IOException;
        import java.io.Serializable;
        import java.util.List;

import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;



public class Results extends AppCompatActivity implements Serializable {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Recipe> recipesList;
    private LocalStorageManager localStorageManager;
    private AuthenticatedApiManager authenticatedApiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Intent getintent = getIntent();
        localStorageManager = LocalStorageManager.getInstance(getApplicationContext());

        recipesList = (List<Recipe>) getintent.getSerializableExtra("Recipes");
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecipeListAdapter(recipesList);
        recyclerView.setAdapter(adapter);
    }




    public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> {
        private RecipesItem itemRecipe;
        private List<Recipe> items;

        public RecipeListAdapter(List<Recipe> items) {
            this.items = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Context context = holder.itemView.getContext();
            itemRecipe = items.get(position).getRecipeobj();
            String name = itemRecipe.getName();
            String theIngredients = "";
            holder.recipeName.setText(name);

            String iconUrl = itemRecipe.getImg();
            Picasso.get().load(iconUrl).into(holder.image_recipe);
            List<Ingredients> ingredientlist = itemRecipe.getIngredientsList();
            if (ingredientlist != null && !ingredientlist.isEmpty()) {
                for (int i = 0; i < ingredientlist.size(); i++) {
                    String ingredient_name = ingredientlist.get(i).getIngredientname();
                    theIngredients += ingredient_name + ",";

                }
                holder.ingredientName.setText(theIngredients);
            }
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {
            private com.example.myrecipes.screens.clickListener clickListener;
            ImageView image_recipe;
            TextView recipeName;
            TextView ingredientName;
            Button toSave;

            public ViewHolder(View itemView) {
                super(itemView);

                image_recipe = itemView.findViewById(R.id.imageView);
                image_recipe.setOnClickListener(this);
                recipeName = itemView.findViewById(R.id.name);
                ingredientName = itemView.findViewById(R.id.ingredients);
                toSave = itemView.findViewById(R.id.Save);
                toSave.setOnClickListener(this);
            }


            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.imageView) {
                    Intent newIntent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(itemRecipe.getUrl()));
                    v.getContext().startActivity(newIntent);
                } else if (v.getId() == R.id.Save) {
                    if (localStorageManager.getUser() == null) {
                        android.app.FragmentManager manager = getFragmentManager();
                        android.app.Fragment frag = manager.findFragmentByTag("fragment_edit_name");
                        if (frag != null) {
                            manager.beginTransaction().remove(frag).commit();
                        }

                        DialogBox loginBox = new DialogBox();
                        loginBox.show(manager, "fragment_edit_name");
                    } else {
                        authenticatedApiManager = AuthenticatedApiManager.getInstance(getApplicationContext());
                        RecipesItem recipe = new RecipesItem(itemRecipe.getImg(),itemRecipe.getIngredientsList(),itemRecipe.getName(),itemRecipe.getUrl());
                        authenticatedApiManager.addRecipes(recipe).enqueue(new Callback<List<RecipesItem>>() {
                            @Override
                            public void onResponse(Call<List<RecipesItem>> call, Response<List<RecipesItem>> response) {
                                if (response.isSuccessful()) {
                                     toSave.setText("SAVED");
                                } else {
                                    try {
                                        String errorJson = response.errorBody().string();

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<List<RecipesItem>> call, Throwable t) {

                            }
                        });


                    }
                }
            }
        }
    }

}