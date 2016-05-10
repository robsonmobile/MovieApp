package app.com.rio.movieapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rio on 10/05/16.
 */
public class Trailer implements Parcelable {
    public String id;
    public String key;
    public String site;
    public String type;
    public String name;

    public Trailer() {}

    public Trailer(Parcel in) {
        id = in.readString();
        key = in.readString();
        site = in.readString();
        type = in.readString();
        name = in.readString();
    }

    public static final Creator<Trailer> CREATOR = new Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(key);
        dest.writeString(site);
        dest.writeString(type);
        dest.writeString(name);
    }
}
