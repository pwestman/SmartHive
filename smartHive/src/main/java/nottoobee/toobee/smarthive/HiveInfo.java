/*
    Team Name: tooBee || !tooBee
*/

package nottoobee.toobee.smarthive;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class HiveInfo extends AppCompatActivity {


    private String hiveKey;
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
        ((TextView)findViewById(R.id.info_humidity)).setText(i.getStringExtra("hiveHumidity") + " %");

        hiveKey = i.getStringExtra("hiveKey");
    }

public void deleteHive(MenuItem item) {
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
    alertDialogBuilder.setTitle("Delete Hive?");

    alertDialogBuilder
            .setMessage("Click yes to delete this hive")
            .setCancelable(false)
            .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int id) {
                    // TODO
                    MainActivity.deleteHive(hiveKey);
                    startActivity(new Intent(HiveInfo.this, MainActivity.class));
                }
            })
            .setNegativeButton("No",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int id) {

                    dialog.cancel();
                }
            });

    AlertDialog alertDialog = alertDialogBuilder.create();

    alertDialog.show();
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hive_info_menu, menu);
        return true;
    }
}
