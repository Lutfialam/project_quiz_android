package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MoreQuiz extends AppCompatActivity {
    final Context context = this;
    private ImageButton imageButton;
    private ImageButton imageButton3;
    private LinearLayout ly;
    private EditText more_quiz_search;
    private Button more_quiz_search_button;
    private FlexboxLayout more_quiz_not_found;
    CountDownLatch cd = new CountDownLatch(0);
    List<Quiz> quiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_quiz);

        ly = (LinearLayout) findViewById(R.id.root_list);
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton3 = (ImageButton) findViewById(R.id.imageButton3);
        more_quiz_search = (EditText) findViewById(R.id.more_quiz_search);
        more_quiz_search_button = (Button) findViewById(R.id.more_quiz_search_button);
        more_quiz_not_found = (FlexboxLayout) findViewById(R.id.more_quiz_not_found);

        try {
            quiz = new QuizService().getAllQuiz(context);
            cd.countDown();
            cd.await();
            set_quiz();
        } catch (Exception e) {
            Toast toast = new Toast(context).makeText(context, e.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
        }

        more_quiz_search_button.setOnClickListener(v -> {
            String search_value = more_quiz_search.getText().toString();

            if (search_value.length() > 0) {
                quiz = new QuizService().getQuizByName(context, search_value);
                if (quiz.size() <= 0) {
                    more_quiz_not_found.setVisibility(View.VISIBLE);
                    ly.setVisibility(View.GONE);
                } else {
                    set_quiz();
                    more_quiz_not_found.setVisibility(View.GONE);
                    ly.setVisibility(View.VISIBLE);
                }
            } else {
                quiz = new QuizService().getAllQuiz(context);
                set_quiz();
            }
        });

        imageButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
        });

        imageButton3.setOnClickListener(v -> {
            Intent intent = new Intent(context, Profile.class);
            startActivity(intent);
        });
    }

    private void set_quiz() {
        System.out.println("masuk");
        if (quiz.size() > 0) {
            System.out.println("lebih dari 0 quiz nya");
            if (ly != null && ly.getChildCount() > 0) ly.removeAllViews();
            for (Quiz q : quiz) {
                String name = q.getName();
                String total = "time: " + q.getTime() + " minutes";

                ViewGroup vg = (ViewGroup) getLayoutInflater().inflate(R.layout.card_layout, null);
                ly.addView(vg);
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

                text.setText(name.substring(0, 2).toUpperCase());
                text_name.setText(name);
                text_total.setText(total);

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
            }
        } else {
            more_quiz_not_found.setVisibility(View.GONE);
            ly.setVisibility(View.VISIBLE);
        }
    }
}