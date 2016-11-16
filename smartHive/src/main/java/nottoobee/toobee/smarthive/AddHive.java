/*
    Team Name: tooBee || !tooBee
*/

package nottoobee.toobee.smarthive;

import android.*;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

import java.util.List;
import java.util.Locale;

public class AddHive extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    static final Integer LOCATION = 0x1;
    private String mUsername;
    private String mPhotoUrl;
    private String mUid;
    private static final int MY_PERMISSION_REQUEST_FINE_LOCATION = 1;
    private boolean permissionIsGranted = false;
    private Location loc;

    // TODO: Validate all fields.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hive);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) {
            startActivity(new Intent(this, SignIn.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }
            mUid = mFirebaseUser.getUid();
        }
    }

    public void addHive(View view) {
         if(permissionIsGranted) {
             // Get location
             LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

             Location lastKnown = null;
             // Define a listener that responds to location updates
             LocationListener locationListener = new LocationListener() {
                 public void onLocationChanged(Location location) {
                     loc = location;
                 }

                 public void onStatusChanged(String provider, int status, Bundle extras) {
                 }

                 public void onProviderEnabled(String provider) {
                 }

                 public void onProviderDisabled(String provider) {
                 }
             };

             // Register the listener with the Location Manager to receive location updates
             try {
                 //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                 lastKnown = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
             } catch (SecurityException e) {
                 e.printStackTrace();
             }

             double lat = 0, longi = 0;
             try {
                 lat = lastKnown.getLatitude();
                 longi = lastKnown.getLongitude();
             } catch (Exception e) {
                 e.printStackTrace();
             }

             TextView tv = (TextView) findViewById(R.id.textView19);
             tv.setText(Double.toString(lat) + ", " + Double.toString(longi));
             // Create Hive
             String hiveName = ((EditText) findViewById(R.id.add_hive_name)).getText().toString();
             Hive hive = new Hive(hiveName, Double.toString(lat) + ", " + Double.toString(longi));


             // Upload to database
             DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/users/" + mFirebaseUser.getUid());
             ref.push().setValue(hive);
         }else{checkPerm();}
    }

    public void checkPerm(){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_REQUEST_FINE_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case MY_PERMISSION_REQUEST_FINE_LOCATION:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    permissionIsGranted = true;
                }else {
                    permissionIsGranted = false;
                    Toast.makeText(getApplicationContext(), "Location permissions required to be granted", Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }
}
