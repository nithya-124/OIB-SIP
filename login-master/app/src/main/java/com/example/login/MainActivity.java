package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b;
        b=findViewById(R.id.button);
        EditText e1,e2;
        e1=findViewById(R.id.editTextTextPersonName);
        e2=findViewById(R.id.editTextTextPersonName2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user=e1.getText().toString();
                String pass=e2.getText().toString();
                if(user.equals("shravani")&&(pass.equals("12345"))) {
                    Toast.makeText(MainActivity.this, "login successsfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), MainActivity2.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "incorrect input", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}