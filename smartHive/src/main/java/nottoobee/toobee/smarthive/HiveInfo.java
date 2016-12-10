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
        ab.setDisplayUseLogoEnabled(true);
        ((TextView)findViewById(R.id.info_hive_name)).setText(i.getStringExtra("hiveName"));
        ((TextView)findViewById(R.id.info_population)).setText(i.getStringExtra("hiveDataPop"));
        ((TextView)findViewById(R.id.info_temp)).setText(i.getStringExtra("hiveDataTemp"));
        ((TextView)findViewById(R.id.info_date)).setText(i.getStringExtra("hiveDataDate"));
        ((TextView)findViewById(R.id.info_weight)).setText(i.getStringExtra("hiveDataWeight"));
        //Log.i("Time", i.getStringExtra("hiveDataDate"));
    }
}
