package nz.ac.op.paffjj1student.dialogfragments;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //class members that can be accessed by inner classes
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

        //CHANGE TO DIALOG FRAGMENT
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

            AlertBuilderFragment confirmMusic = new AlertBuilderFragment();
            FragmentManager fm = getFragmentManager();
            confirmMusic.show(fm, "confirm");

        }
    }

    public void confirmSelection(boolean enrollMusic){

        int selectedId = instrumentGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) findViewById(selectedId);

        //sets feedback depending on boolean input
        if(enrollMusic) {
            String enrolMsg = "You have enrolled for " + radioButton.getText() + " in " + monthSpinner.getSelectedItem().toString() + ".";
            feedback.setText(enrolMsg);
        }else{
            feedback.setText("Oh well...");
        }

    }




}
