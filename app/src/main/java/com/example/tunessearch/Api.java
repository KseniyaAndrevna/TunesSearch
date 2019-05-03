package com.example.tunessearch;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET("search")
    Call<Result> getAlbums(@Query("term") String term, @Query("media") String media, @Query("entity") String entity, @Query("attribute") String attribute);

    @GET("lookup")
    Call<Result> getSongs(@Query("id") int id, @Query("entity") String entity);
}
