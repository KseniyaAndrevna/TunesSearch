package com.example.tunessearch;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectionInfoActivity extends AppCompatActivity {

    private ImageView albumImg;
    private TextView albumName;
    private TextView artistName;
    private TextView genre;
    private TextView countTrack;
    private TextView release;
    private RecyclerView recycler;
    private Api api;
    private Item item;
    private List<Item> items = new ArrayList<>();
    private ItemsAdapter adapter;
    private ActionBar actionBar;
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collecction_info);

        //back btn enable
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        item = getIntent().getExtras().getParcelable("item");
        albumImg = findViewById(R.id.info_img_album);
        albumName = findViewById(R.id.info_tv_album_name);
        artistName = findViewById(R.id.info_tv_artist_name);
        genre = findViewById(R.id.info_tv_genre);
        countTrack = findViewById(R.id.info_tv_count_track);
        release = findViewById(R.id.info_tv_realise);
        api = ((App) getApplication()).getApi();
        recycler = findViewById(R.id.info_rv_songs);

        assert item != null;
        Picasso.get().load(item.getImageRes()).into(albumImg);
        albumName.setText(item.getTitle());
        artistName.setText(item.getSubTitle());
        genre.setText(item.getPrimaryGenreName());
        countTrack.setText(item.getTrackCount());
        release.setText(dateToString());

        adapter = new ItemsAdapter();
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        Call<Result> call = api.getSongs(item.getCollectionId(), "song");
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result result = response.body();
                items = result.getItems();
                //remove album info
                items.remove(0);

                adapter.setItems(items);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                System.out.println(t);
            }
        });
    }
//on back btn press
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//make date cool
    public String dateToString() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            date = format.parse(item.getReleaseDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(date);
    }
}