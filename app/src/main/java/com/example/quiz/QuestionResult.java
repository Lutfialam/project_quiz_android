package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class QuestionResult extends AppCompatActivity {
    private TextView textResult;
    private Button back_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_result);
        Intent intent = getIntent();

        textResult = (TextView) findViewById(R.id.question_result);
        back_home = (Button) findViewById(R.id.question_back_home);

        int correct_result = intent.getIntExtra("data_correct", 0);
        int total_result = intent.getIntExtra("data_total", 0);
        textResult.setText(correct_result + " from " + total_result);

        back_home.setOnClickListener(v -> {
            Intent intent1 = new Intent(this, MainActivity.class);
            startActivity(intent1);
        });
    }
}