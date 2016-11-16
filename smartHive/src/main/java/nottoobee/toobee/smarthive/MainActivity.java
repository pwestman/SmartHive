/*
    Team Name: tooBee || !tooBee
*/

package nottoobee.toobee.smarthive;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private String mUsername;
    private String mPhotoUrl;
    private int numHives = 0;
    TextView hive1, hive2, hive3, hive4;
    ImageView h1, h2, h3, h4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerList = (ListView)findViewById(R.id.navList);

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

        hive1 = (TextView) findViewById(R.id.textView);
        hive2 = (TextView) findViewById(R.id.textView2);
        hive3 = (TextView) findViewById(R.id.textView3);
        hive4 = (TextView) findViewById(R.id.textView4);

        h1 = (ImageView) findViewById(R.id.imageView5);
        h2 = (ImageView) findViewById(R.id.imageView4);
        h3 = (ImageView) findViewById(R.id.imageView6);
        h4 = (ImageView) findViewById(R.id.imageView7);

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

        //TODO: Finish getting user's hives.
        // Database connection - Get DB reference that corresponds to active user.
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(mFirebaseUser.getUid());

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
        LinearLayout ln = new LinearLayout(this);
        layout.addView(ln);
        TextView tv = new TextView(this);
        tv.setHint("hive_name_" + numHives++);
        tv.setText(hive.getName());
        ln.addView(tv);
        ImageView iv = new ImageView(this);
        iv.setImageResource(R.drawable.hive_green);
        ln.addView(iv);
    }

    public void newHive(MenuItem item) {
        startActivity(new Intent(this, AddHive.class));
    }
}
