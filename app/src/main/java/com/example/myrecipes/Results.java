package com.example.myrecipes;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myrecipes.models.Ingredients;
import com.example.myrecipes.models.Recipe;
import com.example.myrecipes.models.RecipesItem;
import com.example.myrecipes.models.SearchRecipes;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class Results extends AppCompatActivity implements Serializable {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Recipe> recipesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Intent getintent = getIntent();
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
            holder.name.setText(name);

            String iconUrl = itemRecipe.getImg();
            Picasso.with(context).load(iconUrl).into(holder.image_recipe);
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
            private clickListener clickListener;
            ImageView image_recipe;
            TextView name;
            TextView ingredientName;

            public ViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                image_recipe = itemView.findViewById(R.id.imageView);
                name = itemView.findViewById(R.id.name);
                ingredientName = itemView.findViewById(R.id.ingredients);

            }


            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(itemRecipe.getUrl()));
                v.getContext().startActivity(newIntent);
            }
        }

    }

}
