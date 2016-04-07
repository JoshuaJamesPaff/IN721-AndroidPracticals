package nz.ac.op.paffjj1student.languagepreference;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView displayText;
    private Button saveSettings;

    SharedPreferences prefs;
    SharedPreferences.Editor prefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("textSettings", MODE_PRIVATE);
        prefsEditor = prefs.edit();

        displayText = (TextView) findViewById(R.id.textViewDisplaySettings);
        saveSettings = (Button) findViewById(R.id.buttonSave);

        String languagePref = prefs.getString("language", null);
        String colorPref = prefs.getString("color", null);

        //set text only if there are settings saved
        if(languagePref != null && colorPref != null){
            setText(languagePref, colorPref);
        }

        //sets listener to button
        saveSettings.setOnClickListener(new SaveSettingsClickHandler());

    }


    //gets strings from radio buttons and saves them to shared preferences
    public class SaveSettingsClickHandler implements View.OnClickListener{

        @Override
        public void onClick(View v){

            RadioGroup rgLang = (RadioGroup) findViewById(R.id.radioGroupLanguage);
            RadioGroup rgColor = (RadioGroup) findViewById(R.id.radioGroupColor);

            int lang = rgLang.getCheckedRadioButtonId();
            int color = rgColor.getCheckedRadioButtonId();

            RadioButton selectedLang = (RadioButton) findViewById(lang);
            RadioButton selectedColor = (RadioButton) findViewById(color);

            String colorName = selectedColor.getText().toString();
            String languageName = selectedLang.getText().toString();

            prefsEditor.putString("color", colorName);
            prefsEditor.putString("language", languageName);
            prefsEditor.commit();

        }
    }

    //sets color and language of text based on paramters
    private void setText(String language, String color){

        switch (language)
        {
            case("French"):
                displayText.setText("Bonjour le monde");
                break;
            case("Spanish"):
                displayText.setText("Hola Mundo");
                break;
            case("German"):
                displayText.setText("Hallo Welt");
                break;
        }

        switch (color)
        {
            case("Blue"):
                displayText.setTextColor(Color.BLUE);
                break;
            case("Green"):
                displayText.setTextColor(Color.GREEN);
                break;
            case("Yellow"):
                displayText.setTextColor(Color.YELLOW);
                break;
        }

    }


}
