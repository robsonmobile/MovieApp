package app.com.rio.movieapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rio on 10/05/16.
 */
public class Review implements Parcelable {
    public String id;
    public String author;
    public String content;
    public String url;

    public Review() {}

    public Review(Parcel in) {
        id = in.readString();
        author = in.readString();
        content = in.readString();
        url = in.readString();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(author);
        dest.writeString(content);
        dest.writeString(url);
    }
}
