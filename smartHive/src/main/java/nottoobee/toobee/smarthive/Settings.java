/*
    Team Name: tooBee || !tooBee
*/

package nottoobee.toobee.smarthive;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class Settings extends AppCompatActivity {

    private Spinner unitSpinner, timerSpinner;
    private Button submitBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        unitSpinner = (Spinner) findViewById(R.id.unitSpin);
        timerSpinner = (Spinner) findViewById(R.id.timerSpin);

        List<String> list = new ArrayList<>();
        list.add("Metric");
        list.add("Imperial");

        List<String> timerList = new ArrayList<>();
        list.add("5 minutes");
        list.add("10 minutes");
        list.add("15 minutes");
        list.add("30 minutes");
        list.add("60 minutes");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
        ArrayAdapter<String> dataAdapterTimer = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,timerList);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterTimer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        unitSpinner.setAdapter(dataAdapter);
        timerSpinner.setAdapter(dataAdapter);

        // Spinner item selection Listener
        addListenerOnSpinnerItemSelection();

        // Button click Listener
        //addListenerOnButton();

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        try {
            ab.setDisplayShowHomeEnabled(true);
            ab.setLogo(R.drawable.logo);
        }catch (Exception e){
            e.printStackTrace();
        }

        ab.setDisplayUseLogoEnabled(true);
    }

    public void addListenerOnSpinnerItemSelection(){

        unitSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        timerSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    /*public void addListenerOnButton() {
        unitSpinner = (Spinner) findViewById(R.id.unitSpin);
        timerSpinner = (Spinner) findViewById(R.id.timerSpin);
        submitBut = (Button) findViewById(R.id.submit_changes);
        submitBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Settings.this, "Units changed to " + String.valueOf(unitSpinner.getSelectedItem()), Toast.LENGTH_LONG).show();
            }
        });
    }*/
}
