package nz.ac.op.paffjj1student.languagequiz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Mr Nobody on 30/03/2016.
 */
public class AlertBuilderFragment extends DialogFragment
{
    private String feedback = "";

    public AlertBuilderFragment(){}

    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        //creates dialog builder and sets its values
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(feedback);
        builder.setPositiveButton("Continue", new ButtonHandler());

        Dialog customDialog = builder.create();

        return customDialog;

    }

    // dialog button handler
    public class ButtonHandler implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialog, int whichButton) {

            //creates activity object and calls method
            QuizActivity myActivity = (QuizActivity) getActivity();
            myActivity.changeQuestion();
        }
    }

    //sets feedback
    public void setFeedBack(String feedback){
        this.feedback = feedback;
    }

}
