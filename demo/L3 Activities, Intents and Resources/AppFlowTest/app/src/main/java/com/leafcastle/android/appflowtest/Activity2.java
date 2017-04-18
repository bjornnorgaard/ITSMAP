package com.leafcastle.android.appflowtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Activity2 extends AppCompatActivity {


    Button btnCancel;
    Button btnOK;
    EditText edtUserinput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        Intent fromActivityOneOrWhoeverSummedMe = getIntent();
        String s = fromActivityOneOrWhoeverSummedMe.getStringExtra("user_input");


        edtUserinput = (EditText) findViewById(R.id.edtUserinput);
        if(s!=null){
            edtUserinput.setText(s);
        }
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                canceled();
            }
        });
        btnOK = (Button) findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ok();
            }
        });

    }

    public void canceled(){
        setResult(RESULT_CANCELED);
        finish();
    }

    public void ok(){

        String s = edtUserinput.getText().toString();
        Intent data = new Intent();
        data.putExtra("user_input", s);
        setResult(RESULT_OK, data);
        finish();
    }

}
