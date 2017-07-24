package com.sparken.codetoart;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.sparken.codetoart.Utility.Utility;
import com.sparken.codetoart.upcomingmovies.UpcomingMovieDetailsFragment;
import com.sparken.codetoart.upcomingmovies.UpcomingMovieFragment;

/**
 * Created by ravi on 19/7/17.
 * This activity specifies the upcoming movies. Two fragments name UpcomingMovieFragment and UpcomingMovieDetailsFragment
 */

public class MovieDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Utility.showFragment(this, R.id.container_movie_dashboard, UpcomingMovieFragment.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_movie) {

            Utility.showToast(this, "Scroll Down To See Upcoming Movies");

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        try {
            Fragment currentFrag = getSupportFragmentManager().findFragmentById(R.id.container_movie_dashboard);
            if (currentFrag instanceof UpcomingMovieFragment) {
                backDialog();
            } else if (currentFrag instanceof UpcomingMovieDetailsFragment) {
                Utility.showFragment(this, R.id.container_movie_dashboard, UpcomingMovieFragment.class);
            }  else {
                Utility.showFragment(this, R.id.container_movie_dashboard, UpcomingMovieFragment.class);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private void backDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to exit ?");
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }

}
