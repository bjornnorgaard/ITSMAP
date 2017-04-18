package com.example.norgaard.post_it;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        final EditText editText = (EditText) findViewById(R.id.edtInputField);

        final Intent intent = getIntent();
        String s = intent.getStringExtra("user_input");
        if (s != null) {
            editText.setText(s);
        }

        Button btnOk = (Button) findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get string from EditText, somehow needs to be toString().
                String s = editText.getText().toString();
                Intent intent = new Intent();
                // Put value of s with key: "user_input".
                intent.putExtra("user_input", s);
                // Set result to build-in: "RESULT_OK" and put intent with it.
                setResult(RESULT_OK, intent);
                // Finish.
                finish();
            }
        });

        Button btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
