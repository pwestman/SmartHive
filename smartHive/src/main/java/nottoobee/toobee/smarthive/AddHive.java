/*
    Team Name: tooBee || !tooBee
*/

package nottoobee.toobee.smarthive;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddHive extends AppCompatActivity implements LocationListener {

    private FirebaseUser mFirebaseUser;
    private double lat;
    private double longi;
    TextView tv;
    private final int MY_PERMISSION_REQUEST_FINE_LOCATION = 1;
    private ArrayList<String> hiveNames;

    // TODO: Validate all fields.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hive);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        hiveNames = new ArrayList<>();
    }

    public void addHive(View view) {
        checkPerm();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, false);

            if (provider != null && !provider.equals("")) {

                // Get the location from the given provider
                locationManager.requestLocationUpdates(provider, 20000, 1, this);

                Location location = locationManager.getLastKnownLocation(provider);


                if (location != null)
                    onLocationChanged(location);
                else
                    Toast.makeText(getBaseContext(), getString(R.string.location_cant), Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getBaseContext(), getString(R.string.no_provider), Toast.LENGTH_SHORT).show();
            }


            tv = (TextView) findViewById(R.id.textView19);
            tv.setText(Double.toString(lat).substring(0, 6) + ", " + Double.toString(longi).substring(0, 6));
            // Create Hive
            String hiveName = ((EditText) findViewById(R.id.add_hive_name)).getText().toString();
            Hive hive = new Hive(hiveName, Double.toString(lat).substring(0, 6) + ", " + Double.toString(longi).substring(0, 6));


            // Upload to database
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/users/" + mFirebaseUser.getUid());
            hiveNames = MainActivity.getHiveNameList();
            //checking if there are any gaps in numbering, which could appear when user deletes hive with middle index
            if (!hiveNames.isEmpty()) {
                int index = 0;
                boolean check = true;
                for (int i = 1; i <= hiveNames.size(); i++) {
                    if (check) {
                        if (!hiveNames.get(i - 1).equals(Integer.toString(i))) {
                            index = i;
                            check = false;

                        }
                    }
                }
                //if no problem with indexing, it assigns the last index
                if (index == 0) {
                    index = hiveNames.size() + 1;
                }
                hive.setKey(Integer.toString(index));
                ref.child(Integer.toString(index)).setValue(hive);
            } else {
                hive.setKey("1");
                ref.child("1").setValue(hive);
            }
            ref.push();
        }
    }

    public void checkPerm() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_REQUEST_FINE_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), R.string.permissions_granted, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.locations_permissions_granted, Toast.LENGTH_SHORT).show();
                }
                break;
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
        Toast.makeText(this, getString(R.string.enabled_provider) + " " + provider,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, getString(R.string.disabled_provider) + " " + provider,
                Toast.LENGTH_SHORT).show();
    }


}
