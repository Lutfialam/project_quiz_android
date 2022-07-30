package com.example.quiz.services;

import android.content.Context;

import com.example.quiz.helpers.Session;
import com.example.quiz.models.Question;
import com.example.quiz.models.Quiz;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;

public class QuizService extends Instance {
    CountDownLatch cd = new CountDownLatch(0);
    Type listType = new TypeToken<ArrayList<Quiz>>(){}.getType();

    public List<Quiz> getAllQuiz(Context context) {
        List<Quiz> quiz = new ArrayList<Quiz>();
        try {
            get(context, "/quiz", new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println(e.getMessage());
                    cd.countDown();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    new Session(context).setString("quizList", response.body().string());
                    cd.countDown();
                }
            });

            quiz = new Gson().fromJson(new Session(context).getString("quizList"), listType);
            cd.await();
            return quiz;
        } catch (Exception e) {
            System.out.println(e);
            return quiz;
        }
    }

    public List<Quiz> getFewQuiz(Context context, int limit) {
        List<Quiz> quiz = new ArrayList<Quiz>();
        quiz = getAllQuiz(context).subList(0, limit);
        return quiz;
    }
    
    public List<Quiz> getQuizByName(Context context, String name) {
        List<Quiz> quiz = new ArrayList<Quiz>();
        for (Quiz item : getAllQuiz(context)) {
            if (item.getName().toLowerCase().contains(name.toLowerCase())) {
                quiz.add(item);
                System.out.println("ada" + item.getName());
            }
        }
        return quiz;
    }

    public Quiz getQuiz(Context context, String id) {
        try {
            get(context, "/quiz/" + id, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println(e.getMessage());
                    cd.countDown();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    new Session(context).setString("quiz", response.body().string());
                    cd.countDown();
                }
            });

            System.out.println(new Session(context).getString("quiz"));
            Quiz quiz = new Gson().fromJson(new Session(context).getString("quiz"), Quiz.class);

            cd.await();
            return quiz;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public long store(Context context, Quiz quiz) {
        try {
            JSONObject data = new JSONObject();
            data.put("name", quiz.getName());
            data.put("time", quiz.getTime());
            data.put("questions", new Gson().toJson(quiz.getQuestions()));
            data.put("description", quiz.getDescription());

            post(context, "/quiz", data.toString(), new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    cd.countDown();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
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

    public void update(Context context, Quiz quiz) {
        try {
            JSONObject data = new JSONObject();
            data.put("id", quiz.getId());
            data.put("name", quiz.getName());
            data.put("time", quiz.getTime());
            data.put("questions", new Gson().toJson(quiz.getQuestions()));
            data.put("description", quiz.getDescription());

            put(context, "/quiz/" + quiz.getId(), data.toString(), new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println(e.getMessage());
                    cd.countDown();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    cd.countDown();
                }
            });
            cd.await();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public boolean destroy(Context context, String id) {
        delete(context, "/quiz/" + id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
        return true;
    }
}
