/*
    Team Name: tooBee || !tooBee
*/

package nottoobee.toobee.smarthive;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class HowTo extends AppCompatActivity {

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_use);

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

        ab.setDisplayUseLogoEnabled(true);
    }

    private void addDrawerItems() {
        final String[] burgerArray = { "Hives Home", "How To", "Hive Map", "About Us" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, burgerArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {
                    case 0:
                        Intent i = new Intent(HowTo.this, MainActivity.class);
                        startActivity(i);
                        break;
                    case 1:
                        Intent j = new Intent(HowTo.this, HowTo.class);
                        startActivity(j);
                        break;
                    case 2:
                        Toast.makeText(HowTo.this, "You clicked Hive Map", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Intent k = new Intent(HowTo.this, AboutUs.class);
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
