package com.udacity.sandwichclub;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private ImageView ingredientsIv;
    private TextView tvKnownAs;
    private TextView tvOrigin;
    private TextView tvIngredients;
    private TextView tvDescription;
    private ViewGroup vgAlsoKnwonContainer;
    private ViewGroup vgIngredientsContainer;
    private ViewGroup vgPlaceOriginContainer;
    private ViewGroup vgDescriptionContainer;
    Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.image_iv);
        tvKnownAs = findViewById(R.id.also_known_tv);
        tvOrigin = findViewById(R.id.origin_tv);
        tvIngredients = findViewById(R.id.ingredients_tv);
        tvDescription = findViewById(R.id.description_tv);
        vgAlsoKnwonContainer = findViewById(R.id.vg_also_known_container);
        vgIngredientsContainer = findViewById(R.id.vg_ingredients_container);
        vgPlaceOriginContainer = findViewById(R.id.vg_place_origin_container);
        vgDescriptionContainer = findViewById(R.id.vg_description_container);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());

        if (sandwich.getDescription() != null && !sandwich.getDescription().isEmpty()) {
            tvDescription.setText(sandwich.getDescription());
        } else {
            vgDescriptionContainer.setVisibility(View.GONE);
        }

        if (sandwich.getPlaceOfOrigin() != null && !sandwich.getPlaceOfOrigin().isEmpty()) {
            tvOrigin.setText(sandwich.getPlaceOfOrigin());
        } else {
            vgPlaceOriginContainer.setVisibility(View.GONE);
        }

        if (sandwich.getAlsoKnownAs().size() > 0) {
            for (int i=0; i<sandwich.getAlsoKnownAs().size(); i++) {
                String knownAs = sandwich.getAlsoKnownAs().get(i);

                if ((i + 1) < sandwich.getAlsoKnownAs().size()) {
                    knownAs = knownAs + ",";
                }
                tvKnownAs.setText(knownAs);
            }
        } else {
            vgAlsoKnwonContainer.setVisibility(View.GONE);
        }

        if (sandwich.getIngredients().size() > 0) {
            for (int i=0; i<sandwich.getIngredients().size(); i++) {
                String ingredients = sandwich.getIngredients().get(i);

                if ((i + 1) < sandwich.getIngredients().size()) {
                    ingredients = ingredients + ",";
                }
                tvIngredients.setText(ingredients);
            }
        } else {
            vgIngredientsContainer.setVisibility(View.GONE);
        }
    }
}
