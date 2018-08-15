package com.adrian_971029.infobook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adrian_971029.infobook.R;
import com.adrian_971029.infobook.model.Item;
import com.adrian_971029.infobook.model.Volume;
import com.adrian_971029.infobook.model.VolumeInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>  {

    private ArrayList<Item> items;
    private Context context;

    public MainAdapter(Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_book, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        final VolumeInfo volumeInfo = items.get(position).getVolumeInfo();

        if (volumeInfo.getImageLinks() != null) {
            Picasso.with(context).load(volumeInfo.getImageLinks().getThumbnail()).into(holder.mImageBook);
        } else {
            holder.mImageBook.setImageResource(R.drawable.libros);
        }

        holder.mTitleBook.setText(volumeInfo.getTitle());
        holder.mAuthorBook.setText(allAuthor(volumeInfo.getAuthors()));
    }

    @Override
    public int getItemCount() {
        if (items != null) {
            return items.size();
        } else {
            return 0;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_img_book)
        ImageView mImageBook;
        @BindView(R.id.item_tv_book_title)
        TextView mTitleBook;
        @BindView(R.id.item_tv_book_author)
        TextView mAuthorBook;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }

    private String allAuthor(ArrayList<String> mAuthors) {
        String result = "";
        if (mAuthors.size() == 0) {
            return result;
        } else if (mAuthors.size() == 1) {
            result = "by ";
            result += mAuthors.get(0);
            return result;
        } else {
            result = "by ";
            for (int i = 0; i < mAuthors.size(); i++) {
                if (i == mAuthors.size()-1) {
                    result += mAuthors.get(i);
                } else {
                    result += mAuthors.get(i) + " , ";
                }
            }
            return result;
        }
    }

}
