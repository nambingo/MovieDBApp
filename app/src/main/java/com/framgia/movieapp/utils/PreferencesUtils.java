package com.framgia.movieapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.framgia.movieapp.model.MainResults;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by FRAMGIA\pham.duc.nam on 26/06/2017.
 */

public class PreferencesUtils {
  public static final String PREFS_TAG = "SharedPrefs";
  public static final String PRODUCT_TAG = "Movie App";

    public static ArrayList<MainResults> getFavoriteResult(Context context) {
        Gson gson = new Gson();
        ArrayList<MainResults> productFromShared = new ArrayList<>();
        SharedPreferences sharedPref =
                context.getSharedPreferences(PREFS_TAG, Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString(PRODUCT_TAG, "");

        Type type = new TypeToken<ArrayList<MainResults>>() {
        }.getType();
        productFromShared = gson.fromJson(jsonPreferences, type);

        return productFromShared;
    }

    public static void setFavoriteResult(MainResults mainResults, Context context) {

        Gson gson = new Gson();
        SharedPreferences sharedPref =
                context.getSharedPreferences(PREFS_TAG, Context.MODE_PRIVATE);

        String jsonSaved = sharedPref.getString(PRODUCT_TAG, "");
        String jsonNewproductToAdd = gson.toJson(mainResults);

        JSONArray jsonArrayProduct = new JSONArray();

        try {
            if (jsonSaved.length() != 0) {
                jsonArrayProduct = new JSONArray(jsonSaved);
            }
            jsonArrayProduct.put(new JSONObject(jsonNewproductToAdd));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(PRODUCT_TAG, String.valueOf(jsonArrayProduct));
        editor.commit();
    }
}
