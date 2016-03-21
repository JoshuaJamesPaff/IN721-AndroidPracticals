package nz.ac.op.paffjj1student.dialogfragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by JP on 22/03/2016.
 */

public class AlertBuilderFragment extends DialogFragment
{

    public AlertBuilderFragment(){}

    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        //creates dialog builder and sets its values
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setIcon(R.drawable.music_icon);
        builder.setTitle("Really Enrol?");
        builder.setPositiveButton("Yes", new YesButtonHandler());
        builder.setNegativeButton("No", new NoButtonHandler());

        Dialog customDialog = builder.create();

        return customDialog;

    }

// dialog yes button handler
    public class YesButtonHandler implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialog, int whichButton) {

            //creates activity object and calls method
            MainActivity myActivity = (MainActivity) getActivity();
            myActivity.confirmSelection(true);
        }
    }
    // dialog yno button handler
    public class NoButtonHandler implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialog, int whichButton) {

            //creates activity object and calls method
            MainActivity myActivity = (MainActivity) getActivity();
            myActivity.confirmSelection(true);

        }
    }

}
