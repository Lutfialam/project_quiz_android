package com.example.quiz.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {
    private SharedPreferences prefs;

    public Session(Context cntx) {
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void destroy_session() {
        prefs.edit().remove("id").apply();
        prefs.edit().remove("name").apply();
        prefs.edit().remove("email").apply();
        prefs.edit().remove("level").apply();
    }

    public String getInitialName() {
        if (this.getName() != null && this.getName().length() > 1) {
            System.out.println(this.getName());
            try {
                String[] initial_name = this.getName().split(" ");
                String first_initial = initial_name[0].substring(0, 1);
                if (initial_name.length <= 1) {
                    return first_initial;
                }
                String first_second_initial = initial_name[0].substring(1, 2).length() <= 0 ? "" : initial_name[0].substring(1, 2);
                String second_initial = initial_name[1].substring(0, 1).length() <= 0 ? first_second_initial : initial_name[1].substring(0, 1);
                return second_initial.length() <= 0 ? this.getName().substring(0, 2) : first_initial.concat(second_initial);
            } catch (Exception e) {
                char[] asd = this.getName().toCharArray();
                return String.valueOf(asd[0]);
            }
        }
        return "N";
    }
    public void setId(String id) {
        prefs.edit().putString("id", id).apply();
    }

    public String getId() {
        return prefs.getString("id","");
    }

    public void setName(String name) {
        prefs.edit().putString("name", name).apply();
    }

    public String getName() {
        return prefs.getString("name","");
    }

    public void setEmail(String email) {
        prefs.edit().putString("email", email).apply();
    }

    public String getEmail() {
        return prefs.getString("email","");
    }

    public void setLevel(String level) {
        prefs.edit().putString("level", level).apply();
    }

    public String getLevel() {
        return prefs.getString("level", "default");
    }
    
    public void setString(String key, String value) {
        prefs.edit().putString(key, value).commit();
    }

    public String getString(String key) {
        return prefs.getString(key, "");
    }
}