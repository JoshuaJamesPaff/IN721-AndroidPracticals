package com.example.paffjj1.practical21joshuapaff;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //creates button object and handler objects
        Button buttonMain = (Button) findViewById(R.id.button);
        EditText editText = (EditText) findViewById(R.id.editText);
        ButtonClickHandler handler = new ButtonClickHandler();
        ButtonLongClickHandler longHandler = new ButtonLongClickHandler();
        OnKeyHandler keyHandler = new OnKeyHandler();

        //sets listeners to button and editText
        buttonMain.setOnClickListener(handler);
        buttonMain.setOnLongClickListener(longHandler);
        editText.setOnKeyListener(keyHandler);


    }

    public class ButtonClickHandler implements View.OnClickListener{

        @Override
        public void onClick(View v){

            Toast clickToast = Toast.makeText(MainActivity.this,"Click" ,Toast.LENGTH_LONG);
            clickToast.show();

        }
    }

    public class ButtonLongClickHandler implements View.OnLongClickListener{

        @Override
        public boolean onLongClick(View v) {
            Toast clickToast = Toast.makeText(MainActivity.this,"Long click",Toast.LENGTH_LONG);
            clickToast.show();
            return true;
        }
    }

    public class OnKeyHandler implements View.OnKeyListener{

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(keyCode == KeyEvent.KEYCODE_AT){
                    Toast atToast = Toast.makeText(MainActivity.this,"Dont type @",Toast.LENGTH_LONG);
                    atToast.show();
                }

            if(keyCode == KeyEvent.KEYCODE_ENTER){
                EditText editText = (EditText) findViewById(R.id.editText);
                Editable username = editText.getText();
                if(username.length() == 8){
                    Toast atToast = Toast.makeText(MainActivity.this,("Thankyou " + username),Toast.LENGTH_LONG);
                    atToast.show();
                }else{
                    Toast atToast = Toast.makeText(MainActivity.this,"Username must be 8 characters",Toast.LENGTH_LONG);
                    atToast.show();
                }
            }




            return false;
        }
    }





}
