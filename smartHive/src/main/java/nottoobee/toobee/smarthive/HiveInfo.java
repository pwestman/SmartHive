/*
    Team Name: tooBee || !tooBee
*/

package nottoobee.toobee.smarthive;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

public class HiveInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hive_info);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        try {
            ab.setDisplayShowHomeEnabled(true);
            ab.setLogo(R.drawable.logo);
        }catch (Exception e){
            e.printStackTrace();
        }

        Intent i = getIntent();
        Hive hive = i.getParcelableExtra("hive");
        ab.setDisplayUseLogoEnabled(true);
        ((TextView)findViewById(R.id.info_hive_name)).setText(hive.getName());
        ((TextView)findViewById(R.id.info_population)).setText(Integer.toString(hive.getData().getPopulation()));
        ((TextView)findViewById(R.id.info_temp)).setText(Integer.toString(hive.getData().getTemperature()));
        ((TextView)findViewById(R.id.info_date)).setText(Long.toString(hive.getData().getDate()));
        ((TextView)findViewById(R.id.info_weight)).setText(Integer.toString(hive.getData().getWeight()));
    }
}
