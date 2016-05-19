package nz.ac.op.paffjj1student.toolbars;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class ToolBarActivity extends AppCompatActivity {

    private TextView mainLabel;

    private EditText searchBar;
    private TextView searchLabel;

    private TextView settingsLabel;
    private RadioButton rbA;
    private RadioButton rbB;
    private RadioButton rbC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_bar);

        //initialising controls
        mainLabel = (TextView) findViewById(R.id.textViewTitle);
        searchBar = (EditText) findViewById(R.id.etSearchBar);
        searchLabel = (TextView) findViewById(R.id.textViewSearch);
        settingsLabel = (TextView) findViewById(R.id.textViewSettings);
        rbA = (RadioButton) findViewById(R.id.rbSettingA);
        rbB = (RadioButton) findViewById(R.id.rbSettingB);
        rbC = (RadioButton) findViewById(R.id.rbSettingC);
    }

    public void HideAll(){
        mainLabel.setVisibility(View.VISIBLE);

        searchBar.setVisibility(View.INVISIBLE);
        searchLabel.setVisibility(View.INVISIBLE);
        settingsLabel.setVisibility(View.INVISIBLE);
        rbA.setVisibility(View.INVISIBLE);
        rbB.setVisibility(View.INVISIBLE);
        rbC.setVisibility(View.INVISIBLE);

    }
    public void ShowSettings(){
        mainLabel.setVisibility(View.INVISIBLE);
        searchLabel.setVisibility(View.INVISIBLE);
        searchBar.setVisibility(View.INVISIBLE);;

        settingsLabel.setVisibility(View.VISIBLE);
        rbC.setVisibility(View.VISIBLE);
        rbB.setVisibility(View.VISIBLE);
        rbA.setVisibility(View.VISIBLE);

    }
    public void ShowSearch(){
        searchLabel.setVisibility(View.VISIBLE);
        searchBar.setVisibility(View.VISIBLE);

        mainLabel.setVisibility(View.INVISIBLE);
        settingsLabel.setVisibility(View.INVISIBLE);
        rbA.setVisibility(View.INVISIBLE);
        rbB.setVisibility(View.INVISIBLE);
        rbC.setVisibility(View.INVISIBLE);

    }

    // method inflates menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.control_display_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int itemID = item.getItemId();
        String itemTitle = (String) item.getTitle();

        switch(itemTitle){
            case "Hide":
                HideAll();
                break;

            case "Settings":
                ShowSettings();
                break;

            case "Search":
                ShowSearch();
                break;
        }

        return true;

    }
}
