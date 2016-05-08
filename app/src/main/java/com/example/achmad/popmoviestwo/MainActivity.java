package com.example.achmad.popmoviestwo;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import com.example.achmad.popmoviestwo.Data.Movie;

import java.util.ArrayList;

public class MainActivity extends SingleFragmentActivity implements MovieFragment.PosterCallback {

    ArrayList<Movie> mGridData = new ArrayList<Movie>();
    GridViewAdapter mGridAdapter;
    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }*/

    @Override
    protected Fragment createFragment() {
        return new MovieFragment();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMoviePosterClicked(int movieId) {

        /*if(findViewById(R.id.movie_detail_container) != null){
            DetailFragment detailFragment = DetailFragment.newInstance(movieId, true);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, detailFragment)
                    .commit();
        }else{*/
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("Movieid", movieId);
        startActivity(intent);
        /*}*/
    }
}
