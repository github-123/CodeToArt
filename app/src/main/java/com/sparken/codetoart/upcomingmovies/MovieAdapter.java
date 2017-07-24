package com.sparken.codetoart.upcomingmovies;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sparken.codetoart.R;
import com.sparken.codetoart.constant.MovieConstant;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.sparken.codetoart.callwebservice.WSUrls.URL_MOVIE_IMAGE_LIST;

/**
 * Created by root on 19/7/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    private List<MovieInfoModel> mMovieList;
    private FragmentActivity mContext;
    MovieInfoModel movieInfoModel;

    public MovieAdapter(FragmentActivity context, List<MovieInfoModel> movieList) {
        this.mContext = context;
        this.mMovieList = movieList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivMovie;
        public TextView tvTitle;
        public TextView tvReleaseDate;
        public TextView tvIsAdult;


        public MyViewHolder(View itemView) {
            super(itemView);
            ivMovie = (ImageView) itemView.findViewById(R.id.iv_movie_image);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_movie_title);
            tvReleaseDate = (TextView) itemView.findViewById(R.id.tv_release_date);
            tvIsAdult = (TextView) itemView.findViewById(R.id.tv_is_adult);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String movieID = "";
                    movieID = movieInfoModel.getId();
                    UpcomingMovieDetailsFragment movieDetailsFrag = new UpcomingMovieDetailsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(MovieConstant.KEY_MOVIE_ID, movieID);
                    movieDetailsFrag.setArguments(bundle);
                    mContext.getSupportFragmentManager().beginTransaction().replace(R.id.container_movie_dashboard, movieDetailsFrag).commit();
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_movies, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ImageView tvMovie = holder.ivMovie;
        TextView tvtitle = holder.tvTitle;
        TextView tvReleaseDate = holder.tvReleaseDate;
        TextView tvIsAdult = holder.tvIsAdult;
        String picUrl = "";

        try {

            movieInfoModel = mMovieList.get(position);

            if (null != movieInfoModel) {
                tvtitle.setText(movieInfoModel.getTitle());
                tvReleaseDate.setText(movieInfoModel.getRelease_date());
                if (movieInfoModel.isAdult()) tvIsAdult.setText("(A)");
                else tvIsAdult.setText("(U/A)");
                picUrl = movieInfoModel.getPoster_path();
                String strFinalUrl = URL_MOVIE_IMAGE_LIST + picUrl.substring(1);

                Picasso.with(mContext).
                        load(strFinalUrl).
                        error(R.drawable.error).
                        noFade().
                        placeholder(R.drawable.loading).
                        into(tvMovie);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return (null != mMovieList ? mMovieList.size() : 0);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
