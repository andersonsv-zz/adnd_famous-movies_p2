package br.com.andersonv.famousmovies.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Trailer implements Parcelable {

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

    @SerializedName("name")
    private final String name;

    @SerializedName("key")
    private final String key;

    private Trailer(Parcel in) {
        this.name = in.readString();
        this.key = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(name);
        parcel.writeString(key);
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

}
