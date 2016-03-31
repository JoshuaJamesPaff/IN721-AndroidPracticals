package nz.ac.op.paffjj1student.languagetrainer;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class QuizActivity extends AppCompatActivity {

    private QuestionManager manager;
    private RadioGroup genders;
    private ImageView image;
    private Button submitAnswer;
    private TextView questionNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //manager of questions
        manager = new QuestionManager();

        //image and button objects
        image = (ImageView) findViewById(R.id.imageViewNoun);
        submitAnswer = (Button) findViewById(R.id.buttonSubmit);

        //listener object
        ButtonHandler bHandler = new ButtonHandler();

        //listener set to button
        submitAnswer.setOnClickListener(bHandler);

        //sets default picture
        int imgID = manager.getQuestion().getImgID();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgID);
        image.setImageBitmap(bitmap);

        //radio group
        genders = (RadioGroup) findViewById(R.id.radioGroup);

        //question number
        questionNum = (TextView) findViewById(R.id.textViewQNumber);

    }

    public class ButtonHandler implements View.OnClickListener{

        @Override
        public void onClick(View v){

            //gets answer from selected radio button
            int selectedId = genders.getCheckedRadioButtonId();
            RadioButton selection = (RadioButton) findViewById(selectedId);
            String result;
            String answer = manager.getQuestion().getAnswer();

            //evaluates answer
            if(manager.checkQuestion((String)selection.getText())){
                result = "Congratulations, " + answer + " is correct!";
            }else{
                result = "Sorry, the correct answer was " + answer + ".";
            }

            //creates dialog fragment and sets feedback
            AlertBuilderFragment feedback = new AlertBuilderFragment();
            feedback.setFeedBack(result);
            FragmentManager fm = getFragmentManager();
            feedback.show(fm, "continue");

        }
    }

    //changes question image and label
    public void changeQuestion(){

        //if the game is over
        if(manager.getCurrentQuestion() > 11){

            String stringScore = manager.getScore() + "";

            //resets quiz
            manager = new QuestionManager();

            //pass intent to final score activity
            Intent changeActivityIntent = new Intent(QuizActivity.this, FinalScoreActivity.class);
            changeActivityIntent.putExtra("score", stringScore);

            startActivity(changeActivityIntent);

        }else{
            //update question
            image.setImageResource(manager.getQuestion().getImgID());
            String qNumber = "Question " + manager.getCurrentQuestion();
            questionNum.setText(qNumber);
        }
    }
}
