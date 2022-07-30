package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quiz.helpers.Session;
import com.example.quiz.models.User;
import com.example.quiz.services.AuthService;

import java.util.concurrent.CountDownLatch;

public class Login extends AppCompatActivity {
    final Context context = this;
    private EditText email;
    private EditText password;
    private Button button;
    CountDownLatch cd = new CountDownLatch(0);

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AuthService auth = new AuthService();
        
        if (auth.isLoggedIn()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.emailLogin);
        password = (EditText) findViewById(R.id.passwordLogin);
        button = (Button) findViewById(R.id.buttonLogin);
        TextView registration = (TextView) findViewById(R.id.goToRegister);
        button.setOnClickListener(v -> authenticate(v));

        registration.setOnClickListener(v -> {
            Intent intent = new Intent(context, Register.class);
            startActivity(intent);
        });
    }

    public void authenticate(View v) {
        String emailValue = email.getText().toString();
        String passwordValue = password.getText().toString();

        try {
            AuthService auth = new AuthService();
            auth.login(context, emailValue, passwordValue,
                () -> {
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                }, () -> {
                    Toast.makeText(context, new Session(context).getString("loginStatus"), Toast.LENGTH_LONG).show();
                }
            );
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}