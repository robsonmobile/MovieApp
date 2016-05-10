package app.com.rio.movieapp.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import app.com.rio.movieapp.R;
import app.com.rio.movieapp.model.Movies;
import app.com.rio.movieapp.util.Cons;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rio on 09/05/16.
 */
public class MovieParalaxAdapter extends RecyclerView.Adapter<MovieParalaxAdapter.MovieViewHolder> {

    private ArrayList<Movies> mMovieList;
    private Context mContext;
    private int lastPosition = -1;
    private int LIST_BIG;
    private int LIST_LITTLE;

    public MovieParalaxAdapter(Context context, ArrayList<Movies> list) {
        this.mContext = context;
        this.mMovieList = list;
        this.LIST_BIG = 0;
        this.LIST_BIG = 10;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (viewType == this.LIST_BIG) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_paralax_movie, parent, false);
        }else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_paralax_movie_litle, parent, false);
        }

        v.setTag(Integer.valueOf(viewType));

        return new MovieViewHolder(v);

    }

    @Override
    public int getItemViewType(int position) {
        super.getItemViewType(position);
        if (position % 5 == 0) {
            return LIST_BIG;
        }
        return LIST_LITTLE;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movies movies = mMovieList.get(position);

        holder.tvTitle.setText(movies.title);
        holder.tvRelease.setText(movies.release_date);
        holder.tvRatting.setText(String.valueOf(movies.vote_average));

        Glide.with(mContext)
                .load(Cons.TEMP_GET_IMAGE + movies.poster_path)
                .error(R.drawable.error_image)
                .into(holder.ivCover);

        setAnimation(holder.mCardView, position);

    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.rl_list_movies)
        CardView mCardView;
        @Bind(R.id.iv_cover_list)
        ImageView ivCover;
        @Bind(R.id.tv_title_list)
        TextView tvTitle;
        @Bind(R.id.tv_release_list)
        TextView tvRelease;
        @Bind(R.id.tv_ratting_list)
        TextView tvRatting;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_up_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

}
