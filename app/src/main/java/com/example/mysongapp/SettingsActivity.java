package com.example.mysongapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {
    static LinearLayout settings ;
    static boolean b = true;
    static Switch aSwitch ;

    @Override
    protected void onResume() {
        super.onResume();
        isDark();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        settings = findViewById(R.id.settingsParent);
        aSwitch = findViewById(R.id.switch1);

        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (aSwitch.isChecked())
                {
                    b = true;
                    isDark();
                    aSwitch.setChecked(true);
                }
                else
                {
                    b = false;
                    isDark();
                    aSwitch.setChecked(false);
                }
            }
        });

         }
    void isDark()
    {
        if(b)
        {
            settings.setBackgroundResource(R.mipmap.wall_wall_foreground);
            aSwitch.setChecked(true);
        }
        else
        {
            settings.setBackgroundResource(R.color.white);
            aSwitch.setChecked(false);
        }

    }

}

