package app.com.rio.movieapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rio on 07/05/16.
 */
public class Movies implements Parcelable {
    public long id;
    public String poster_path;
    public String overview;
    public String release_date;
    public String original_title;
    public String original_language;
    public String title;
    public String backdrop_path;
    public double popularity;
    public long vote_count;
    public double vote_average;

    public String type_sort;

    public Movies() {}

    public Movies(Parcel in) {
        id = in.readLong();
        poster_path = in.readString();
        overview = in.readString();
        release_date = in.readString();
        original_title = in.readString();
        original_language = in.readString();
        title = in.readString();
        backdrop_path = in.readString();
        popularity = in.readDouble();
        vote_count = in.readLong();
        vote_average = in.readDouble();

        type_sort = in.readString();
    }

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(poster_path);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeString(original_title);
        dest.writeString(original_language);
        dest.writeString(title);
        dest.writeString(backdrop_path);
        dest.writeDouble(popularity);
        dest.writeLong(vote_count);
        dest.writeDouble(vote_average);

        dest.writeString(type_sort);
    }
}
