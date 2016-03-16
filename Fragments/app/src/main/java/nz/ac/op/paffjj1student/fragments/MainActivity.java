package nz.ac.op.paffjj1student.fragments;

import android.app.FragmentTransaction;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tablet_layout);

        //creates button object and handler objects
        Button buttonImageFragment = (Button) findViewById(R.id.buttonImageView);
        Button buttonListFragment = (Button) findViewById(R.id.buttonListView);

        ImageButtonClickHandler imageClickHandler = new ImageButtonClickHandler();
        ListButtonClickHandler listClickHandler = new ListButtonClickHandler();

        //sets listeners to buttons
        buttonImageFragment.setOnClickListener(imageClickHandler);
        buttonListFragment.setOnClickListener(listClickHandler);

    }

    public class ImageButtonClickHandler implements View.OnClickListener{

        @Override
        public void onClick(View v){

            Fragment imageFragment = new ShowImageFragment();
            FragmentManager fm = getFragmentManager();

            //fragment transaction obj
            FragmentTransaction ft = fm.beginTransaction();

            ft.replace(R.id.image_fragment_container, imageFragment);

            ft.commit();
        }
    }

    public class ListButtonClickHandler implements View.OnClickListener{

        @Override
        public void onClick(View v){

            Fragment listViewFragment = new ShowListViewFragment();
            FragmentManager fm = getFragmentManager();

            //fragment transaction obj
            FragmentTransaction ft = fm.beginTransaction();

            ft.replace(R.id.list_view_fragment_container, listViewFragment);

            ft.commit();
        }
    }
}
