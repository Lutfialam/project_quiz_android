package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.quiz.models.Quiz;

public class QuizDetail extends AppCompatActivity {
    final Context context = this;
    private Quiz quiz;
    private Button button;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_detail);

        text = (TextView) findViewById(R.id.quiz_detail_text);
        button = (Button) findViewById(R.id.quiz_detail_button);

        Intent intent = getIntent();
        quiz = new Quiz();
        quiz.setId(intent.getStringExtra("data_id"));
        quiz.setName(intent.getStringExtra("data_name"));
        quiz.setDescription(intent.getStringExtra("data_description"));
        quiz.setTime(intent.getIntExtra("data_time_in_minute", 1));

        String detail = "You will do the "+quiz.getName()+" quiz in "+quiz.getTime()+" minutes. make sure you are ready to do it!";
        text.setText(detail);
        button.setOnClickListener(v -> {
            Intent intent1 = new Intent(this, Question.class);
            intent1.putExtra("quiz_id", quiz.getId());
            intent1.putExtra("quiz_time", quiz.getTime());
            startActivity(intent1);
        });

        Button button_back = (Button) findViewById(R.id.quiz_back_button);
        button_back.setOnClickListener(v -> {
            startActivity(new Intent(context, MainActivity.class));
        });
    }
}