package br.com.andersonv.famousmovies.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Review implements Parcelable{

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

    @SerializedName("author")
    private final String author;

    @SerializedName("content")
    private final String content;

    protected Review(Parcel in) {
        author = in.readString();
        content = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(author);
        parcel.writeString(content);
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
