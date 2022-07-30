package com.example.quiz;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;

public class ConfirmDialog extends Dialog implements View.OnClickListener {
    private Runnable callback;
    public Button confirm;
    public Button cancel;

    public ConfirmDialog(@NonNull Context context) {
        super(context);
    }

    public void setConfirmAction(Runnable runnable) {
        callback = runnable;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.confirm_dialog);

        cancel = (Button) findViewById(R.id.dialog_cancel);
        confirm = (Button) findViewById(R.id.dialog_confirm);
        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dialog_confirm) {
            callback.run();
        }
        dismiss();
    }
}
