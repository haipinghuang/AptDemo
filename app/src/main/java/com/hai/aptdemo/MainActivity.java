package com.hai.aptdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.hai.annotation.NewClass;
import com.hai.lib.Inject;

@NewClass
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Inject.inject(this);
    }

    public void clk(View view) {
        Toast.makeText(this, Inject.inject(this), Toast.LENGTH_SHORT).show();
    }
}
