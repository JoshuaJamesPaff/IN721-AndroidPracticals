package nz.ac.op.paffjj1student.languagequiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FinalScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_score);

        //receives score from intent
        Intent receiveScore = getIntent();
        String score = receiveScore.getStringExtra("score");
        int scoreInt = Integer.parseInt(score);

        //displays final score
        TextView scoreDisplay = (TextView) findViewById(R.id.textViewFinalScore);
        String finalResult = "Thank-you for trying the German Gender Bender! You finished with a score of " + ((100/11)*scoreInt) + "%";
        scoreDisplay.setText(finalResult);

        //button object
        Button retryButton = (Button) findViewById(R.id.buttonRetryQuiz);
        //listener object
        ButtonClickHandler handler = new ButtonClickHandler();
        //bind handler to button
        retryButton.setOnClickListener(handler);

    }

    //click handler inner class
    public class ButtonClickHandler implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            //goes back to start activity
            Intent changeActivityIntent = new Intent(FinalScoreActivity.this, StartQuiz.class);
            startActivity(changeActivityIntent);

        }
    }
}
