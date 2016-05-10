package app.com.rio.movieapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;

import java.util.ArrayList;

import app.com.rio.movieapp.R;
import app.com.rio.movieapp.model.Review;

/**
 * Created by rio on 08/01/16.
 */
public class ReviewExpandAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private Typeface mFont;
    private RequestManager requestManager;

    private ArrayList<Review> mDataList;

    public ReviewExpandAdapter(Context context, RequestManager requestManager) {
        mContext		= context;
        this.requestManager = requestManager;
        mInflater 		= LayoutInflater.from(context);

    }

    public void setData(ArrayList<Review> list) {
        mDataList = list;
    }

    @Override
    public int getCount() {
        return (mDataList == null) ? 0 : mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView	= mInflater.inflate(R.layout.item_review, null);

            holder = new ViewHolder();

            holder.tvAuthor = (TextView) convertView.findViewById(R.id.tv_author_review);
            holder.tvContent = (TextView) convertView.findViewById(R.id.tv_content_review);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Review item = mDataList.get(position);

        holder.tvAuthor.setText(item.author);
        holder.tvContent.setText("\"" + item.content + "\"");

        return convertView;
    }

    static class ViewHolder {
        public TextView tvAuthor;
        public TextView tvContent;
    }

}
