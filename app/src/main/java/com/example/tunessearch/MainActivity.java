package com.example.tunessearch;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recycler;
    ItemsAdapter adapter;
    private Api api;
    EditText editText;
    Button search;
    List<Item> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler = findViewById(R.id.main_rv_albums);
        editText = findViewById(R.id.main_et_search);
        search = findViewById(R.id.main_btn_search);
        api = ((App) getApplication()).getApi();
        adapter = new ItemsAdapter();

        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Call<Result> call = api.getAlbums("adel", "music", "album");
                    System.out.println(call.request().url());
                    call.enqueue(new Callback<Result>() {
                        @Override
                        public void onResponse(Call<Result> call, Response<Result> response) {
                            Result result = response.body();
                            items = result.getItems();

                            Collections.sort(items, new Comparator<Item>() {
                                @Override
                                public int compare(Item o1, Item o2) {
                                    return o1.getTitle().compareTo(o2.getTitle());
                                }
                            });
                            adapter.setItems(items);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<Result> call, Throwable t) {
                            System.out.println(t);
                        }
                    });

                    //можно еще с этим поиграться будет
                View view = MainActivity.this.getCurrentFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
    }
}
