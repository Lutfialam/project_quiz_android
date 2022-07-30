package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quiz.helpers.Session;
import com.example.quiz.models.Quiz;
import com.example.quiz.services.QuizService;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    final Context context = this;
    private LinearLayout root_list_newest_quiz;
    private LinearLayout quiz_code_card;
    private ImageButton add_quiz;
    private ImageButton imageButton2;
    private ImageButton imageButton3;
    private TextView avatar;
    private EditText quiz_code;
    private Button quiz_button;
    private Button home_more_quiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        Session session = new Session(this);
        root_list_newest_quiz = (LinearLayout) findViewById(R.id.root_list_newest_quiz);
        quiz_code_card = (LinearLayout) findViewById(R.id.quiz_code_card);
        add_quiz = (ImageButton) findViewById(R.id.add_quiz);
        quiz_code = (EditText) findViewById(R.id.quiz_code);
        quiz_button = (Button) findViewById(R.id.quiz_code_button);


        imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
        imageButton3 = (ImageButton) findViewById(R.id.imageButton3);
        home_more_quiz = (Button) findViewById(R.id.home_more_quiz);

        avatar = (TextView) findViewById(R.id.avatar);
        avatar.setText(session.getInitialName().toUpperCase());

        if (session.getLevel().equalsIgnoreCase("admin")) {
            add_quiz.setVisibility(View.VISIBLE);
        } else {
            add_quiz.setVisibility(View.GONE);
        }

        getData();
        onClickListener();
    }

    private void getData() {
        boolean run = true;
        try {
            List<Quiz> quiz = new QuizService().getFewQuiz(context, 4);

            if (quiz != null) {
                for (Quiz q : quiz) {
                    String name = q.getName();
                    String total = "time: " + q.getTime() + " minutes";

                    ViewGroup vg = (ViewGroup) getLayoutInflater().inflate(R.layout.card_layout, null);
                    root_list_newest_quiz.addView(vg);
                    vg.setOnClickListener(v -> {
                        Intent intent = new Intent(this, QuizDetail.class);
                        intent.putExtra("data_id", q.getId());
                        intent.putExtra("data_name", q.getName());
                        intent.putExtra("data_description", q.getDescription());
                        intent.putExtra("data_time_in_minute", q.getTime());
                        startActivity(intent);
                    });
                    TextView text = (TextView) vg.findViewById(R.id.short_name_card);
                    TextView text_name = (TextView) vg.findViewById(R.id.name_card);
                    TextView text_total = (TextView) vg.findViewById(R.id.question_total_card);

                    if (new Session(context).getLevel().equalsIgnoreCase("admin")) {
                        ImageButton more_action = (ImageButton) vg.findViewById(R.id.card_more);
                        PopupMenu dropDownMenu = new PopupMenu(getApplicationContext(), more_action);
                        Menu menu = dropDownMenu.getMenu();
                        menu.add(0, 0, 0, "Edit data");
                        menu.add(0, 1, 0, "delete data");
                        dropDownMenu.setOnMenuItemClickListener(item -> {
                            if (item.getItemId() == 1) {
                                ConfirmDialog confirmDialog = new ConfirmDialog(context);
                                confirmDialog.setConfirmAction(() -> {
                                    QuizService quizService = new QuizService();
                                    quizService.destroy(context, q.getId());
                                });
                                confirmDialog.show();
                            } else {
                                Intent intent = new Intent(context, QuizUpdate.class);
                                intent.putExtra("data_id", q.getId());
                                intent.putExtra("data_name", q.getName());
                                intent.putExtra("data_description", q.getDescription());
                                intent.putExtra("data_time", q.getTime());
                                startActivity(intent);
                            }
                            return true;
                        });

                        more_action.setOnClickListener(v -> {
                            dropDownMenu.show();
                        });
                    } else {
                        ImageButton more_action = (ImageButton) vg.findViewById(R.id.card_more);
                        more_action.setVisibility(View.GONE);
                    }

                    text.setText(name.substring(0, 2).toUpperCase());
                    text_name.setText(name);
                    text_total.setText(total);
                }
            }
        } catch (Exception e) {
            Log.e("error", "er: " + e.getMessage());
        }
    }

    private void onClickListener() {
        imageButton2.setOnClickListener(v -> {
            Intent intent = new Intent(context, MoreQuiz.class);
            startActivity(intent);
        });

        imageButton3.setOnClickListener(v -> {
            Intent intent = new Intent(context, Profile.class);
            startActivity(intent);
        });

        home_more_quiz.setOnClickListener(v -> {
            Intent intent = new Intent(context, MoreQuiz.class);
            startActivity(intent);
        });

        quiz_button.setOnClickListener(v -> {
            Quiz quiz = new QuizService().getQuiz(context, quiz_code.getText().toString());

            if (quiz != null) {
                Intent intent = new Intent(this, QuizDetail.class);
                intent.putExtra("data_id", quiz.getId());
                intent.putExtra("data_name", quiz.getName());
                intent.putExtra("data_description", quiz.getDescription());
                intent.putExtra("data_time_in_minute", quiz.getTime());
                startActivity(intent);
            } else {
                Toast.makeText(this, "Quiz code is invalid! try another code", Toast.LENGTH_SHORT).show();
            }
        });

        add_quiz.setOnClickListener(v -> {
            Intent intent = new Intent(context, QuizAdd.class);
            startActivity(intent);
        });
    }
}