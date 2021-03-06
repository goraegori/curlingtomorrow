package com.gor2.curlingtomorrow.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.gor2.curlingtomorrow.R;

public class DialogSave extends Dialog{

    private Context context;
    TextView txtPlayerNameRed, txtPlayerNameYellow;

    public DialogSave(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_save);
        txtPlayerNameRed = findViewById(R.id.txtPlayerNameRed);
        txtPlayerNameYellow = findViewById(R.id.txtPlayerNameYellow);

        Button btnSet = findViewById(R.id.btnSet);
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String redName = txtPlayerNameRed.getText().toString(), yellowName = txtPlayerNameYellow.getText().toString();
                if(redName.isEmpty() || yellowName.isEmpty()) return;

                ((PreviewActivity)context).txtPlayerRed.setText(redName);
                ((PreviewActivity)context).txtPlayerYellow.setText(yellowName);
                dismiss();
            }
        });
        Button btnReset = findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
