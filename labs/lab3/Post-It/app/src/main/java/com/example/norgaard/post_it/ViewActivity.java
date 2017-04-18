package com.example.norgaard.post_it;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ViewActivity extends AppCompatActivity {

    Context context = this;
    TextView textView;

    public static final int REQUEST_CODE_ACTIVITY_EDIT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Button btnEdit = (Button) findViewById(R.id.btnStartEditActivity);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditActivity.class);
                intent.putExtra("user_input", textView.getText().toString());
                startActivityForResult(intent, REQUEST_CODE_ACTIVITY_EDIT);
            }
        });

        textView = (TextView) findViewById(R.id.txwForString);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ACTIVITY_EDIT) {
            if (resultCode == RESULT_OK) {
                // Shit went good

                String s = data.getStringExtra("user_input");

                if (data != null) {
                    Toast.makeText(this, "Ok!" + s, Toast.LENGTH_SHORT).show();
                    textView.setText(s);
                }
            }
            else {
                // Shit went wrong
            }
        }
    }
}
