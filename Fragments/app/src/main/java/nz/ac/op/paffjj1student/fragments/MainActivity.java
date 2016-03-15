package nz.ac.op.paffjj1student.fragments;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //creates button object and handler objects
        Button buttonImageFragmentToggle = (Button) findViewById(R.id.buttonImageView);
        Button buttonListFragmentToggle = (Button) findViewById(R.id.buttonListView);

        ButtonClickHandler clickHandler = new ButtonClickHandler();

        //sets listeners to buttons
        buttonImageFragmentToggle.setOnClickListener(clickHandler);
        buttonListFragmentToggle.setOnClickListener(clickHandler);

    }

    public class ButtonClickHandler implements View.OnClickListener{

        @Override
        public void onClick(View v){

            FragmentManager fm = getSupportFragmentManager();

            //fragment transaction obj
            FragmentTransaction ft = fm.beginTransaction();

            // add fragments
            switch (v.getId()) {

                //image fragment toggle
                case R.id.buttonImageView:

                    Fragment imageFragment = new ShowImageFragment();

                    ft.replace(R.id.image_fragment_container, imageFragment);

                    ft.commit();

                    break;

                //listView fragment toggle
                case R.id.buttonListView:

                    Fragment listViewFragment = new ShowListViewFragment();

                    ft.replace(R.id.list_view_fragment_container, listViewFragment);

                    ft.commit();

                    break;

                default:
                    break;
            }

        }
    }
}
