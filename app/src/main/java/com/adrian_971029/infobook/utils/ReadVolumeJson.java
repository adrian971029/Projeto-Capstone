package com.adrian_971029.infobook.utils;

import android.util.Log;

import com.adrian_971029.infobook.adapter.MainAdapter;
import com.adrian_971029.infobook.model.Item;
import com.adrian_971029.infobook.model.Volume;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadVolumeJson {

    private Volume volume;
    private ArrayList<Item> items;
    private MainAdapter mAdapter;

    public ReadVolumeJson(Volume volume, ArrayList<Item> items, MainAdapter mAdapter) {
        this.volume = volume;
        this.items = items;
        this.mAdapter = mAdapter;
    }

    public void chamaJSon(String category) {
        Call<Volume> volumeResponseCall = CategoryHelper.defineCategoria(category);

        volumeResponseCall.enqueue(new Callback<Volume>() {
            @Override
            public void onResponse(Call<Volume> call, Response<Volume> response) {
                if (!response.isSuccessful()) {
                    Log.i("TAG", "Error:" + response.code());
                } else {
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
                Log.e("Book:", "Error:" + t.getMessage());
            }

        });
    }

}
