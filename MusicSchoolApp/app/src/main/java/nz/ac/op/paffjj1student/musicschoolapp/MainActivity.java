package nz.ac.op.paffjj1student.musicschoolapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    //class members that can be accessed by inner classes
    private String selectedMonth;
    private String selectedInstrument;
    private TextView feedback;
    private Spinner monthSpinner;
    private RadioGroup instrumentGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // control objects created
        Button enrolButton = (Button) findViewById(R.id.buttonEnrol);
        instrumentGroup = (RadioGroup) findViewById(R.id.radioGroupInstruments);
        monthSpinner = (Spinner) findViewById(R.id.spinnerMonths);
        feedback = (TextView) findViewById(R.id.textViewFeedback);

        //array adapter object (for spinner)
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.months, android.R.layout.simple_spinner_item);
        monthSpinner.setAdapter(adapter);

        //listener objects created
        ButtonHandler bHandler = new ButtonHandler();


        //listeners set to objects
        enrolButton.setOnClickListener(bHandler);


    }

    public class ButtonHandler implements View.OnClickListener{

        @Override
        public void onClick(View v){

            int selectedId = instrumentGroup.getCheckedRadioButtonId();
            RadioButton radioButton = (RadioButton) findViewById(selectedId);
            String enrolMsg = "You have enrolled for " + radioButton.getText() + " in " + monthSpinner.getSelectedItem().toString() + ".";
            feedback.setText(enrolMsg);

        }
    }




}
