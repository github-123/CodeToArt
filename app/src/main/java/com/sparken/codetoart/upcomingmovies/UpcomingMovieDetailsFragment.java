package com.sparken.codetoart.upcomingmovies;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.gson.Gson;
import com.sparken.codetoart.MovieDashboardActivity;
import com.sparken.codetoart.R;
import com.sparken.codetoart.Utility.Utility;
import com.sparken.codetoart.callwebservice.WSUrls;
import com.sparken.codetoart.constant.AppController;
import com.sparken.codetoart.constant.CustomProgressDialog;
import com.sparken.codetoart.constant.MovieConstant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by ravi sonawane on 20/7/17.
 * Thia class specifies the details about upcoming movies. like images, movie name and popularity.
 */

public class UpcomingMovieDetailsFragment extends Fragment implements ViewPagerEx.OnPageChangeListener, BaseSliderView.OnSliderClickListener {

    SliderLayout sliderLayout;
    HashMap<String, String> Hash_file_maps;

    TextView tvTitle, tvDeveloped, tvOverview;
    RatingBar btnRating;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);

        try {
            Hash_file_maps = new HashMap<String, String>();
            sliderLayout = (SliderLayout) view.findViewById(R.id.slider);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            tvOverview = (TextView) view.findViewById(R.id.tv_overview);
            tvDeveloped = (TextView) view.findViewById(R.id.tv_developed);
            btnRating = (RatingBar) view.findViewById(R.id.btn_rating);

            Bundle bundle = this.getArguments();
            if (bundle != null) {
                String strId = "";
                strId = bundle.getString(MovieConstant.KEY_MOVIE_ID);
                String url = WSUrls.URL_MOVIE_DETAILS + strId;
                StringBuffer stringBuffer = new StringBuffer(url).append("?api_key=").append(MovieConstant.DEFAULT_API_KEY);
                getMoviesDetails(stringBuffer.toString());
            }

            tvDeveloped.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sliderLayout.stopAutoCycle();
                    Intent intent = new Intent(getActivity(), DevelopedByActivity.class);
                    startActivity(intent);
                }
            });

        } catch (Throwable e) {
            e.printStackTrace();
        }
        return view;
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

    private void showMovieImages(String finalPath) {

        if (Utility.isNetworkConnected(getActivity())) {

            CustomProgressDialog.showDialog(getActivity(), "Please wait...");
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, finalPath, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    CustomProgressDialog.dismissDialog(getActivity());
                    try {

                        JSONArray getArray = response.getJSONArray("backdrops");
                        if (getArray != null) {
                            for (int i = 0; i < getArray.length(); i++) {
                                JSONObject objectInArray = getArray.getJSONObject(i);
                                String img_id = "";
                                img_id = objectInArray.getString("file_path");
                                scrollImagesOnlline(img_id.substring(0));
                            }
                        }
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    CustomProgressDialog.dismissDialog(getActivity());
                    errorDialog(error.getMessage().toString());
                }
            }) {
                /*@Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("api_key", MovieConstant.DEFAULT_API_KEY);
                    return params;
                }*/
            };

            AppController.getInstance().addToRequestQueue(jsonObjReq);

        } else {
            Toast.makeText(getActivity(), "Please Connect To Internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void scrollImagesOnlline(String filePath) {

        StringBuffer stringBuffer = new StringBuffer(WSUrls.URL_MOVIE_IMAGE_LIST).append(filePath);

        TextSliderView textSliderView = new TextSliderView(getActivity());
        textSliderView.description("Banner")
                .image(stringBuffer.toString())
                .setScaleType(BaseSliderView.ScaleType.Fit)
                .setOnSliderClickListener(this);
        textSliderView.bundle(new Bundle());
        textSliderView.getBundle().putString("extra", "Banner");
        sliderLayout.addSlider(textSliderView);

        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(3000);
        sliderLayout.addOnPageChangeListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
    }

    @Override
    public void onStop() {
        sliderLayout.stopAutoCycle();
        super.onStop();
    }


    public void getMoviesDetails(String imageUrl) {

        if (Utility.isNetworkConnected(getActivity())) {

            CustomProgressDialog.showDialog(getActivity(), "Please wait...");
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, imageUrl, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    CustomProgressDialog.dismissDialog(getActivity());
                    Gson gson = new Gson();
                    MovieDetailModel model = gson.fromJson(response.toString(), MovieDetailModel.class);
                    if (model != null) {
                        String movie_id = "";
                        String strTitle = "";
                        String strOverview = "";
                        String strRating = "";

                        strTitle = model.getTitle();
                        ((MovieDashboardActivity) getActivity()).getSupportActionBar().setTitle(strTitle);
                        strOverview = model.getOverview();
                        strRating = model.getPopularity();

                        tvTitle.setText(strTitle);
                        tvOverview.setText(strOverview);
                        btnRating.setRating(Float.valueOf(strRating));

                        movie_id = model.getId();
                        String mID = movie_id.substring(0);
                        StringBuffer stringBuffer = new StringBuffer(WSUrls.URL_MOVIE_DETAILS).append(mID).append("/images?api_key=b7cd3340a794e5a2f35e3abb820b497f");
                        showMovieImages(stringBuffer.toString());
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    CustomProgressDialog.dismissDialog(getActivity());
                    errorDialog(error.getMessage().toString());
                }
            }) {
                /*@Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("api_key", MovieConstant.DEFAULT_API_KEY);
                    return params;
                }*/
            };

            AppController.getInstance().addToRequestQueue(jsonObjReq);

        } else {
            Toast.makeText(getActivity(), "Please Connect To Internet", Toast.LENGTH_SHORT).show();
        }
    }
}
