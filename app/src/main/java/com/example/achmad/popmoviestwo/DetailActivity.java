package com.example.achmad.popmoviestwo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import butterknife.ButterKnife;

/**
 * Created by Achmad on 08/05/2016.
 */
public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.detail_movie);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            int movieId = getIntent().getIntExtra("Movieid", 1);
            startDetailFragment(movieId);
        }
    }

    private void startDetailFragment(int movieId) {
        DetailFragment fragment = (DetailFragment)
                getSupportFragmentManager().findFragmentById(R.id.container);

        if (fragment == null) {
            fragment = DetailFragment.newInstance(movieId, false);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment, DetailFragment.TAG)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
