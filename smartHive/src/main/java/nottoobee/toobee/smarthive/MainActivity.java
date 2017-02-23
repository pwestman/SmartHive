/*
    Team Name: tooBee || !tooBee
*/

package nottoobee.toobee.smarthive;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;


public class MainActivity extends AppCompatActivity {

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private String mUsername;
    private String mPhotoUrl;
    private GridLayout grid;
    private int numHives = 0;
    private static DatabaseReference ref;
    private static ArrayList <String> hiveName;
    private static ArrayList <String> hiveLocations;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerList = (ListView)findViewById(R.id.navList);
        grid = (GridLayout)findViewById(R.id.hive_display);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_closed);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        ActionBar ab = getSupportActionBar();

        try {
            ab.setDisplayShowHomeEnabled(true);
            ab.setLogo(R.drawable.logo);
        }catch (Exception e){
            e.printStackTrace();
        }

        ab.setDisplayUseLogoEnabled(true);

        addDrawerItems();

        // TODO: Remove these once the placement of hive graphics once the GridLayout is working.
        //drawHive(new Hive("Hive 1", "gps coordinates"), (GridLayout)findViewById(R.id.hive_display));
        //drawHive(new Hive("Hive 2", "gps coordinates"), (GridLayout)findViewById(R.id.hive_display));

        // Firebase Auth
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
        }

        hiveName = new ArrayList<>();
        hiveLocations = new ArrayList<>();
        // Database connection - Get DB reference that corresponds to active user.
        ref = FirebaseDatabase.getInstance()
                .getReference("/users/" + mFirebaseUser.getUid());
        ref.limitToLast(5).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Hive hive = dataSnapshot.getValue(Hive.class);
                drawHive(hive, grid, dataSnapshot.getKey());
                hiveName.add(hive.getName());
                hiveLocations.add(hive.getLocation());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.e("Hive", "The read failed: " + firebaseError.getMessage());
            }
        });

    }

    private void addDrawerItems() {
        final String[] burgerArray = { getString(R.string.how_to), getString(R.string.about_us_drawer)};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, burgerArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {
                    case 0:
                        Intent i = new Intent(MainActivity.this, HowTo.class);
                        startActivity(i);
                        break;
                    default:
                        Intent k = new Intent(MainActivity.this, AboutUs.class);
                        startActivity(k);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bee_menu, menu);
        return true;
    }

    public void getinfo(View view) {
        Intent intent = new Intent(this, HiveInfo.class);
        startActivity(intent);
    }

    /**
     * Creates a graphical representation of the given Hive in the given GridLayout.
     * @param hive hive object
     * @param layout the GridLayout layout on the main screen, where the hive objects will be inflated
     */
    private void drawHive(Hive hive, GridLayout layout, final String key) {
        // TODO: Pretty this up to make it look like the mockup.
        final Hive newHive = hive;
        LinearLayout ln = new LinearLayout(this);
        layout.addView(ln);
        TextView tv = new TextView(this);
        tv.setHint("hive_name_" + numHives++);
        tv.setText(hive.getName());
        ln.addView(tv);
        ImageView iv = new ImageView(this);
        iv.setImageResource(R.drawable.hive_green);
        iv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, HiveInfo.class);
                i.putExtra("hiveKey", key);
                startActivity(i);
            }
        });
        ln.addView(iv);
    }

    public void newHive(MenuItem item) {
        startActivity(new Intent(this, AddHive.class));
    }

    public static void deleteHive(String name){
        ref.child(name).removeValue();
    }

    public static void updateLocation(String name, String newLocation){
        ref.child(name).child("location").setValue(newLocation);
    }

    public static ArrayList getHiveNameList(){
        return hiveName;
    }

    public static ArrayList getHiveLocationList(){
        return hiveLocations;
    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(getResources().getString(R.string.exit));

        alertDialogBuilder
                .setMessage(getResources().getString(R.string.click_yes))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.yes),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.no),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
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
