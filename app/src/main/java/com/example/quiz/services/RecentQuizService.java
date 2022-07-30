package com.example.quiz.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.quiz.helpers.DataHelper;
import com.example.quiz.helpers.Session;
import com.example.quiz.models.QuestionRes;
import com.example.quiz.models.Quiz;
import com.example.quiz.models.RecentQuiz;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

class RecentResponse {
    List<RecentQuiz> data;
    String status;

    public List<RecentQuiz> getData() {
        return data;
    }

    public void setData(List<RecentQuiz> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

public class RecentQuizService extends Instance {
    Type listType = new TypeToken<ArrayList<RecentQuiz>>(){}.getType();

    public long store(Context context, String quiz_id, List<QuestionRes> qr) {
        try {
            JSONObject data = new JSONObject();
            data.put("quiz_id", quiz_id);
            data.put("questions", new Gson().toJson(qr));

            post(context, "/history", data.toString(), new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println(e);
                    cd.countDown();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    System.out.println(response.message());
                    cd.countDown();
                }
            });

            cd.await();
            return 1;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return 0;
        }
    }

    public List<RecentQuiz> getFewQuiz(Context context, int limit) {
        List<RecentQuiz> quiz = new ArrayList<RecentQuiz>();
        try {
            get(context, "/history", new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println(e.getMessage());
                    cd.countDown();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String body = response.body().string();
                    new Session(context).setString("recentQuizList", body);
                    cd.countDown();
                }
            });

            RecentResponse recent = new Gson().fromJson(new Session(context).getString("recentQuizList"), RecentResponse.class);
            cd.await();
            return recent.data;
        } catch (Exception e) {
            System.out.println(e);
            return quiz;
        }
    }
}
