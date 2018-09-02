package com.adrian_971029.infobook.utils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.adrian_971029.infobook.R;
import com.adrian_971029.infobook.adapter.MainAdapter;
import com.adrian_971029.infobook.model.Item;
import com.adrian_971029.infobook.model.Volume;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadVolumeJson {

    private static final String TAG  = ReadVolumeJson.class.getSimpleName();

    private Volume volume;
    private ArrayList<Item> items;
    private MainAdapter mAdapter;
    ProgressBar mProgressBar;
    TextView mTextMensagem;
    Context context;

    public ReadVolumeJson(Volume volume, ArrayList<Item> items, MainAdapter mAdapter, ProgressBar mProgressBar, TextView mTextMensagem,Context context) {
        this.volume = volume;
        this.items = items;
        this.mAdapter = mAdapter;
        this.mProgressBar = mProgressBar;
        this.mTextMensagem = mTextMensagem;
        this.context = context;
    }

    public void chamaJSon(String category) {
        Call<Volume> volumeResponseCall = CategoryHelper.defineCategoria(category);

        volumeResponseCall.enqueue(new Callback<Volume>() {
            @Override
            public void onResponse(Call<Volume> call, Response<Volume> response) {
                if (!response.isSuccessful()) {
                    exibirProgreso(false);
                    Log.i(TAG, context.getResources().getString(R.string.error) + response.code());
                } else {
                    exibirProgreso(false);
                    Volume volumeResponse = response.body();
                    if (volumeResponse != null) {
                        volume = volumeResponse;
                        items.addAll(volume.getItems());
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<Volume> call, Throwable t) {
                exibirProgreso(false);
                Log.e(TAG, context.getResources().getString(R.string.error) + t.getMessage());
            }

        });
    }

    private void exibirProgreso(boolean exibir){
        mTextMensagem.setVisibility(exibir ? View.VISIBLE : View.GONE);

        mProgressBar.setVisibility(exibir ? View.VISIBLE : View.GONE);
    }

}
