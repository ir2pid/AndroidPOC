package com.noisyninja.quandoopoc.layers;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.gson.GsonBuilder;
import com.noisyninja.quandoopoc.BuildConfig;

import java.lang.reflect.Type;

import static android.content.Context.MODE_PRIVATE;

/**
 * Module to hold all utility methods
 * Created by sudiptadutta on 27/04/18.
 */
public class UtilModule {

    public static String DEFAULT_PREF = "DEFAULT_PREF";

    public String getStringRes(Context context, @StringRes int resId) {
        return context.getString(resId);
    }

    public String getStringPref(Context context, String key) {
        return context.getSharedPreferences(DEFAULT_PREF, MODE_PRIVATE).getString(key, null);
    }

    public void setStringPref(Context context, String key, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(DEFAULT_PREF, MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void deleteStringPref(Context context, String key) {
        context.getSharedPreferences(DEFAULT_PREF, MODE_PRIVATE).edit().remove(key).apply();
    }

    public void showSnackBar(View view, String text) {
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT);
        ViewGroup contentLay = (ViewGroup) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text).getParent();
        ProgressBar item = new ProgressBar(view.getContext());
        contentLay.addView(item);
        snackbar.show();
        //.setAction("Action", null).show();
    }

    public void logI(Class clazz, String text) {
        if (BuildConfig.DEBUG) {
            Log.i(clazz.getSimpleName(), text);
        }
    }

    public String toJson(Object object) {
        return new GsonBuilder().setPrettyPrinting().create().toJson(object);
    }

    public <T> T fromJson(String string, Class<T> t) {
        return new GsonBuilder().setPrettyPrinting().create().fromJson(string, t);
    }

    public <T> T fromJson(String string, Type type) {
        return new GsonBuilder().setPrettyPrinting().create().fromJson(string, type);
    }
/*
    public void loadImage(Context context, String url, ImageView imageView) {
        String imageUrl = BuildConfig.POSTER_URI + url;
        //Utils.logI(Utils.class, "Loading image:" + imageUrl);
        Picasso.with(context)
                .load(imageUrl)
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(imageView);
    }

    public void share(Context context, String name, String title) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(BuildConfig.MIME_TYPE);
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, getStringRes(context, R.string.share_tag) + name);
        context.startActivity(intent);
    }

    public void getAllShared(Context context) {
        Map<String, ?> allEntries = context.getSharedPreferences(DEFAULT_PREF, MODE_PRIVATE).getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
        }
    }
*/

}