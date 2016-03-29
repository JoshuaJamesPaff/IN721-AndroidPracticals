package nz.ac.op.paffjj1student.languagequiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartQuiz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_quiz);

        //button to start quiz
        Button startQuiz = (Button) findViewById(R.id.buttonStartQuiz);
        ButtonClickHandler handler = new ButtonClickHandler();
        //bind handler to button
        startQuiz.setOnClickListener(handler);
    }


    public class ButtonClickHandler implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            //starts quiz
            Intent changeActivityIntent = new Intent(StartQuiz.this, QuizActivity.class);
            startActivity(changeActivityIntent);

        }
    }
}
