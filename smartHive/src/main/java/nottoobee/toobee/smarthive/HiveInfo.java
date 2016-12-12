/*
    Team Name: tooBee || !tooBee
*/

package nottoobee.toobee.smarthive;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class HiveInfo extends AppCompatActivity implements LocationListener {

    private String hiveKey;
    private ListView mDrawerList;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    private TextView tv;

    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;

    Location location;
    double latitude;
    double longitude;

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hive_info);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        mDrawerList = (ListView) findViewById(R.id.navList);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_closed);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        addDrawerItems();

        ActionBar ab = getSupportActionBar();


        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowHomeEnabled(true);
            ab.setLogo(R.drawable.logo);
            ab.setDisplayUseLogoEnabled(true);
        }


        Intent i = getIntent();

        ((TextView) findViewById(R.id.info_hive_name)).setText(i.getStringExtra("hiveName"));
        ((TextView) findViewById(R.id.info_population)).setText(i.getStringExtra("hiveDataPop"));
        ((TextView) findViewById(R.id.info_temp)).setText(i.getStringExtra("hiveDataTemp"));
        ((TextView) findViewById(R.id.info_date)).setText(i.getStringExtra("hiveDataDate"));
        ((TextView) findViewById(R.id.info_weight)).setText(i.getStringExtra("hiveDataWeight"));
        ((TextView) findViewById(R.id.info_humidity)).setText(i.getStringExtra("hiveHumidity") + " %");

        hiveKey = i.getStringExtra("hiveKey");
        tv = (TextView) findViewById(R.id.info_location);
        tv.setText(i.getStringExtra("hiveLocation"));
    }


    public void deleteHive(MenuItem item) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(getResources().getString(R.string.delete_hive_));

        alertDialogBuilder
                .setMessage(getResources().getString(R.string.click_yes_delete))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // TODO
                        MainActivity.deleteHive(hiveKey);
                        startActivity(new Intent(HiveInfo.this, MainActivity.class));
                    }
                })
                .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }

    public void checkPerm() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int MY_PERMISSION_REQUEST_FINE_LOCATION = 1;
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_REQUEST_FINE_LOCATION);
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
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
        i.putExtra("location", tv.getText().toString());
        i.putExtra("hiveName", j.getStringExtra("hiveName"));
        startActivity(i);
    }

    private void addDrawerItems() {
        final String[] burgerArray = {getString(R.string.hives_home), getString(R.string.how_to), getString(R.string.about_us_drawer)};
        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, burgerArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
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


    public void setLocation(View view) {
        checkPerm();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            try {
                locationManager = (LocationManager) this
                        .getSystemService(LOCATION_SERVICE);

                // getting GPS status
                isGPSEnabled = locationManager
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);

                // getting network status
                isNetworkEnabled = locationManager
                        .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if (isGPSEnabled && isNetworkEnabled) {
                    this.canGetLocation = true;
                    // First get location from Network Provider

                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        latitude = location.getLatitude();
                        longitude = location.getLongitude();

                    }

                    // if GPS Enabled get lat/long using GPS Services
                    if (isGPSEnabled) {
                        if (location == null) {
                            locationManager.requestLocationUpdates(
                                    LocationManager.GPS_PROVIDER,
                                    MIN_TIME_BW_UPDATES,
                                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                            Log.d("GPS Enabled", "GPS Enabled");
                            if (locationManager != null) {
                                location = locationManager
                                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                if (location != null) {
                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();
                                }
                            }
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            tv.setText(String.format(Locale.US, "%1$.3f", latitude) + ", " + String.format(Locale.US, "%1$.3f", longitude));

            MainActivity.updateLocation(hiveKey, Double.toString(latitude).substring(0, 7) + ", " + Double.toString(longitude).substring(0, 7));
        } else {
            Toast.makeText(getBaseContext(), "Location permissions required", Toast.LENGTH_SHORT).show();
        }
    }
}

