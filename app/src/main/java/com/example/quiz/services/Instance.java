package com.example.quiz.services;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.quiz.Login;
import com.example.quiz.MainActivity;
import com.example.quiz.helpers.Session;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Instance {
    OkHttpClient client = new OkHttpClient();
    CountDownLatch cd = new CountDownLatch(0);
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public String getToken(Context context) {
        try {
            Session session = new Session(context);
            mAuth.getCurrentUser().getIdToken(true).addOnCompleteListener(task -> {
                if (task.isComplete()) {
                    task.addOnCompleteListener(task1 -> {
                        session.setString("token", task.getResult().getToken());
                        cd.countDown();
                    });

                    task.addOnFailureListener(e -> {
                        Intent intent = new Intent(context, Login.class);
                        context.startActivity(intent);
                        cd.countDown();
                    });
                }
            });
            cd.await();
            return session.getString("token");
        } catch (Exception e) {
            System.out.println("failed on instance");
            System.out.println(e.getMessage());
            return "failed";
        }
    }

    public Request.Builder instance(Context context, String... path) {
        String url = "https://project-final-quiz.herokuapp.com/api" + path[0];
        return new Request.Builder().addHeader("Authorization", getToken(context)).url(url);
    }

    public Request.Builder defaultInstance (String ...path) {
        String url = "https://project-final-quiz.herokuapp.com/api" + path[0];
        return new Request.Builder().url(url);
    }

    public void defaultPost(String url, String json, Callback callback) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = defaultInstance(url).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public void defaultGet(String url, Callback callback) {
        Request request = defaultInstance(url).build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public void get(Context context, String url, Callback callback) {
        Request request = instance(context, url).build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public void post(Context context, String url, String json, Callback callback) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = instance(context, url).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public void put(Context context, String url, String json, Callback callback) {
        System.out.println("put method");
        RequestBody body = RequestBody.create(JSON, json);
        Request request = instance(context, url).put(body).build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public void delete(Context context, String url, Callback callback) {
        Request request = instance(context, url).delete().build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }
}
