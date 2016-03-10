package paffjj1op.welcometodunedin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //sets menu labels
        setMenuList();

        // creates listview object to hold menu items and sets the listener
        ListView menuDrawer = (ListView) findViewById(R.id.left_drawer);
        menuDrawer.setOnItemClickListener(new DrawerMenuClickHandler());

    }


    // sets labels to list view using adapter
    public void setMenuList(){

        //array adapter set to list view
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.pages, android.R.layout.activity_list_item);
        ListView menuGroupListView = (ListView) findViewById(R.id.left_drawer);
        menuGroupListView.setAdapter(adapter);

    }

    //this class listens for clicks to a drawer menu listview object and redirects to the relevant activity
    public class DrawerMenuClickHandler implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            //creates string and sets it to the selected item from the drawer menu
            String selectedItem = parent.getItemAtPosition(position).toString();
            Intent nextIntent;

            // switches on the string and sets the intent based on its content
            switch(selectedItem){
                case "Services":
                    nextIntent = new Intent(MainActivity.this, Services.class);
                    break;
                case "Fun Things To Do":
                    nextIntent = new Intent(MainActivity.this, Activities.class);
                    break;
                case "Dining":
                    nextIntent = new Intent(MainActivity.this, Dining.class);
                    break;
                case "Shopping":
                    nextIntent = new Intent(MainActivity.this, Shopping.class);
                    break;
                default:
                    nextIntent = null;
            }

            //goto the next event
            if (nextIntent != null){
                startActivity(nextIntent);
            }

        }
    }
}
