package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quiz.helpers.Session;
import com.example.quiz.models.User;
import com.example.quiz.services.AuthService;

public class Register extends AppCompatActivity {
    final Context context = this;
    private TextView login;
    private EditText name;
    private EditText email;
    private EditText password;
    private EditText password_confirmation;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText) findViewById(R.id.nameRegister);
        email = (EditText) findViewById(R.id.emailRegister);
        password = (EditText) findViewById(R.id.passwordRegister);
        password_confirmation = (EditText) findViewById(R.id.passwordConfRegister);
        button = (Button) findViewById(R.id.buttonRegister);
        login = (TextView) findViewById(R.id.goToLogin);

        button.setOnClickListener(v -> register());

        login.setOnClickListener(v -> {
            Intent intent = new Intent(context, Login.class);
            startActivity(intent);
        });
    }

    private void register() {
        String nameValue = name.getText().toString();
        String emailValue = email.getText().toString();
        String passwordValue = password.getText().toString();
        String confirmationValue = password_confirmation.getText().toString();

        if (nameValue.length() > 0 && emailValue.length() > 0 && passwordValue.length() > 0 && confirmationValue.length() > 0) {
            if (passwordValue.equals(confirmationValue)) {
                AuthService auth = new AuthService();
                User user = new User();
                user.setName(nameValue);
                user.setEmail(emailValue);
                user.setPassword(passwordValue);
                user.setPassword_confirmation(confirmationValue);

                auth.register(context, user, () -> {
                    Intent intent = new Intent(context, Login.class);
                    intent.putExtra("register success", new Session(context).getString("signupStatus"));
                    startActivity(intent);
                }, () -> {
                    Toast.makeText(context, new Session(context).getString("signupStatus"), Toast.LENGTH_LONG).show();
                });
            } else {
                Toast.makeText(context, "Password with password confirmation is not match", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(context, "All field is required", Toast.LENGTH_SHORT).show();
        }
    }
}