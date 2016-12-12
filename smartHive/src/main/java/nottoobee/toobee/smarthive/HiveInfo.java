/*
    Team Name: tooBee || !tooBee
*/

package nottoobee.toobee.smarthive;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HiveInfo extends AppCompatActivity implements LocationListener {

    private final int MY_PERMISSION_REQUEST_FINE_LOCATION = 1;
    private String hiveKey;
    private String provider;
    private LocationManager locationManager;
    private double lat;
    private double longi;
    private ListView mDrawerList;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hive_info);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        mDrawerList = (ListView)findViewById(R.id.navList);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_closed);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        addDrawerItems();

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
        ((TextView)findViewById(R.id.info_location)).setText(i.getStringExtra("hiveLocation"));

        hiveKey = i.getStringExtra("hiveKey");
    }

public void deleteHive(MenuItem item) {
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
    alertDialogBuilder.setTitle(getResources().getString(R.string.delete_hive_));

    alertDialogBuilder
            .setMessage(getResources().getString(R.string.click_yes_delete))
            .setCancelable(false)
            .setPositiveButton(getResources().getString(R.string.yes),new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int id) {
                    // TODO
                    MainActivity.deleteHive(hiveKey);
                    startActivity(new Intent(HiveInfo.this, MainActivity.class));
                }
            })
            .setNegativeButton(getResources().getString(R.string.no),new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int id) {

                    dialog.cancel();
                }
            });

    AlertDialog alertDialog = alertDialogBuilder.create();

    alertDialog.show();
}

    public void setLocation(View view){
        checkPerm();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            provider = locationManager.getBestProvider(criteria, false);

            if (provider != null && !provider.equals("")) {

                // Get the location from the given provider
                locationManager.requestLocationUpdates(provider, 20000, 1, this);

                Location location = locationManager.getLastKnownLocation(provider);


                if (location != null) {
                    onLocationChanged(location);
                    Toast.makeText(getBaseContext(), R.string.location_updated, Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getBaseContext(), R.string.location_cant, Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getBaseContext(), R.string.no_provider, Toast.LENGTH_SHORT).show();
            }


            TextView tv = (TextView) findViewById(R.id.info_location);
            tv.setText(Double.toString(lat).substring(0, 6) + ", " + Double.toString(longi).substring(0, 6));

            MainActivity.updateLocation(hiveKey, Double.toString(lat).substring(0, 6) + ", " + Double.toString(longi).substring(0, 6));

        }else{
            Toast.makeText(getBaseContext(), R.string.permission_required, Toast.LENGTH_SHORT).show();
        }
    }

    public void checkPerm(){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_REQUEST_FINE_LOCATION);
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        longi = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, getString(R.string.new_provider) + provider,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, getString(R.string.disabled_provider) + provider,
                Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hive_info_menu, menu);
        return true;
    }


    public void goToMap(View view) {
        Intent j = getIntent();

        Intent i = new Intent(HiveInfo.this, HiveLocation.class);
        i.putExtra("location", j.getStringExtra("hiveLocation"));
        i.putExtra("hiveName", j.getStringExtra("hiveName"));
        startActivity(i);
    }

    private void addDrawerItems() {
        final String[] burgerArray = { getString(R.string.hives_home), getString(R.string.how_to), getString(R.string.about_us_drawer) };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, burgerArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {
                    case 0:
                        Intent i = new Intent(HiveInfo.this, MainActivity.class);
                        startActivity(i);
                        break;
                    case 1:
                        Intent j = new Intent(HiveInfo.this, HowTo.class);
                        startActivity(j);
                        break;
                    default:
                        Intent k = new Intent(HiveInfo.this, AboutUs.class);
                        startActivity(k);
                }
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);  // OPEN DRAWER
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
