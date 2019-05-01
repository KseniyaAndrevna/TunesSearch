package com.example.tunessearch;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET("search")
    Call<Result> getAlbums(@Query("term") String term, @Query("media") String media, @Query("entity") String entity);

    @GET("lookup")
    Call<List<Item>> getSongs(@Query("id") int id, @Query("entity") String entity);
}
