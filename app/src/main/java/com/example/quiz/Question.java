package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.quiz.helpers.Session;
import com.example.quiz.models.QuestionRes;
import com.example.quiz.models.Quiz;
import com.example.quiz.models.RecentQuiz;
import com.example.quiz.services.QuizService;
import com.example.quiz.services.RecentQuizService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Question extends AppCompatActivity {
    List<com.example.quiz.models.Question> questions;
    private final Context context = this;
    private int complete_question_result = 0;
    private CountDownTimer countDownTimer;
    private TextView countText;
    private LinearLayout ly;
    private boolean finish = false;
    private String quiz_id;

    @Override
    public void onBackPressed() {
        finish = true;
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        complete_question_result = 0;
        ly = (LinearLayout) findViewById(R.id.question_list_root);
        countText = (TextView) findViewById(R.id.count);

        Intent intent = getIntent();
        quiz_id = intent.getStringExtra("quiz_id");
        System.out.println("Quiz id: " + quiz_id);
        Quiz quiz = new QuizService().getQuiz(context, quiz_id);
        questions = quiz.getQuestions();

        if (questions != null) {
            int i = 1;
            for (com.example.quiz.models.Question q : questions) {
                ViewGroup vg = (ViewGroup) getLayoutInflater().inflate(R.layout.question_layout, null);
                ly.addView(vg);

                TextView question_text = (TextView) vg.findViewById(R.id.question_text);
                RadioButton first_choice = (RadioButton) vg.findViewById(R.id.question_first_choice);
                RadioButton second_choice = (RadioButton) vg.findViewById(R.id.question_second_choice);
                RadioButton third_choice = (RadioButton) vg.findViewById(R.id.question_third_choice);
                RadioButton fourth_choice = (RadioButton) vg.findViewById(R.id.question_fourth_choice);
                View divider = (View) vg.findViewById(R.id.question_divider);

                String quest = i+". " + q.getQuestion();
                question_text.setText(quest);
                first_choice.setText(q.getFirst_choice());
                second_choice.setText(q.getSecond_choice());
                third_choice.setText(q.getThird_choice());
                fourth_choice.setText(q.getFourth_choice());
                i++;

                if(i == questions.size() + 1) {
                    divider.setVisibility(View.INVISIBLE);
                }
            }

            ViewGroup question_button = (ViewGroup) getLayoutInflater().inflate(R.layout.question_button_layout, null);
            Button finish_button = (Button) question_button.findViewById(R.id.question_finish_button);

            finish_button.setOnClickListener(v -> {
                finish = true;
                complete_question_result = 0;
                calculateAndStoreQuiz();
            });

            ly.addView(question_button);
            setCountDown(intent.getIntExtra("quiz_time", 1));
        }
    }

    private void setCountDown (int timeInMinutes) {
        countDownTimer = new CountDownTimer(timeInMinutes * 60000, 1000) {
            public void onTick(long millisUntilFinished) {
                int seconds = (int) millisUntilFinished / 1000;
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                seconds = seconds % 60;

                countText.setText(hours + ":" + minutes + ":" + seconds);
                if (hours == 0 && minutes <= 5) {
                    countText.setBackground(ContextCompat.getDrawable(context, R.drawable.button_danger));
                    countText.setTextColor(ContextCompat.getColor(context, R.color.white));
                }
            }

            public void onFinish() {
                complete_question_result = 0;
                if (!finish) calculateAndStoreQuiz();
            }
        }.start();
    }

    private void calculateAndStoreQuiz() {
        Locale locale = new Locale("id", "ID");
        SimpleDateFormat now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        List<QuestionRes> qr = new ArrayList<QuestionRes>();

        for (int j = 0; j < ly.getChildCount(); j++) {
            ViewGroup child = (ViewGroup) ly.getChildAt(j);
            for (int k = 0; k < child.getChildCount(); k++) {
                if (child.getChildAt(k) instanceof RadioGroup) {
                    RadioGroup rg = (RadioGroup) child.getChildAt(k);
                    RadioButton rd = (RadioButton) rg.findViewById(rg.getCheckedRadioButtonId());

                    int position = 65 + rg.indexOfChild(rd);
                    char c = (char) position;
                    String result = String.valueOf(c);

                    qr.add(new QuestionRes(j, result));
                    if (questions.get(j).getAnswer().equals(result)) {
                        complete_question_result++;
                        Log.e("eeee", complete_question_result + " " + questions.get(j).getAnswer() + " " + result);
                    }
                }
            }
        }

        RecentQuizService recentQuizService = new RecentQuizService();
        recentQuizService.store(context, quiz_id, qr);

        Intent intent1 = new Intent(context, QuestionResult.class);
        intent1.putExtra("data_correct", complete_question_result);
        intent1.putExtra("data_total", questions.size());
        startActivity(intent1);
    }
}