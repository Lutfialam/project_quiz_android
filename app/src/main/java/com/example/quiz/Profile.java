package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.quiz.helpers.Session;
import com.example.quiz.models.Quiz;
import com.example.quiz.models.RecentQuiz;
import com.example.quiz.models.User;
import com.example.quiz.services.AuthService;
import com.example.quiz.services.RecentQuizService;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

public class Profile extends AppCompatActivity {
    private FlexboxLayout not_have_recent_quiz;
    private LinearLayout root_list_recent_quiz;
    private final Context context = this;
    private ImageButton imageButton;
    private ImageButton imageButton2;
    private Button logout;
    private Button try_quiz;
    private TextView initial;
    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = (TextView) findViewById(R.id.profile_name);
        initial = (TextView) findViewById(R.id.profile_avatar);
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
        root_list_recent_quiz = (LinearLayout) findViewById(R.id.root_list_recent_quiz);
        not_have_recent_quiz = (FlexboxLayout) findViewById(R.id.not_have_recent_quiz);
        logout = (Button) findViewById(R.id.logout);
        try_quiz = (Button) findViewById(R.id.try_quiz);

        Session session = new Session(this);
        Log.e("name", session.getName());
        System.out.println(session.getName());
        name.setText(session.getName());
        initial.setText(session.getInitialName());

        List<RecentQuiz> recentQuiz = new RecentQuizService().getFewQuiz(context, 5);
        if (recentQuiz != null && recentQuiz.size() > 0) {
            for (RecentQuiz q : recentQuiz) {
                ViewGroup vg = (ViewGroup) getLayoutInflater().inflate(R.layout.card_recent_quiz, null);
                root_list_recent_quiz.addView(vg);

                TextView text = (TextView) vg.findViewById(R.id.short_name_card);
                TextView text_name = (TextView) vg.findViewById(R.id.name_card);
                TextView text_total = (TextView) vg.findViewById(R.id.question_total_card);
                TextView text_timestamp = (TextView) vg.findViewById(R.id.question_timestamp);

                text.setText(q.getQuiz().getName().substring(0, 2).toUpperCase());
                text_name.setText(q.getQuiz().getName());
                text_total.setText("Correct question: "+q.getScore());
                text_timestamp.setText(q.getCreated_at());
            }
            not_have_recent_quiz.setVisibility(View.GONE);
            root_list_recent_quiz.setVisibility(View.VISIBLE);
        } else {
            root_list_recent_quiz.setVisibility(View.GONE);
            not_have_recent_quiz.setVisibility(View.VISIBLE);
        }

        onClickListener();
    }

    private void onClickListener() {
        logout.setOnClickListener(v -> {
            new AuthService().logout(context);
            Intent intent = new Intent(context, Login.class);
            startActivity(intent);
        });

        try_quiz.setOnClickListener(v -> {
            Intent intent = new Intent(context, MoreQuiz.class);
            startActivity(intent);
        });

        imageButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
        });

        imageButton2.setOnClickListener(v -> {
            Intent intent = new Intent(context, MoreQuiz.class);
            startActivity(intent);
        });
    }
}