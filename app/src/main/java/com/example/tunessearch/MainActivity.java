package com.example.tunessearch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
    private ItemsAdapter adapter;
    private Api api;
    private EditText editText;
    private Button search;
    private List<Item> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler = findViewById(R.id.main_rv_albums);
        editText = findViewById(R.id.main_et_search);
        search = findViewById(R.id.main_btn_search);
        api = ((App) getApplication()).getApi();
        adapter = new ItemsAdapter();

        adapter.setListener(new AdapterListener());
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send response
                if (!editText.getText().toString().equals("")) {
                    Call<Result> call = api.getAlbums(editText.getText().toString(), "music", "album", "albumTerm");
                    System.out.println(call.request().url());
                    call.enqueue(new Callback<Result>() {
                        @Override
                        public void onResponse(@NonNull Call<Result> call, @NonNull Response<Result> response) {
                            Result result = response.body();
                            assert result != null;
                            items = result.getItems();

                            if (!items.isEmpty()) {
                                Collections.sort(items, new Comparator<Item>() {
                                    @Override
                                    public int compare(Item i1, Item i2) {
                                        return i1.getTitle().compareTo(i2.getTitle());
                                    }
                                });
                                //set items to adapter
                                adapter.setItems(items);
                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(MainActivity.this, "No such albums", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Result> call, Throwable t) {
                            System.out.println(t);
                        }
                    });

                    //do not show keyboard after click
                    View view = MainActivity.this.getCurrentFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert view != null;
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                } else {
                    Toast.makeText(MainActivity.this, "You mast tape anything. That's how it works", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    class AdapterListener implements ItemsAdapterListener {

        @Override
        public void OnItemClick(Item item) {
            Intent intent = new Intent(MainActivity.this, CollectionInfoActivity.class);
            intent.putExtra("item", item);
            startActivity(intent);
        }
    }
}
