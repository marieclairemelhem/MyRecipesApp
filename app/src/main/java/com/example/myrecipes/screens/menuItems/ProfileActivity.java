package com.example.myrecipes.screens.menuItems;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.myrecipes.Backend.authenticated.AuthenticatedApiManager;
import com.example.myrecipes.R;
import com.example.myrecipes.localstorage.LocalStorageManager;

import com.example.myrecipes.models.RecipiesApi.RecipesItem;
import com.example.myrecipes.models.Users.User;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    private AuthenticatedApiManager authenticatedApi;
    private User user;
    private LocalStorageManager localStorageManager;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private AuthenticatedApiManager authenticatedApiManager;

    @BindView(R.id.my_recycler_view)
    RecyclerView recipeList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        localStorageManager = LocalStorageManager.getInstance(this);
        getUser();
        authenticatedApi.getRecipesSaved().enqueue(new Callback<List<RecipesItem>>() {
            @Override
            public void onResponse(Call<List<RecipesItem>> call, Response<List<RecipesItem>> response) {
                if (response.isSuccessful()) {
                    List<RecipesItem> recipesItems = response.body();
                    showRecipes(recipesItems);
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

    private void showRecipes( List<RecipesItem> recipes){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecipesListAdapter(recipes);
        recyclerView.setAdapter(adapter);
    }

    private void getUser() {
            if (localStorageManager.getUser() == null) {
                Intent intent = new Intent(this,MenuActivity.class);
                startActivity(intent);
            } else {
                authenticatedApi = AuthenticatedApiManager.getInstance(this);
                authenticatedApi.getProfile().enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                             user = response.body();
                            showUserProfile(user);
                        } else {
                            //error//
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<User> call, Throwable t) {

                    }
                });


            }

        }

    private void showUserProfile(User user) {
         TextView nameTextView = findViewById(R.id.profileName);
        if (!TextUtils.isEmpty(user.getName())) {
            String name=user.getName();
            nameTextView.setText(name);
        }

    }

    public class RecipesListAdapter extends RecyclerView.Adapter<RecipesListAdapter.SavedViewHolder> {
        private RecipesItem recipe;
        private List<RecipesItem> items;

        private RecipesListAdapter(List<RecipesItem> items) {
            this.items = items;
        }


        @NonNull
        @Override
        public SavedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowsaved, parent, false);
            return new SavedViewHolder(view);
        }

        @Override
        public void onBindViewHolder( SavedViewHolder holder, int position) {
             recipe = items.get(position);
            String name = recipe.getName();

            holder.recipeName.setText(name);
            holder.url.setText(recipe.getUrl());
            String iconUrl = recipe.getImg();
            Picasso.get().load(iconUrl).into(holder.image);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public  class SavedViewHolder extends RecyclerView.ViewHolder  implements RecyclerView.OnClickListener {


            TextView recipeName;
            TextView url;
            ImageView imageViewDelete;
            ImageView image;
            private SavedViewHolder(View itemView) {
                super(itemView);
                recipeName = itemView.findViewById(R.id.name);
                image=itemView.findViewById(R.id.image);
                url = itemView.findViewById(R.id.url);
                url.setOnClickListener(this);
               imageViewDelete=itemView.findViewById(R.id.delete);
                imageViewDelete.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.url) {
                    Intent newIntent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(recipe.getUrl()));
                    v.getContext().startActivity(newIntent);
                }
                else if(v.getId()==R.id.delete) {
                    authenticatedApiManager = AuthenticatedApiManager.getInstance(getApplicationContext());
                    authenticatedApiManager.deleteRecipe(recipe.getId()).enqueue(new Callback<List<RecipesItem>>() {
                        @Override
                        public void onResponse(Call<List<RecipesItem>> call, Response<List<RecipesItem>> response) {
                            removeAt(getAdapterPosition());
                        }

                        @Override
                        public void onFailure(Call<List<RecipesItem>> call, Throwable t) {

                        }
                    });
                }
            }

            public void removeAt(int position) {
                items.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, items.size());
                notifyDataSetChanged();
            }
        }


    }
}