package nz.ac.op.paffjj1student.passingdata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreateUsername extends AppCompatActivity {

    //input text property
    private EditText inputUserName = (EditText) findViewById(R.id.editTextInput);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_username);

        //BUTTON CODE
        //button object
        Button addUserButton = (Button) findViewById(R.id.buttonBackToMain);
        //listener object
        ButtonClickHandler handler = new ButtonClickHandler();
        //bind handler to button
        addUserButton.setOnClickListener(handler);

    }

    //click handler inner class
    public class ButtonClickHandler implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            String input = inputUserName.getText().toString();

            //if input is less than 5 char long
            if(input.length() < 5){
                Toast noInput = Toast.makeText(CreateUsername.this,"Please enter a username longer than 5 characters" ,Toast.LENGTH_LONG);
                noInput.show();
            }else{
                //changes activity while passing data thru the intent object using putExtra method
                Intent changeActivityIntent = new Intent(CreateUsername.this, MainActivity.class);
                changeActivityIntent.putExtra("username", input);
                startActivity(changeActivityIntent);
            }
        }
    }
}


