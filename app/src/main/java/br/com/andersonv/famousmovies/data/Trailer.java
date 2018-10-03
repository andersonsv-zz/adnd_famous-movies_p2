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

    private Trailer(Parcel in) {
        this.name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(name);
    }

    public String getName() {
        return name;
    }

    /*
    "id": "5a85f6a1c3a36862e1029f18",
            "iso_639_1": "pt",
            "iso_3166_1": "PT",
            "key": "5yNlNnP1AAY",
            "name": "The ShawShank Redemption Trailer High-Quality Legendado PT-PT",
            "site": "YouTube",
            "size": 1080,
            "type": "Trailer"
     */


}
