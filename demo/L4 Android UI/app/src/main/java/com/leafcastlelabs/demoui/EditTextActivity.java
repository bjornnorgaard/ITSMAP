package com.leafcastlelabs.demoui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class EditTextActivity extends Activity {

    EditText edtPlain;
    EditText edtEmail;
    EditText edtNumber;
    EditText edtPassword;

    Button btnOK;
    Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);

        edtPlain = (EditText)findViewById(R.id.edtPlain);
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtNumber = (EditText)findViewById(R.id.edtNumber);
        edtPassword = (EditText)findViewById(R.id.edtPassword);

        btnOK = (Button)findViewById(R.id.btnOk);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();

                data.putExtra("text_plain",edtPlain.getText().toString());
                data.putExtra("text_email",edtEmail.getText().toString());
                data.putExtra("text_number",edtNumber.getText().toString());
                data.putExtra("text_password",edtPassword.getText().toString());

                setResult(RESULT_OK, data);
                finish();
            }
        });

        btnCancel = (Button)findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_text, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
