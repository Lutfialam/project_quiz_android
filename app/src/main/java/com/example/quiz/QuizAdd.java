package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quiz.models.Question;
import com.example.quiz.models.Quiz;
import com.example.quiz.services.QuizService;

import java.util.ArrayList;
import java.util.List;

public class QuizAdd extends AppCompatActivity {
    private final Context context = this;
    private ArrayAdapter<?> adapter2 = null;
    private Spinner answer_data;
    private int question_number = 1;
    private ImageButton add_question;
    private Button quiz_add_buton_submit;
    private LinearLayout quiz_add_question_layout_root;
    private List<Question> questions = new ArrayList<Question>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_add);

        quiz_add_question_layout_root = (LinearLayout) findViewById(R.id.quiz_add_question_layout_root);
        add_question();

        add_question = (ImageButton) findViewById(R.id.quiz_add_question_add);
        add_question.setOnClickListener(v -> {
            add_question();
        });

        quiz_add_buton_submit = (Button) findViewById(R.id.quiz_add_button_submit);
        quiz_add_buton_submit.setOnClickListener(v -> {
            submit();
        });

        ImageButton button_back = (ImageButton) findViewById(R.id.quiz_add_back_home);
        button_back.setOnClickListener(v -> {
            startActivity(new Intent(context, MainActivity.class));
        });
    }

    private void add_question() {
        ViewGroup vg = (ViewGroup) getLayoutInflater().inflate(R.layout.quiz_add_question_layout, null);
        quiz_add_question_layout_root.addView(vg);
        TextView quiz_add_question_text = (TextView) vg.findViewById(R.id.question_text);
        quiz_add_question_text.setText("Question number " + question_number);

        String[] answer_datas = {"A", "B", "C", "D"};
        answer_data = (Spinner) vg.findViewById(R.id.quiz_add_answer_data);
        adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, answer_datas);
        answer_data.setAdapter(adapter2);
        question_number++;

        Toast.makeText(context, "New question is added with number: " + (question_number - 1), Toast.LENGTH_SHORT).show();
    }

    private void submit() {
        EditText quiz_name = (EditText) findViewById(R.id.quiz_add_quiz_name);
        EditText quiz_description = (EditText) findViewById(R.id.quiz_add_quiz_description);
        EditText quiz_time = (EditText) findViewById(R.id.quiz_add_quiz_time);

        if (all_item_is_not_null()) {
            try {
                Quiz quiz = new Quiz();
                quiz.setName(quiz_name.getText().toString());
                quiz.setDescription(quiz_description.getText().toString());
                quiz.setTime(Integer.parseInt(quiz_time.getText().toString()));

                for (int i = 0; i < quiz_add_question_layout_root.getChildCount(); i++) {
                    ViewGroup child = (ViewGroup) quiz_add_question_layout_root.getChildAt(i);
                    EditText question = (EditText) child.findViewById(R.id.quiz_add_question_text);
                    EditText answer_a = (EditText) child.findViewById(R.id.quiz_add_question_answer_a);
                    EditText answer_b = (EditText) child.findViewById(R.id.quiz_add_question_answer_b);
                    EditText answer_c = (EditText) child.findViewById(R.id.quiz_add_question_answer_c);
                    EditText answer_d = (EditText) child.findViewById(R.id.quiz_add_question_answer_d);
                    Spinner correct_answer = (Spinner) child.findViewById(R.id.quiz_add_answer_data);

                    Question question1 = new Question();
                    question1.setQuestion(question.getText().toString());
                    question1.setFirst_choice(answer_a.getText().toString());
                    question1.setSecond_choice(answer_b.getText().toString());
                    question1.setThird_choice(answer_c.getText().toString());
                    question1.setFourth_choice(answer_d.getText().toString());
                    question1.setAnswer(correct_answer.getSelectedItem().toString());

                    questions.add(question1);
                }

                quiz.setQuestions(questions);
                new QuizService().store(context, quiz);

                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                Log.e("error", e.getMessage());
            }
        }
    }

    private boolean all_item_is_not_null() {
        EditText quiz_name = (EditText) findViewById(R.id.quiz_add_quiz_name);
        EditText quiz_description = (EditText) findViewById(R.id.quiz_add_quiz_description);
        EditText quiz_time = (EditText) findViewById(R.id.quiz_add_quiz_time);

        if (quiz_name.getText().toString().length() <= 0) {
            quiz_name.setError("Quiz name field is required");
            return false;
        } else if (quiz_description.getText().toString().length() <= 0) {
            quiz_description.setError("Quiz description field is required");
            return false;
        } else if (quiz_time.getText().toString().length() <= 0) {
            quiz_time.setError("Quiz time field is required");
            return false;
        }

        for (int i = 0; i < quiz_add_question_layout_root.getChildCount(); i++) {
            ViewGroup child = (ViewGroup) quiz_add_question_layout_root.getChildAt(i);
            EditText question = (EditText) child.findViewById(R.id.quiz_add_question_text);
            EditText answer_a = (EditText) child.findViewById(R.id.quiz_add_question_answer_a);
            EditText answer_b = (EditText) child.findViewById(R.id.quiz_add_question_answer_b);
            EditText answer_c = (EditText) child.findViewById(R.id.quiz_add_question_answer_c);
            EditText answer_d = (EditText) child.findViewById(R.id.quiz_add_question_answer_d);

            if (question.getText().toString().length() <= 0) {
                question.setError("Question field is required");
                return false;
            } else if(answer_a.getText().toString().length() <= 0) {
                answer_a.setError("Question field is required");
                return false;
            } else if(answer_b.getText().toString().length() <= 0) {
                answer_b.setError("Question field is required");
                return false;
            } else if(answer_c.getText().toString().length() <= 0) {
                answer_c.setError("Question field is required");
                return false;
            } else if(answer_d.getText().toString().length() <= 0) {
                answer_d.setError("Question field is required");
                return false;
            }
        }

        return true;
    }
}