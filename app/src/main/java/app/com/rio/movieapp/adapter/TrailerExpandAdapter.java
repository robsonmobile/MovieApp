package app.com.rio.movieapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.ArrayList;

import app.com.rio.movieapp.R;
import app.com.rio.movieapp.model.Trailer;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by rio on 08/01/16.
 */
public class TrailerExpandAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private Typeface mFont;
    private RequestManager requestManager;

    private ArrayList<Trailer> mDataList;

    public TrailerExpandAdapter(Context context, RequestManager requestManager) {
        mContext		= context;
        this.requestManager = requestManager;
        mInflater 		= LayoutInflater.from(context);

    }

    public void setData(ArrayList<Trailer> list) {
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
            convertView	= mInflater.inflate(R.layout.item_trailer, null);

            holder = new ViewHolder();

            holder.ivYoutube = (CircleImageView) convertView.findViewById(R.id.iv_trailer);
            holder.tvContent = (TextView) convertView.findViewById(R.id.tv_title_trailer);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Trailer item = mDataList.get(position);

        holder.tvContent.setText(item.name);

        Glide.with(mContext)
                .load("http://img.youtube.com/vi/" + item.key + "/0.jpg")
                .error(R.drawable.error_image)
                .into(holder.ivYoutube);

        return convertView;
    }

    static class ViewHolder {
        public CircleImageView ivYoutube;
        public TextView tvContent;
    }

}
