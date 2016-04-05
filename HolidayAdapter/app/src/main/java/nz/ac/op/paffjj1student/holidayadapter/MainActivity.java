package nz.ac.op.paffjj1student.holidayadapter;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity {

    private ListView menuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuList = (ListView) findViewById(R.id.listViewMenu);
        menuList.setOnItemClickListener(new DrawerMenuClickHandler());

        setMenuList();


    }

    // sets labels to list view using adapter
    public void setMenuList(){

        //create resources object to convert xml array to a string object
        Resources resourceMachine = getResources();
        String[] menuArray = resourceMachine.getStringArray(R.array.pages);

        //array adapter created and set to list view
        ArrayAdapter<String> menuNamesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuArray);

        menuList.setAdapter(menuNamesAdapter);

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