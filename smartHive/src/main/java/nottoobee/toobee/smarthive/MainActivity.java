/*
    Team Name: tooBee || !tooBee
*/

package nottoobee.toobee.smarthive;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private String mUsername;
    private String mPhotoUrl;
    private GridLayout grid;
    private int numHives = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerList = (ListView)findViewById(R.id.navList);
        grid = (GridLayout)findViewById(R.id.hive_display);

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

        // Database connection - Get DB reference that corresponds to active user.
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("/users/" + mFirebaseUser.getUid());
        ref.limitToLast(5).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                grid.removeAllViews();
                for (DataSnapshot msgSnapshot: snapshot.getChildren()) {
                    Hive hive = msgSnapshot.getValue(Hive.class);
                    Log.i("Hive", hive.getName());
                    drawHive(hive, grid);
                }
            }
            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.e("Hive", "The read failed: " + firebaseError.getMessage());
            }
        });

    }

    private void addDrawerItems() {
        String[] burgerArray = { "Hive Map", "Graphs", "Settings" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, burgerArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, Settings.class);
                startActivity(i);
                //Toast.makeText(MainActivity.this, "Time for an upgrade!", Toast.LENGTH_SHORT).show();
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
     * @param hive
     * @param layout
     */
    private void drawHive(Hive hive, GridLayout layout) {
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
                i.putExtra("hiveName", newHive.getName());
                i.putExtra("hiveLocation", newHive.getLocation());
                i.putExtra("hiveDataWeight", Integer.toString(newHive.getData().getWeight()));
                i.putExtra("hiveDataTemp", Integer.toString(newHive.getData().getTemperature()));
                i.putExtra("hiveDataDate", new Date(newHive.getData().getDate()));
                i.putExtra("hiveDataPop", Integer.toString(newHive.getData().getPopulation()));
                startActivity(i);
            }
        });
        ln.addView(iv);
    }

    public void newHive(MenuItem item) {
        startActivity(new Intent(this, AddHive.class));
    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit Application?");

        alertDialogBuilder
                .setMessage("Click yes to exit")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }
}
