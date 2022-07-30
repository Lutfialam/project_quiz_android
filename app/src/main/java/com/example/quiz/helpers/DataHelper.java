package com.example.quiz.helpers;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.quiz.models.Question;

import java.util.ArrayList;
import java.util.List;

public class DataHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "quiz";
    private static final int DATABASE_VERSION = 1;
    private List<Question> questions;

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        create_user_table(db);
        create_category_table(db);
        create_quiz_table(db);
        create_question_table(db);
        create_recent_quiz_table(db);
        insert_data(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        db.execSQL("DROP TABLE IF EXISTS questions");
        db.execSQL("DROP TABLE IF EXISTS quiz");
        db.execSQL("DROP TABLE IF EXISTS categories");
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS recent_quiz");
        onCreate(db);
    }

    private void create_user_table(SQLiteDatabase db) {
        String user_table = "CREATE TABLE IF NOT EXISTS users(id INTEGER PRIMARY KEY, name TEXT, email TEXT, password TEXT, level TEXT);";
        db.execSQL(user_table);
    }

    private void create_category_table(SQLiteDatabase db) {
        String category_table = "CREATE TABLE IF NOT EXISTS categories(id INTEGER PRIMARY KEY, name TEXT, description TEXT);";
        db.execSQL(category_table);
    }

    private void create_quiz_table(SQLiteDatabase db) {
        String quiz_table = "CREATE TABLE IF NOT EXISTS quiz(id INTEGER PRIMARY KEY, category_id INTEGER, name TEXT, description TEXT, time_in_minute INTEGER);";
        db.execSQL(quiz_table);
    }

    private void create_question_table(SQLiteDatabase db) {
        String question_table = "CREATE TABLE IF NOT EXISTS questions(id INTEGER PRIMARY KEY, quiz_id INTEGER, question TEXT, first_choice TEXT, second_choice TEXT, third_choice TEXT, fourth_choice TEXT, answer TEXT);";
        db.execSQL(question_table);
    }

    private void create_recent_quiz_table(SQLiteDatabase db) {
        String recent_quiz_table = "CREATE TABLE IF NOT EXISTS recent_quiz(id INTEGER PRIMARY KEY, user_id INTEGER, quiz_id INTEGER, total INTEGER, created_at TEXT)";
        db.execSQL(recent_quiz_table);
    }

    private void insert_data(SQLiteDatabase db) {
        String user_data = "INSERT INTO users(name, email, password, level) VALUES " +
                "('Lutfi', 'email@email.com', 'password', 'user'), " +
                "('admin', 'admin@admin.com', 'password', 'admin');";
        db.execSQL(user_data);

        String category_data = "INSERT INTO categories(name, description) VALUES " +
                "('Programming', 'Category question about proggramming'), " +
                "('animal', 'Category question about animal') ;";
        db.execSQL(category_data);

        String quiz_data = "INSERT INTO quiz(category_id, name, description, time_in_minute) VALUES " +
                "(1, 'Java quiz', 'Full question about java proggramming', 2), " +
                "(2, 'Animal quiz', 'Full question about animal', 1);";
        db.execSQL(quiz_data);

        questions = new ArrayList<Question>();
        questions.add(new Question(1, "What is java?", "java is island", "java is coffee", "java is programming language", "java is food", "C"));
        questions.add(new Question(1, "How write string to console", "console.log('string')", "System.out.println('string')", "print('string')", "<p>string</p>", "B"));

        questions.add(new Question(2, "Animal that have 2 foot?", "cow", "chicken", "fish", "ant", "B"));
        questions.add(new Question(2, "choose which is a mammal", "whale", "chicken", "lizard", "ant", "A"));

        for(Question q : questions) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("question", q.getQuestion());
            contentValues.put("first_choice", q.getFirst_choice());
            contentValues.put("second_choice", q.getSecond_choice());
            contentValues.put("third_choice", q.getThird_choice());
            contentValues.put("fourth_choice", q.getFourth_choice());
            contentValues.put("answer", q.getAnswer());
            db.insert("questions", null, contentValues);
        }
    }
}
