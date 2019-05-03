package com.example.tunessearch;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

    private List<Item> items = new ArrayList<>();
    private ItemsAdapterListener listener = null;

    void setListener(ItemsAdapterListener listener) {
        this.listener = listener;
    }

    void setItems(List<Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = items.get(position);
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView title;
        private TextView subTitle;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.item_iv);
            title = itemView.findViewById(R.id.item_title);
            subTitle = itemView.findViewById(R.id.item_sub_title);
        }

        void bind(final Item item, final ItemsAdapterListener listener) {

            Picasso.get().load(item.getImageRes()).into(image);
            title.setText(item.getTitle());
            subTitle.setText(item.getSubTitle());

            setFilter(0, image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.OnItemClick(item);
                    }
                }
            });
        }
    }
//make img monochrom. Why? I do not know, but i like it
    private void setFilter(int saturation, ImageView image) {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(saturation);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        image.setColorFilter(filter);
    }
}
