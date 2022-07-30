package com.example.quiz.services;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.quiz.helpers.Session;
import com.example.quiz.models.ErrorUserMessage;
import com.example.quiz.models.ResponseJSON;
import com.example.quiz.models.ResponseJSONObject;
import com.example.quiz.models.User;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.function.Function;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AuthService extends Instance {
    CountDownLatch cd = new CountDownLatch(0);
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public void register(Context context, User user, Runnable onSuccess, Runnable onFailed) {
        try {
            JSONObject data = new JSONObject();
            data.put("name", user.getName());
            data.put("email", user.getEmail());
            data.put("password", user.getPassword());
            data.put("password_confirmation", user.getPassword_confirmation());

            defaultPost("/auth/signup", data.toString(), new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    new Session(context).setString("signupStatus", "error: " + e.getMessage());
                    onFailed.run();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String body = response.body().string();

                    try {
                        ResponseJSON jsonString = new Gson().fromJson(body, ResponseJSON.class);
                        new Session(context).setString("signupStatus", jsonString.getStatus() + ": " + jsonString.getMessage());
                        onSuccess.run();
                    } catch (Exception e) {
                        ResponseJSONObject json = new Gson().fromJson(body, ResponseJSONObject.class);
                        ErrorUserMessage msg = json.getMessage();

                        String message = "";
                        if (msg.getEmail() != null && msg.getEmail().length() > 0) {
                            message = msg.getEmail();
                        }

                        if (msg.getPassword_confirmation() != null && msg.getPassword_confirmation().length() > 0) {
                            message += " " + msg.getPassword_confirmation();
                        }
                        new Session(context).setString("signupStatus", "Error: " + message);
                        onFailed.run();
                    }
                }
            });
        } catch (Exception e) {
            new Session(context).setString("signupStatus", "Error: " + e.getMessage());
            onFailed.run();
        }
    }

    public boolean isLoggedIn () {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        return currentUser != null;
    }

    public User getUser() {
        User user = new User();
        user.setId(mAuth.getCurrentUser().getUid());
        user.setName(mAuth.getCurrentUser().getDisplayName());
        user.setEmail(mAuth.getCurrentUser().getEmail());
        return user;
    }

    public void login(Context activity, String email, String password, Runnable onSuccess, Runnable onFailed) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser data = mAuth.getCurrentUser();
                System.out.println(data.getDisplayName());
                new Session(activity).setId(data.getUid());
                new Session(activity).setName(data.getDisplayName());
                new Session(activity).setEmail(data.getEmail());

                DatabaseReference user = new Database().instance.child("users").child(data.getUid());
                user.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        new Session(activity).setLevel(snapshot.child("level").getValue(String.class));
                        new Session(activity).setString("loginStatus", "Login success");
                        onSuccess.run();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        new Session(activity).setString("loginStatus", "error: " + error.getMessage());
                        onFailed.run();
                    }
                });
            } else {
                new Session(activity).setString("loginStatus", "error: " + task.getException().getMessage());
                onFailed.run();
            }
        }).addOnFailureListener(e -> {
            new Session(activity).setString("loginStatus", "error: " + e.getMessage());
            onFailed.run();
        });
    }

    public void logout(Context context) {
        mAuth.signOut();
        new Session(context).setId("");
        new Session(context).setName("");
        new Session(context).setEmail("");
        new Session(context).setLevel("");
    }
}
