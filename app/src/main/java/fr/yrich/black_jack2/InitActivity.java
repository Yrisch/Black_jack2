package fr.yrich.black_jack2;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class InitActivity extends AppCompatActivity {
    InitActivity activity;
    EditText selectname;
    NumberPicker selectbourse;
    ArrayList<String> namesinit;
    ArrayList<Integer> boursesinit;
    Integer i;
    Button vldBtn;
    Button vldJ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_init);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        vldBtn = (Button) findViewById(R.id.initstart);
        selectbourse = (NumberPicker) findViewById(R.id.bourse);
        selectname = (EditText) findViewById(R.id.name);
        vldBtn = (Button) findViewById(R.id.initstart);
        selectbourse.setMinValue(1);
        selectbourse.setMaxValue(1000);
        namesinit = new ArrayList<>();
        boursesinit = new ArrayList<>();
        vldJ = (Button) findViewById(R.id.vldJ);
        this.activity = this;


        vldJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                namesinit.add(selectname.getText().toString());
                i = new Integer(selectbourse.getValue());
                boursesinit.add(i);
                selectbourse.setValue(0);
                selectname.setHint("Nouveau Joueur");
                selectname.setText("");
                Toast.makeText(getApplicationContext(),"Nouveau Joueur créé",Toast.LENGTH_SHORT).show();


            }
        });
        vldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameAct = new Intent(getApplicationContext(), gameActivity.class);
                gameAct.putStringArrayListExtra("names",namesinit);
                gameAct.putIntegerArrayListExtra("bourses",boursesinit);
                startActivity(gameAct);
                finish();


            }
        });
    }

}
