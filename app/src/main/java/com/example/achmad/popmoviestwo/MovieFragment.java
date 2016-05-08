package com.example.achmad.popmoviestwo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.achmad.popmoviestwo.Api.MyApi;
import com.example.achmad.popmoviestwo.Data.Discover;
import com.example.achmad.popmoviestwo.Data.Movie;
import com.paginate.Paginate;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Achmad on 06/05/2016.
 */
public class MovieFragment extends Fragment {
    public ArrayList<Movie> mGridData = new ArrayList<Movie>();
    public GridViewAdapter mGridAdapter;
    int mCurrentPage = 2;
    private PosterCallback mCallback;
    private int mTotalPages = 1;
    private Paginate mPaginate;
    private boolean mIsFinished = false;
    public GridView mGridView;

    public interface PosterCallback {
        void onMoviePosterClicked(int movieId);
    }

    public static MovieFragment newInstance() {
        return new MovieFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mGridAdapter = new GridViewAdapter(getActivity(), mGridData);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        mGridView = (GridView) v.findViewById(R.id.gridView);

        fetchMovie(mCurrentPage);

        mGridView.setAdapter(mGridAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(),""+mGridData.get(position).getId(),Toast.LENGTH_SHORT).show();
                mCallback.onMoviePosterClicked(mGridData.get(position).getId());
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (PosterCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement " + mCallback.toString());
        }
    }

    public void fetchMovie(int currentPage) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MyApi tmdbApi = retrofit.create(MyApi.class);

        // parameter apa aja yg dibutuhkan, bisa dilihat disini ya
        // http://docs.themoviedb.apiary.io/#reference/discover/discovermovie/get
        Call<Discover> call = tmdbApi.discoverMovies("popularity.desc",
                BuildConfig.TMDB_API_KEY, 1);

        call.enqueue(new Callback<Discover>() {
            @Override
            public void onResponse(Call<Discover> call, Response<Discover> response) {
                mGridData.addAll(response.body().getMovies());
                mGridAdapter.notifyDataSetChanged();
                mTotalPages = 3;
                Log.d("totalnya=", "" + mTotalPages);

                //paginateNextPage();
                if (mTotalPages > 1) {
                    paginateNextPage();
                }
            }

            @Override
            public void onFailure(Call<Discover> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to fetch movie", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void paginateNextPage() {
        Paginate.Callbacks paginateCallbacks = new Paginate.Callbacks() {
            @Override
            public void onLoadMore() {
                fetchMovieNextPage();
            }

            @Override
            public boolean isLoading() {
                if (mCurrentPage == mTotalPages) {
                    mPaginate.setHasMoreDataToLoad(false);
                    return false;
                }

                return !mIsFinished;
            }

            @Override
            public boolean hasLoadedAllItems() {
                if (mCurrentPage == mTotalPages) {
                    mPaginate.setHasMoreDataToLoad(false);
                    return true;
                }

                return false;
            }
        };

        mPaginate = Paginate.with(mGridView, paginateCallbacks)
                .setLoadingTriggerThreshold(2)
                .addLoadingListItem(true)
                .build();
    }

    private void fetchMovieNextPage() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MyApi tmdbApi = retrofit.create(MyApi.class);

        // parameter apa aja yg dibutuhkan, bisa dilihat disini ya
        // http://docs.themoviedb.apiary.io/#reference/discover/discovermovie/get
        Call<Discover> call = tmdbApi.discoverMovies("popularity.desc",
                BuildConfig.TMDB_API_KEY, mCurrentPage);

        call.enqueue(new Callback<Discover>() {
            @Override
            public void onResponse(Call<Discover> call, Response<Discover> response) {
                mGridData.addAll(response.body().getMovies());
                mGridAdapter.notifyDataSetChanged();
                mIsFinished = true;
                ++mCurrentPage;
            }

            @Override
            public void onFailure(Call<Discover> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to fetch movie", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
