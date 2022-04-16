package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Model.NewsModel;
import com.example.myapplication.R;

import java.util.List;

public class WebviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int TYPE = 1;
    private final Context context;
    private final List<Object> listRecyclerItem;
    private ItemClickListener mitemclickListener;

    public WebviewAdapter(Context context, List<Object> listRecyclerItem, ItemClickListener itemClickListener) {
        this.context = context;
        this.listRecyclerItem = listRecyclerItem;
        this.mitemclickListener  = itemClickListener;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageView img;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            img = (ImageView) itemView.findViewById (R.id.imageView);

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case TYPE:

            default:

                View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                        R.layout.list_items_news_recycler, viewGroup, false);

                return new ItemViewHolder((layoutView));
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        int viewType = getItemViewType(i);


        switch (viewType) {
            case TYPE:
            default:

                ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
                NewsModel news = (NewsModel) listRecyclerItem.get(i);
                itemViewHolder.name.setText(news.getTitle ());
//                Picasso.get ().load (((News) listRecyclerItem.get (i)).getImg ()).into (((ItemViewHolder) viewHolder).img);

                Glide.with (context)
                        .load (((NewsModel) listRecyclerItem.get (i)).getImg ())
                        .into (((ItemViewHolder) viewHolder).img);

//                to find position of itemclicked
                viewHolder.itemView.setOnClickListener (view -> {


                    mitemclickListener.onItemClick ((NewsModel) listRecyclerItem.get (i));
                });
        }

    }

    @Override
    public int getItemCount() {
        return listRecyclerItem.size();
    }

//    recyclerview itemclick

    public interface ItemClickListener{
        void onItemClick(NewsModel news);
    }

}
