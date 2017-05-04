package com.leafcastlelabs.demoui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;


public class PickerActivity extends Activity {

    NumberPicker numberPicker1;
    NumberPicker numberPicker2;
    NumberPicker numberPicker3;
    TextView txtOld;
    TextView txtNew;
    Button btnOK;
    Button btnCancel;

    String strOld = "000";
    String strNew = "000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker);




        txtOld = (TextView) findViewById(R.id.txtNew);
        txtNew = (TextView) findViewById(R.id.txtOld);
        numberPicker1 = (NumberPicker) findViewById(R.id.numberPicker1);
        numberPicker2 = (NumberPicker) findViewById(R.id.numberPicker2);
        numberPicker3 = (NumberPicker) findViewById(R.id.numberPicker3);

        numberPicker1.setMinValue(0);
        numberPicker1.setMaxValue(9);
        numberPicker1.setValue(0);
        numberPicker1.setWrapSelectorWheel(true);
        numberPicker1.setOnValueChangedListener(new AnyPickerChanged());

        numberPicker2.setMinValue(0);
        numberPicker2.setMaxValue(9);
        numberPicker2.setValue(0);
        numberPicker2.setWrapSelectorWheel(true);
        numberPicker2.setOnValueChangedListener(new AnyPickerChanged());

        numberPicker3.setMinValue(0);
        numberPicker3.setMaxValue(9);
        numberPicker3.setValue(0);
        numberPicker3.setWrapSelectorWheel(true);
        numberPicker3.setOnValueChangedListener(new AnyPickerChanged());

        btnOK = (Button)findViewById(R.id.btnOk);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("combination", strNew);
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

    private class AnyPickerChanged implements NumberPicker.OnValueChangeListener {

        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

            int one = numberPicker1.getValue();
            int two = numberPicker2.getValue();
            int three = numberPicker3.getValue();

            switch(picker.getId()){
                case R.id.numberPicker1:
                    strOld = concatNum(oldVal,two,three);
                    strNew = concatNum(newVal,two,three);
                    break;
                case R.id.numberPicker2:
                    strOld = concatNum(one,oldVal,three);
                    strNew = concatNum(one,newVal,three);
                    break;
                case R.id.numberPicker3:
                    strOld = concatNum(one,two,oldVal);
                    strNew = concatNum(one,two,newVal);
                    break;
            }

            txtNew.setText("New value: " + strNew);
            txtOld.setText("Old value: " + strOld);
        }

        private String concatNum(int i1, int i2, int i3){
            return Integer.toString(i1).concat(Integer.toString(i2).concat(Integer.toString(i3)));
        }
    }

}
