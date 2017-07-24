package com.sparken.codetoart.upcomingmovies;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.android.volley.Request;
import com.sparken.codetoart.MovieDashboardActivity;
import com.sparken.codetoart.R;
import com.sparken.codetoart.callwebservice.CallWebservice;
import com.sparken.codetoart.callwebservice.VolleyResponseListener;
import com.sparken.codetoart.callwebservice.WSUrls;
import com.sparken.codetoart.constant.MovieConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by ravi on 19/7/17.
 * This class specifies the upcoming movies.
 */

public class UpcomingMovieFragment extends Fragment {

    private RecyclerView recyclerView;
    private MovieDashboardActivity dashboardActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_upcoming_movie, container, false);

        try {
            actionbarSetting();

            dashboardActivity = (MovieDashboardActivity) getActivity();

            recyclerViewSetting(view);

            getUpcomingMovies();

        } catch (Throwable e) {
            e.printStackTrace();
        }
        return view;
    }

    private void recyclerViewSetting(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_movie);
        recyclerView.setLayoutManager(new LinearLayoutManager(dashboardActivity));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void actionbarSetting() {
        ((MovieDashboardActivity) getActivity()).getSupportActionBar().setTitle("Upcoming Movies");
    }


    private void errorDialog(String message) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
                .setPositiveButton("QUIT", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) dashboardActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    private void getUpcomingMovies() {

        final List<MovieInfoModel> movieInfoModels = new ArrayList<MovieInfoModel>();

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(MovieConstant.PARAM_API_KEY, MovieConstant.DEFAULT_API_KEY);

        CallWebservice.getWebservice(true, dashboardActivity, Request.Method.POST, WSUrls.URL_MOVIES, hashMap, new VolleyResponseListener<MovieInfoModel>() {

            @Override
            public void onResponse(MovieInfoModel[] object, String message) {

                if (object[0] instanceof MovieInfoModel) {

                    for (MovieInfoModel bean : object) {

                        movieInfoModels.add(bean);
                    }

                    MovieAdapter movieAdapter = new MovieAdapter(getActivity(), movieInfoModels);
                    recyclerView.setAdapter(movieAdapter);
                    movieAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(String message) {
                errorDialog(message);
            }

        }, MovieInfoModel[].class);
    }

}
