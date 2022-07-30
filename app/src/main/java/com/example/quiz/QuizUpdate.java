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

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class QuizUpdate extends AppCompatActivity {
    private final Context context = this;
    private ArrayAdapter<?> adapter2 = null;
    Intent intent;
    private Spinner answer_data;
    private int question_number = 1;
    private ImageButton add_question;
    private Button quiz_add_buton_submit;
    private LinearLayout quiz_add_question_layout_root;
    private List<Question> questions = new ArrayList<Question>();
    private Quiz quizData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_update);
        intent = getIntent();

        quizData = new QuizService().getQuiz(context, intent.getStringExtra("data_id"));

        try {
            questions = quizData.getQuestions();
            quiz_add_question_layout_root = (LinearLayout) findViewById(R.id.quiz_add_question_layout_root);
            // add_question(); ganti loop replace
            set_value_to_all_input();

            add_question = (ImageButton) findViewById(R.id.quiz_add_question_add);
            add_question.setOnClickListener(v -> {
                add_question();
            });

            quiz_add_buton_submit = (Button) findViewById(R.id.quiz_add_button_submit);
            quiz_add_buton_submit.setOnClickListener(v -> {
                submit();
            });

            ImageButton button_back = (ImageButton) findViewById(R.id.quiz_update_back_home);
            button_back.setOnClickListener(v -> {
                startActivity(new Intent(context, MainActivity.class));
            });
        } catch (Exception e) {
            Log.e("error", "errr: " + e.getMessage());
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
        }
    }

    private void add_question() {
        ViewGroup vg = (ViewGroup) getLayoutInflater().inflate(R.layout.quiz_add_question_layout, null);
        quiz_add_question_layout_root.addView(vg);
        TextView quiz_add_question_text = (TextView) vg.findViewById(R.id.question_text);
        quiz_add_question_text.setText("Question number " + question_number);

        String[] answers = {"A", "B", "C", "D"};
        answer_data = (Spinner) vg.findViewById(R.id.quiz_add_answer_data);
        adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, answers);
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
                quiz.setId(intent.getStringExtra("data_id"));
                quiz.setName(quiz_name.getText().toString());
                quiz.setDescription(quiz_description.getText().toString());
                quiz.setTime(Integer.parseInt(quiz_time.getText().toString()));

                List<Question> questionList = new ArrayList<Question>();
                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < quiz_add_question_layout_root.getChildCount(); i++) {
                    ViewGroup child = (ViewGroup) quiz_add_question_layout_root.getChildAt(i);
                    EditText id = (EditText) child.findViewById(R.id.question_update_id);
                    if (id.getText().toString().length() >= 1) {
                        EditText question = (EditText) child.findViewById(R.id.quiz_add_question_text);
                        EditText answer_a = (EditText) child.findViewById(R.id.quiz_add_question_answer_a);
                        EditText answer_b = (EditText) child.findViewById(R.id.quiz_add_question_answer_b);
                        EditText answer_c = (EditText) child.findViewById(R.id.quiz_add_question_answer_c);
                        EditText answer_d = (EditText) child.findViewById(R.id.quiz_add_question_answer_d);
                        Spinner correct_answer = (Spinner) child.findViewById(R.id.quiz_add_answer_data);

                        com.example.quiz.models.Question question1 = new Question();
                        question1.setId(id.getText().toString());
                        question1.setQuestion(question.getText().toString());
                        question1.setFirst_choice(answer_a.getText().toString());
                        question1.setSecond_choice(answer_b.getText().toString());
                        question1.setThird_choice(answer_c.getText().toString());
                        question1.setFourth_choice(answer_d.getText().toString());
                        question1.setAnswer(correct_answer.getSelectedItem().toString());

                        jsonArray.put(question1);
                        questionList.add(question1);
                    } else {
                        EditText question = (EditText) child.findViewById(R.id.quiz_add_question_text);
                        EditText answer_a = (EditText) child.findViewById(R.id.quiz_add_question_answer_a);
                        EditText answer_b = (EditText) child.findViewById(R.id.quiz_add_question_answer_b);
                        EditText answer_c = (EditText) child.findViewById(R.id.quiz_add_question_answer_c);
                        EditText answer_d = (EditText) child.findViewById(R.id.quiz_add_question_answer_d);
                        Spinner correct_answer = (Spinner) child.findViewById(R.id.quiz_add_answer_data);

                        com.example.quiz.models.Question question1 = new Question();
                        question1.setQuestion(question.getText().toString());
                        question1.setFirst_choice(answer_a.getText().toString());
                        question1.setSecond_choice(answer_b.getText().toString());
                        question1.setThird_choice(answer_c.getText().toString());
                        question1.setFourth_choice(answer_d.getText().toString());
                        question1.setAnswer(correct_answer.getSelectedItem().toString());

                        jsonArray.put(question1);
                        questionList.add(question1);
                    }
                }

                quiz.setQuestions(questionList);
                new QuizService().update(context, quiz);

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

    private void set_value_to_all_input() {
        EditText quiz_name = (EditText) findViewById(R.id.quiz_add_quiz_name);
        EditText quiz_description = (EditText) findViewById(R.id.quiz_add_quiz_description);
        EditText quiz_time = (EditText) findViewById(R.id.quiz_add_quiz_time);

        quiz_name.setText(intent.getStringExtra("data_name"));
        quiz_description.setText(intent.getStringExtra("data_description"));
        quiz_time.setText(String.valueOf(intent.getIntExtra("data_time", 1)));

        try {
            for (Question q : questions) {
                ViewGroup vg = (ViewGroup) getLayoutInflater().inflate(R.layout.quiz_add_question_layout, null);
                quiz_add_question_layout_root.addView(vg);
                TextView quiz_add_question_text = (TextView) vg.findViewById(R.id.question_text);
                quiz_add_question_text.setText("Question number " + question_number);

                EditText question_id = (EditText) vg.findViewById(R.id.question_update_id);
                EditText question = (EditText) vg.findViewById(R.id.quiz_add_question_text);
                EditText answer_a = (EditText) vg.findViewById(R.id.quiz_add_question_answer_a);
                EditText answer_b = (EditText) vg.findViewById(R.id.quiz_add_question_answer_b);
                EditText answer_c = (EditText) vg.findViewById(R.id.quiz_add_question_answer_c);
                EditText answer_d = (EditText) vg.findViewById(R.id.quiz_add_question_answer_d);

                question_id.setText(q.getId());
                question.setText(q.getQuestion());
                answer_a.setText(q.getFirst_choice());
                answer_b.setText(q.getSecond_choice());
                answer_c.setText(q.getThird_choice());
                answer_d.setText(q.getFourth_choice());

                String[] answer_datas = {"A", "B", "C", "D"};
                answer_data = (Spinner) vg.findViewById(R.id.quiz_add_answer_data);
                adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, answer_datas);
                answer_data.setAdapter(adapter2);

                if (q.getAnswer().equals("A")) {
                    answer_data.setSelection(0);
                } else if (q.getAnswer().equals("B")) {
                    answer_data.setSelection(1);
                } else if (q.getAnswer().equals("C")) {
                    answer_data.setSelection(2);
                } else if (q.getAnswer().equals("D")) {
                    answer_data.setSelection(3);
                }

                question_number++;
            }
        } catch (Exception e) {
            Log.e("error", "errc: " + e.getMessage());
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
        }
    }
}