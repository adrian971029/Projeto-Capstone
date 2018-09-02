package com.adrian_971029.infobook.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adrian_971029.infobook.DetailsActivity;
import com.adrian_971029.infobook.R;
import com.adrian_971029.infobook.model.Item;
import com.adrian_971029.infobook.model.VolumeInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private static final String VOLUME_INFO = "volume_info";

    private ArrayList<VolumeInfo> volumeInfoArrayList;
    private Context context;

    public FavoritesAdapter(Context context, ArrayList<VolumeInfo> volumeInfoArrayList) {
        this.context = context;
        this.volumeInfoArrayList = volumeInfoArrayList;
    }


    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_book, parent, false);
        final MainAdapter.ViewHolder viewHolder = new MainAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MainAdapter.ViewHolder viewHolder, int position) {
        MainAdapter.ViewHolder holder = (MainAdapter.ViewHolder) viewHolder;
        final VolumeInfo volumeInfo = volumeInfoArrayList.get(position);

        if (volumeInfo.getImageLinks() != null) {
            if(volumeInfo.getImageLinks().getExtraLarge() != null) {
                Picasso.with(context).load(volumeInfo.getImageLinks().getExtraLarge()).into(holder.mImageBook);
            } else if(volumeInfo.getImageLinks().getLarge() != null) {
                Picasso.with(context).load(volumeInfo.getImageLinks().getLarge()).into(holder.mImageBook);
            } else if(volumeInfo.getImageLinks().getMedium() != null) {
                Picasso.with(context).load(volumeInfo.getImageLinks().getMedium()).into(holder.mImageBook);
            } else if(volumeInfo.getImageLinks().getSmall() != null) {
                Picasso.with(context).load(volumeInfo.getImageLinks().getSmall()).into(holder.mImageBook);
            } else if(volumeInfo.getImageLinks().getThumbnail() != null) {
                Picasso.with(context).load(volumeInfo.getImageLinks().getThumbnail()).into(holder.mImageBook);
            } else {
                Picasso.with(context).load(volumeInfo.getImageLinks().getSmallThumbnail()).into(holder.mImageBook);
            }
        } else {
            holder.mImageBook.setImageResource(R.drawable.libros);
        }

        holder.mTitleBook.setText(volumeInfo.getTitle());
        holder.mAuthorBook.setText(allAuthor(volumeInfo.getAuthors()));

        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailsActivity.class);
                i.putExtra(VOLUME_INFO,volumeInfo);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (volumeInfoArrayList != null) {
            return volumeInfoArrayList.size();
        } else {
            return 0;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layout_item_book)
        LinearLayout mLayout;
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
        if(mAuthors != null) {
            if (mAuthors.size() == 0) {
                return result;
            } else if (mAuthors.size() == 1) {
                result = context.getString(R.string.lbl_by) + " ";
                result += mAuthors.get(0);
                return result;
            } else {
                result = context.getString(R.string.lbl_by) + " ";
                for (int i = 0; i < mAuthors.size(); i++) {
                    if (i == mAuthors.size()-1) {
                        result += mAuthors.get(i);
                    } else {
                        result += mAuthors.get(i) + " , ";
                    }
                }
                return result;
            }
        } else {
            return result;
        }
    }

}
