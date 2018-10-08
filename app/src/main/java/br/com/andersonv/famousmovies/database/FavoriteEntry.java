package br.com.andersonv.famousmovies.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "favorite")
public class FavoriteEntry {

    @PrimaryKey
    private long id;
    private String title;
    private String poster;
    private String overview;
    @ColumnInfo(name = "vote_average")
    private Double voteAverage;

    @ColumnInfo(name = "release_date")
    private Date releaseDate;

    @ColumnInfo(name = "created_at")
    private Date createdAt;

    @Ignore
    public FavoriteEntry(String title, String poster, String overview, Double voteAverage, Date releaseDate, Date createdAt) {
        this.title = title;
        this.poster = poster;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
        this.createdAt = createdAt;
    }

    public FavoriteEntry(long id, String title, String poster, String overview, Double voteAverage, Date releaseDate, Date createdAt) {
        this.id = id;
        this.title = title;
        this.poster = poster;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster;
    }

    public String getOverview() {
        return overview;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
