package in.gajerait.liveena.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import in.gajerait.liveena.R;
import in.gajerait.liveena.adapter.MoviesAdapter;
import in.gajerait.liveena.bean.Movie;

public class ample extends AppCompatActivity {
    private List<Movie> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;

    private Activity activity;
    private RecyclerView recycler_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ample);

        activity = ample.this;


        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new MoviesAdapter(activity, movieList);
        recycler_view.setLayoutManager(new GridLayoutManager(activity, 3));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
//        recycler_view.setLayoutManager(new GridLayoutManager(activity, 3));
//
////        PhotoCategory_prepareMovieData();recyclerView.setAdapter(mAdapter);
//
//
//        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
//        recycler_view.setLayoutManager(new GridLayoutManager(activity, 3));
////        recycler_view.setLayoutManager(new LinearLayoutManager(activity));
        prepareMovieData();
    }

    private void prepareMovieData() {
        Movie movie = new Movie("", R.drawable.live);
        movieList.add(movie);

        movie = new Movie("", R.drawable.photo);
        movieList.add(movie);

        movie = new Movie("", R.drawable.video);
        movieList.add(movie);

        movie = new Movie("Celebrity", R.drawable.selebrity);
        movieList.add(movie);

        movie = new Movie("Sponsors", R.drawable.sponsar);
        movieList.add(movie);

        movie = new Movie("Booking", R.drawable.booking);
        movieList.add(movie);

        movie = new Movie("Facebook", R.drawable.facebook);
        movieList.add(movie);

        movie = new Movie("Rewards", R.drawable.rewords);
        movieList.add(movie);

        movie = new Movie("Food Stall", R.drawable.food_stol);
        movieList.add(movie);

        movie = new Movie("Classis", R.drawable.classis);
        movieList.add(movie);

        movie = new Movie("You tube", R.drawable.youtub);
        movieList.add(movie);

        movie = new Movie("Contact us", R.drawable.contact);
        movieList.add(movie);

        mAdapter.notifyDataSetChanged();
    }

}