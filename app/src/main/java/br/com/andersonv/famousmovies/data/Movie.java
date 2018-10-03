package br.com.andersonv.famousmovies.data;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Movie implements Parcelable {
    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @SerializedName("id")
    private final Long id;

    @SerializedName("poster_path")
    private final String posterPath;

    @SerializedName("title")
    private final String title;

    @SerializedName("backdrop_path")
    private final String backdropPath;

    @SerializedName("release_date")
    private final String releaseDate;

    @SerializedName("overview")
    private final String overview;

    @SerializedName("vote_average")
    private final Double voteAverage;

    private Movie(Parcel in) {
        this.id = in.readLong();
        this.posterPath = in.readString();
        this.title = in.readString();
        this.backdropPath = in.readString();
        this.releaseDate = in.readString();
        this.overview = in.readString();
        this.voteAverage = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeLong(id);
        parcel.writeString(posterPath);
        parcel.writeString(title);
        parcel.writeString(backdropPath);
        parcel.writeString(releaseDate);
        parcel.writeString(overview);
        parcel.writeDouble(voteAverage);
    }

    public Long getId() { return id; }

    public String getPosterPath() {
        return posterPath;
    }

    public String getTitle() {
        return title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }
}