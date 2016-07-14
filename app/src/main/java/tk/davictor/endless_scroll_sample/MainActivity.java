package tk.davictor.endless_scroll_sample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getHeaderView(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_site_link));
            }
        });

        if (savedInstanceState == null) {
            // also will set title and replace fragment
            onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_list_view));
        }
    }

    private void openLink(@NonNull String link) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(link));
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (item.isChecked()) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.nav_list_view:
                setActionBarTitle(R.string.list_view_title);
                if (!existsInFragmentManager(ListViewFragment.class)) {
                    replaceFragment(new ListViewFragment());
                }
                break;
            case R.id.nav_grid_view:
                setActionBarTitle(R.string.grid_view_title);
                if (!existsInFragmentManager(GridViewFragment.class)) {
                    replaceFragment(new GridViewFragment());
                }
                break;
            case R.id.nav_linear_layout_manager:
                break;
            case R.id.nav_grid_layout_manager:
                break;
            case R.id.nav_staggered_grid_layout_manager:
                break;
            case R.id.nav_github_link:
                openLink(getString(R.string.github_repository_link));
                break;
            case R.id.nav_site_link:
                openLink(getString(R.string.davinctor_tk_link));
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    private void setActionBarTitle(@StringRes int stringRes) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setTitle(stringRes);
        }
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    private <T> boolean existsInFragmentManager(@NonNull Class<T> clazz) {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments == null || fragments.size() == 0) {
            return false;
        }

        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.getClass().isAssignableFrom(clazz)) {
                return true;
            }
        }

        return false;
    }
}
